package com.codeprogression.mvpb.model;

import retrofit2.Call;

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
        when(imdbServiceMock.searchIMDB(anyString(), anyInt())).thenReturn(mock(Call.class));
        presenter = new MainPresenter(imdbServiceMock, null);
        viewModel = new ListViewModel();
    }


    @Test
    public void searchIMDB_lessThan3CharsEntered_EmptyList(){
        viewModel.listItemViewModels.add(new ListItemViewModel("a title", ""));
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
    public void searchIMDB_SomeResultsLoaded_NewQueryErasesOldResults(){
        presenter.attach(viewModel);
        viewModel.listItemViewModels.add(new ListItemViewModel("", ""));

        presenter.searchIMDB("fargo");

        assertThat(viewModel.listItemViewModels).isEmpty();
    }

    @Test
    public void processImdbResult_totalGreaterThanReturned_HasMoreElements(){
        presenter.attach(viewModel);
        IMDBResponse response = new IMDBResponse();
        response.Response = true;
        response.totalResults = 20;
        response.Search = Lists.newArrayList(new ImdbRecord());

        presenter.processIMDBResponse(response);

        assertThat(viewModel.listItemViewModels).hasSize(1);
        assertThat(viewModel.hasMore.get()).isTrue();
        assertThat(viewModel.pagesLoaded).isEqualTo(1);
    }

    @Test
    public void processImdbResult_lastPageReturned_HasNoMoreElements(){
        viewModel.listItemViewModels.add(new ListItemViewModel("first element", ""));//one item already in the list
        viewModel.pagesLoaded = 1;//first page loaded
        presenter.attach(viewModel);

        IMDBResponse response = new IMDBResponse();
        response.Response = true;
        response.totalResults = 2;
        response.Search = Lists.newArrayList(new ImdbRecord());

        presenter.processIMDBResponse(response);

        assertThat(viewModel.listItemViewModels).hasSize(2);
        assertThat(viewModel.hasMore.get()).isFalse();
        assertThat(viewModel.pagesLoaded).isEqualTo(2);
    }

    @Test
    public void loadMoreResults_HasMore_SecondPageIsRequested(){
        presenter.attach(viewModel);
        viewModel.hasMore.set(true);
        viewModel.pagesLoaded = 1;
        viewModel.listItemViewModels.add(new ListItemViewModel("title", ""));

        presenter.searchImdb("fargo", 2);

        verify(imdbServiceMock).searchIMDB("fargo", 2);

    }


}