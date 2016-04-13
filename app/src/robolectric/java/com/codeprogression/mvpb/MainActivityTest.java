package com.codeprogression.mvpb;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class MainActivityTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSetupMainActivity() {
        final MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        final View mainView = mainActivity.findViewById(R.id.main_view);

        Assertions.assertThat(mainView).isNotNull();
    }

    @Test
    public void testButtonClicked() {
        final MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        //inject mockPresenter

        final RelativeLayout mainView = (RelativeLayout) mainActivity.findViewById(R.id.main_view);

        for (int i = 0; i < mainView.getChildCount(); i++) {
            final View childAt = mainView.getChildAt(i);

            if (childAt instanceof Button) {
                childAt.performClick();
                //verrify presenter is called

            }
        }

    }
}