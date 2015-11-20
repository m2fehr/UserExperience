package ch.hsr.userexperience;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements FragmentController {

    public final static String TAG = "MainActiviy";

    private Fragment currentFragment;
    private String url; //temporary

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
//            Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
//            startActivity(dbmanager);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changeFragment(Fragment fragment) {
        Log.e(TAG, "change Fragment");
        currentFragment = fragment;
        FragmentManager fm = getFragmentManager();
        if (fragment instanceof InformationFragment) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            currentUser = new User();
            currentTestResults = new TestResults();
        }
        else if (fragment instanceof TestSummaryFragment) {
            //dbHelper.insertUserAndTests(currentUser, currentTestResults);
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
        //ToDo: Zuerst richtige Werte übergeben!
//        switch (key) {
//            case FragmentController.ALTER: currentUser.set_age(value);
//                break;
//            case FragmentController.GEDULD: currentUser.set_patience(value);
//                break;
//            case FragmentController.GESCHLECHT: currentUser.set_gender(value);
//                break;
//            case FragmentController.WOHNORT: currentUser.set_location(value);
//                break;
//            case FragmentController.ABO: currentUser.set_abo(value);
//                break;
//            case FragmentController.RGABBRUCH: currentUser.set_aborted(value);
//                break;
//            case FragmentController.RGSURFGESCHW: currentUser.set_satisfaction(value);
//                break;
//        }
    }

    @Override
    public Object getValue(String key) {
        switch (key) {
            case FragmentController.URL: return url;
        }
        return null;
    }

}
