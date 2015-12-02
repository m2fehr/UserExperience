package ch.hsr.userexperience.ui;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.TreeSet;

import ch.hsr.userexperience.R;
import ch.hsr.userexperience.model.TestEntry;
import ch.hsr.userexperience.utils.FragmentController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class SurfFragment extends Fragment {

    public final static String TAG = "SurfFragment";

    private FragmentController fragmentController;
    private WebView webView;

    private long loadingStartTime;
    private long loadingStopTime;

    private boolean loadingFinished, redirect;
    private String urlAboutToLoad = "";
    private TestEntry currentTestEntry = null;

    private ArrayList<String> ressourceUrlList;
    private TreeSet<String> hostUrlSet;

    public SurfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_surf, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        ressourceUrlList = new ArrayList<>();
        hostUrlSet = new TreeSet<>();

        webView = (WebView) activity.findViewById(R.id.webView);
        webView.setWebViewClient(wvc);
        webView.getSettings().setJavaScriptEnabled(true);
        if(fragmentController != null) {
            webView.loadUrl((String) fragmentController.getValue(FragmentController.URL));
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            fragmentController = (FragmentController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentController");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentController = null;
    }

    public void aboutToChangeFragment() {
        Log.e(TAG, "aboutToChangeFragment called");
        if (currentTestEntry != null && fragmentController != null) {
            fragmentController.storeValue(FragmentController.TESTENTRY, currentTestEntry);
            Log.e(TAG, "Results stored: " + currentTestEntry.toString());
            currentTestEntry = null;
        }
    }

    public void webViewGoBack() {
        webView.goBack();
    }

    public boolean webViewCanGoBack() {
        if (webView.canGoBack())
            return true;
        else
            return false;
    }

    private final WebViewClient wvc = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            loadingFinished = false;

            if (!url.equals(urlAboutToLoad) && currentTestEntry != null && fragmentController != null) {
                fragmentController.storeValue(FragmentController.TESTENTRY, currentTestEntry);
                Log.e(TAG, "Results stored: " + currentTestEntry.toString());
                currentTestEntry = null;
            }

            urlAboutToLoad = url;
            Log.e(TAG, "pageStarted: " + url);
            ressourceUrlList.clear();
            hostUrlSet.clear();
            loadingStartTime = System.currentTimeMillis();
        }

        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) { //example prevents resourceloading from google
//            if (Uri.parse(url).getHost().equals("www.google.com")) {
//                return true;
//            }
//            if (!loadingFinished) {
//                redirect = true;
//            }
            Log.e(TAG, "shouldOverride: " + url);
            return false;
        }


        @Override
        public void onLoadResource(WebView view, String url) {
            ressourceUrlList.add(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            if (!redirect) {
//                loadingFinished = true;
//            }
//            if(loadingFinished && !redirect) {
//                loadingStopTime = System.currentTimeMillis();
//                processResult(url);
//            }
//            else {
//                redirect = false;
//            }

            if(urlAboutToLoad.equals(url)) {
                loadingStopTime = System.currentTimeMillis();
                processResult(url);
            }
        }
    };

    public void processResult(String loadedUrl) {
        long timeToLoad = loadingStopTime - loadingStartTime;

        for (String url : ressourceUrlList) {
            hostUrlSet.add(getHost(url));
        }

        if (fragmentController != null){
            currentTestEntry = new TestEntry(loadedUrl,
                    timeToLoad, ressourceUrlList.size(), hostUrlSet.size(), true);
//            fragmentController.storeValue(FragmentController.TESTENTRY, entry);
//            Log.e(TAG, "Page Results: " + entry.toString());
        }
    }

    public String getHost(String url) {
        if (url == null || url.length() == 0)
            return "";

        int doubleslash = url.indexOf("//");
        if (doubleslash == -1)
            doubleslash = 0;
        else
            doubleslash += 2;
        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();
        int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;
        return url.substring(doubleslash, end);
    }
}