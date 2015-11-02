package ch.hsr.userexperience;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActiviy";

    private WebView webView;
    private EditText urlField;
    private Button startBtn;
    private long startTime;
    private long doneTime;
    private boolean loadingFinished, redirect;
    private ArrayList<String> ressourceUrlList;
    private TreeSet<String> hostUrlSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.webView);
        urlField = (EditText) findViewById(R.id.urlField);
        startBtn = (Button) findViewById(R.id.startTestButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });

        ressourceUrlList = new ArrayList<>();
        hostUrlSet = new TreeSet<>();

        urlField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v == urlField && event != null) {
                    startTest();
                    return true;
                }
                return false;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                urlField.setText(urlNewString);
                webView.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished = false;
//                startTime = System.currentTimeMillis();
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                ressourceUrlList.add(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                }
                if (loadingFinished && !redirect) {
                    doneTime = System.currentTimeMillis();
                    Log.d(TAG, "loaded url: " + url);
                    processResult();
                    //int timeToLoad = doneTime - startTime;
                } else {
                    redirect = false;
                }
            }
        });

        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action now", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void startTest() {
        webView.clearCache(true);
        startBtn.setEnabled(false);
        ressourceUrlList.clear();
        hostUrlSet.clear();
//        startBtn.setVisibility(View.GONE);
//        urlField.setVisibility(View.GONE);

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        String url = urlField.getText().toString();
        if(!url.startsWith("http://"))
            url = "http://" + url;

        startTime = System.currentTimeMillis();
        webView.loadUrl(url);
    }

    private void processResult() {
        long loadingTime = doneTime - startTime;
        startBtn.setEnabled(true);
//        urlField.setText("");
//        Toast.makeText(getBaseContext(), "PLT: " + loadingTime + "ms", Toast.LENGTH_LONG).show();
// //       urlField.setVisibility(View.VISIBLE);

        for (String url : ressourceUrlList) {
            hostUrlSet.add(getHost(url));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Test results");
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView t = new TextView(this);
        t.setText("Time: " + loadingTime);
        layout.addView(t);
        for (String host : hostUrlSet)
        {
            t = new TextView(this);
            t.setText(host);

            layout.addView(t);
        }

        final ScrollView scrollView = new ScrollView(this);
        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        builder.show();

    }

    public String getHost(String url){
        if(url == null || url.length() == 0)
            return "";

        int doubleslash = url.indexOf("//");
        if(doubleslash == -1)
            doubleslash = 0;
        else
            doubleslash += 2;

        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;

        return url.substring(doubleslash, end);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
