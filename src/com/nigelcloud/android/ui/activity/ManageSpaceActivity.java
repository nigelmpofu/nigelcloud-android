/**
 *   ownCloud Android client application
 *
 *   @author masensio
 *   @author Christian Schabesberger
 *   Copyright (C) 2018 ownCloud GmbH.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2,
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.nigelcloud.android.ui.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nigelcloud.android.R;
import com.nigelcloud.android.lib.common.utils.Log_OC;

import java.io.File;

public class ManageSpaceActivity extends AppCompatActivity {

    private static final String TAG = ManageSpaceActivity.class.getSimpleName();

    private static final String LIB_FOLDER = "lib";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_space);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.manage_space_title);

        TextView descriptionTextView = findViewById(R.id.general_description);
        descriptionTextView.setText(getString(R.string.manage_space_description, getString(R.string.app_name)));

        Button clearDataButton = findViewById(R.id.clearDataButton);
        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearDataAsynTask clearDataTask = new ClearDataAsynTask();
                clearDataTask.execute();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean retval = true;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                Log_OC.w(TAG, "Unknown menu item triggered");
                retval =  super.onOptionsItemSelected(item);
        }
        return retval;
    }

    /**
     * AsyncTask for Clear Data, saving the passcode
     */
    private class ClearDataAsynTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result = true;

            // Save passcode from Share preferences
            SharedPreferences appPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());

            boolean passCodeEnable = appPrefs.getBoolean(PassCodeActivity.PREFERENCE_SET_PASSCODE, false);
            boolean patternEnabled = appPrefs.getBoolean(PatternLockActivity.PREFERENCE_SET_PATTERN,false);
            boolean fingerprintEnabled = appPrefs.getBoolean(FingerprintActivity.PREFERENCE_SET_FINGERPRINT,false);

            String passCodeDigits[] = new String[4];
            if (passCodeEnable) {
                passCodeDigits[0] = appPrefs.getString(PassCodeActivity.PREFERENCE_PASSCODE_D1, null);
                passCodeDigits[1] = appPrefs.getString(PassCodeActivity.PREFERENCE_PASSCODE_D2, null);
                passCodeDigits[2] = appPrefs.getString(PassCodeActivity.PREFERENCE_PASSCODE_D3, null);
                passCodeDigits[3] = appPrefs.getString(PassCodeActivity.PREFERENCE_PASSCODE_D4, null);
            }
            String patternValue = new String();
            if(patternEnabled){
                patternValue = appPrefs.getString(PatternLockActivity.KEY_PATTERN,null);
            }

            // Clear data
            result = clearApplicationData();


            // Clear SharedPreferences
            SharedPreferences.Editor appPrefsEditor = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext()).edit();
            appPrefsEditor.clear();
            result = result && appPrefsEditor.commit();

            // Recover passcode
            if (passCodeEnable) {
                appPrefsEditor.putString(PassCodeActivity.PREFERENCE_PASSCODE_D1, passCodeDigits[0]);
                appPrefsEditor.putString(PassCodeActivity.PREFERENCE_PASSCODE_D2, passCodeDigits[1]);
                appPrefsEditor.putString(PassCodeActivity.PREFERENCE_PASSCODE_D3, passCodeDigits[2]);
                appPrefsEditor.putString(PassCodeActivity.PREFERENCE_PASSCODE_D4, passCodeDigits[3]);
            }

            // Recover pattern
            if(patternEnabled){
                appPrefsEditor.putString(PatternLockActivity.KEY_PATTERN,patternValue);
            }

            // Reenable fingerprint
            appPrefsEditor.putBoolean(FingerprintActivity.PREFERENCE_SET_FINGERPRINT, fingerprintEnabled);

            appPrefsEditor.putBoolean(PassCodeActivity.PREFERENCE_SET_PASSCODE, passCodeEnable);
            appPrefsEditor.putBoolean(PatternLockActivity.PREFERENCE_SET_PATTERN,patternEnabled);
            result = result && appPrefsEditor.commit();

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                Snackbar snackbar = Snackbar.make(
                        findViewById(android.R.id.content),
                        R.string.manage_space_clear_data,
                        Snackbar.LENGTH_LONG
                );
                snackbar.show();

            } else {
                finish();
                System.exit(0);
            }

        }

        public boolean clearApplicationData() {
            boolean clearResult = true;
            File appDir = new File(getCacheDir().getParent());
            if (appDir.exists()) {
                String[] children = appDir.list();
                if (children != null) {
                    for (String s : children) {
                        if (!LIB_FOLDER.equals(s)) {
                            File fileToDelete = new File(appDir, s);
                            clearResult = clearResult && deleteDir(fileToDelete);
                            Log_OC.d(TAG, "Clear Application Data, File: " + fileToDelete.getName() + " DELETED *****");
                        }
                    }
                } else {
                    clearResult = false;
                }
            }
            return  clearResult;
        }

        public boolean deleteDir(File dir) {
            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                if (children != null) {
                    for (int i = 0; i < children.length; i++) {
                        boolean success = deleteDir(new File(dir, children[i]));
                        if (!success) {
                            Log_OC.w(TAG, "File NOT deleted " + children[i]);
                            return false;
                        } else {
                            Log_OC.d(TAG, "File deleted " + children[i]);
                        }
                    }
                } else {
                    return false;
                }
            }

            return dir.delete();
        }
    }
}
