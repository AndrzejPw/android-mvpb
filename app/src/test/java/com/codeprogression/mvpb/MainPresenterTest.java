package com.codeprogression.mvpb;

import com.codeprogression.mvpb.model.IMDBService;
import com.codeprogression.mvpb.model.MainPresenter;
import com.codeprogression.mvpb.viewModel.ListItemViewModel;
import com.codeprogression.mvpb.viewModel.ListViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    private IMDBService imdbServiceMock;
    private MainPresenter presenter;
    private ListViewModel viewModel;

    @Before
    public void setup(){
        imdbServiceMock = mock(IMDBService.class);
        presenter = new MainPresenter(imdbServiceMock, null);
        viewModel = new ListViewModel();
    }

    @Test
    public void smokeTest(){
        presenter.attach(viewModel);
        assertThat(viewModel.number.get()).isEqualTo(10);
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
        viewModel.listItemViewModels.add(new ListItemViewModel("a title"));
        presenter.attach(viewModel);

        presenter.searchIMDB("fargo");

        verify(imdbServiceMock).searchIMDB("fargo");
    }

    @Test
    public void test_presenterUpdatesViewModel(){
        presenter.attach(viewModel);
        presenter.add();
        assertThat(viewModel.number.get()).isEqualTo(11);
    }

    @Test
    public void test_presenterResetsNumber(){
        viewModel.number.set(1);
        presenter.attach(viewModel);
        assertThat(viewModel.number.get()).isEqualTo(10);
    }

    @Test
    public void test_presenterDoesNotResetNumber(){
        viewModel.number.set(15);
        presenter.attach(viewModel);
        assertThat(viewModel.number.get()).isEqualTo(15);
    }
}