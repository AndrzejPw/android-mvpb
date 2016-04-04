package com.codeprogression.mvpb;

import android.view.View;
import org.assertj.android.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by andrzej.biernacki on 2016-04-04.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
//@Config(manifest = "build/intermediates/manifests/full/debug/AndroidManifest.xml")
public class MainActivityTest {

    @Test
    public void testSetupMainActivity() {
        final MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        final View mainView = mainActivity.findViewById(R.id.main_view);

        Assertions.assertThat(mainView).isNotNull();
    }
}