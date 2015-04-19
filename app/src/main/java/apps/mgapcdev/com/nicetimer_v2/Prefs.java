package apps.mgapcdev.com.nicetimer_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
//import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by Mathias on 02/09/2014.
 */

public class Prefs extends PreferenceActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().
            replace(android.R.id.content, new PrefsFragment()).commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public static class PrefsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference filePicker = (Preference) findPreference("pref_filePicker");
            filePicker.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("audio/*");
                    Log.d("filepicker","opening filepicker");
                    startActivityForResult(i, READ_REQUEST_CODE);
                    return true;
                }
        });
        }
    }
    static int READ_REQUEST_CODE = 42;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Log.d("filepicker","onResult");
        if (requestCode == READ_REQUEST_CODE && resultCode == this.RESULT_OK) {
            Log.d("filepicker","result.ok");
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("filepicker", "Uri: " + uri.toString());
                //showImage(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
