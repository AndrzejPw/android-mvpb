package com.codeprogression.mvpb.model;

import retrofit2.Call;

import com.codeprogression.mvpb.model.IMDBService;
import com.codeprogression.mvpb.model.MainPresenter;
import com.codeprogression.mvpb.viewModel.ListItemViewModel;
import com.codeprogression.mvpb.viewModel.ListViewModel;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MainPresenterTest {

    private IMDBService imdbServiceMock;
    private MainPresenter presenter;
    private ListViewModel viewModel;

    @Before
    public void setup(){
        imdbServiceMock = mock(IMDBService.class);
        when(imdbServiceMock.searchIMDB(anyString())).thenReturn(mock(Call.class));
        presenter = new MainPresenter(imdbServiceMock, null);
        viewModel = new ListViewModel();
    }


    @Test
    public void searchIMDB_lessThan3CharsEntered_EmptyList(){
        viewModel.listItemViewModels.add(new ListItemViewModel("a title"));
        presenter.attach(viewModel);

        presenter.searchIMDB("fa");

        assertThat(viewModel.listItemViewModels).isEmpty();
        verify(imdbServiceMock, never()).searchIMDB(anyString());
    }

    @Test
    public void searchIMDB_QueryEntered_IMDBCalled(){
        presenter.attach(viewModel);

        presenter.searchIMDB("fargo");

        verify(imdbServiceMock).searchIMDB("fargo");
    }

    @Test
    public void processImdbResult_partialList_HasMoreElements(){
        presenter.attach(viewModel);
        IMDBResponse response = new IMDBResponse();
        response.Response = true;
        response.totalResults = 20;
        response.Search = Lists.newArrayList(new ImdbRecord());

        presenter.processIMDBResponse(response);

        assertThat(viewModel.listItemViewModels).hasSize(1);
        assertThat(viewModel.hasMore.get()).isTrue();
    }

    @Test
    public void loadMoreResults_HasMore_SecondPageIsRequested(){
        presenter.attach(viewModel);
        viewModel.hasMore.set(true);
        viewModel.totalElements = 5;
        viewModel.listItemViewModels.add(new ListItemViewModel("title"));
        viewModel.query = "fargo";
        presenter.loadMore();

        verify(imdbServiceMock).searchIMDB("fargo", 2);

    }


}