package ch.hsr.userexperience.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import ch.hsr.userexperience.R;
import ch.hsr.userexperience.db.AndroidDatabaseManager;
import ch.hsr.userexperience.db.DbHelper;
import ch.hsr.userexperience.utils.FragmentController;
import ch.hsr.userexperience.utils.TestEntry;
import ch.hsr.userexperience.utils.TestResults;
import ch.hsr.userexperience.utils.User;

public class MainActivity extends AppCompatActivity implements FragmentController {

    public final static String TAG = "MainActiviy";

    private Fragment currentFragment;
    private String url; //temporary

    private DbHelper dbHelper;

    private User currentUser;
    private TestResults currentTestResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(dbHelper == null)
            dbHelper = new DbHelper(this);
        changeFragment(new InformationFragment());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && currentFragment instanceof SurfFragment) {
            SurfFragment surfFragment = (SurfFragment) currentFragment;
            if(surfFragment.webViewCanGoBack()) {
                surfFragment.webViewGoBack();
                return true;
            }
        }
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (currentFragment != null) {
            if (currentFragment instanceof InformationFragment) {
                MenuItem item = menu.findItem(R.id.action_cancel);
                item.setVisible(false);
                item = menu.findItem(R.id.action_DBopen);
                item.setVisible(true);
                item = menu.findItem(R.id.action_DBexport);
                item.setVisible(true);

            } else  {
                MenuItem item = menu.findItem(R.id.action_cancel);
                item.setVisible(true);
                item = menu.findItem(R.id.action_DBopen);
                item.setVisible(false);
                item = menu.findItem(R.id.action_DBexport);
                item.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            if (currentFragment instanceof SurfFragment || currentFragment instanceof PageSelectionFragment)
                changeFragment(new FeedbackFragment());
            else
                changeFragment(new InformationFragment());
            return true;
        }
        if (id == R.id.action_DBopen) {
            Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
            startActivity(dbmanager);
            return true;
        }
        if (id == R.id.action_DBexport) {
            if(dbHelper != null)
                exportDB();
                //dbHelper.renewDb();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changeFragment(Fragment nextFragment) {
        Log.e(TAG, "change Fragment");
        FragmentManager fm = getFragmentManager();
        if (nextFragment instanceof InformationFragment) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            currentUser = new User();
            currentTestResults = new TestResults();
        }
        if (nextFragment instanceof FeedbackFragment && currentFragment instanceof SurfFragment) {
            SurfFragment frag = (SurfFragment) currentFragment;
            frag.aboutToChangeFragment();
        }
        invalidateOptionsMenu();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        currentFragment = nextFragment;
    }

    @Override
    public void storeValue(String key, Object value) {
        if(currentTestResults == null)
            return;
        switch (key) {
            case FragmentController.URL: url = (String) value;
                break;
            case FragmentController.TESTENTRY: currentTestResults.addEntry((TestEntry) value);
                break;
        }
    }

    @Override
    public void storeValue(String key, int value) {
        if (currentUser == null)
            return;
        switch (key) {
            case FragmentController.ALTER: currentUser.set_age(value);
                break;
            case FragmentController.GEDULD: currentUser.set_patience(value);
                break;
            case FragmentController.GESCHLECHT: currentUser.set_gender(value);
                break;
            case FragmentController.WOHNORT: currentUser.set_location(value);
                break;
            case FragmentController.ABO: currentUser.set_abo(value);
                break;
            case FragmentController.RGABBRUCH: currentUser.set_aborted(value);
                break;
            case FragmentController.RGSURFGESCHW: currentUser.set_satisfaction(value);
                break;
        }
    }

    @Override
    public Object getValue(String key) {
        switch (key) {
            case FragmentController.URL: return url;
        }
        return null;
    }

    @Override
    public void storeToDb() {
        dbHelper.insertUserAndTests(currentUser, currentTestResults);
    }

    private void exportDB(){
        //File sd = Environment.getExternalStorageDirectory();
        File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "ch.hsr.userexperience" +"/databases/" + DbHelper.DATABASE_NAME;
        String backupDBPath = DbHelper.DATABASE_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_SHORT).show();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(this,
                    new String[]{backupDB.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        } catch(IOException e) {
            Toast.makeText(getBaseContext(), "Failed to export DB", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
