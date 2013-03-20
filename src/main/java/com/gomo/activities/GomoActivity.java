package com.gomo.activities;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.gomo.R;
import com.gomo.utilities.MenuHandler;
import com.google.analytics.tracking.android.EasyTracker;

public abstract class GomoActivity extends Activity {

    @Override
    public void onStart() {

        super.onStart();

        EasyTracker.getInstance().activityStart(this);
    }

    @Override
    public void onStop() {

        super.onStop();

        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return MenuHandler.onOptionsItemSelected(item, this);
    }

}