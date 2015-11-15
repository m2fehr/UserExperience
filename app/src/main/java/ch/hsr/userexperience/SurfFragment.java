package ch.hsr.userexperience;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


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
    private Button weiterButton;

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

        weiterButton = (Button) activity.findViewById(R.id.surfFragmentWeiterBtn);
        weiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentController != null) {
                    if(storeResults()){
                        fragmentController.changeFragment(new FeedbackFragment());
                    }
                }
            }
        });

        webView = (WebView) activity.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, url + " finished loading");
            }
        });
        Log.e(TAG, "load URL");
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

    public void webViewGoBack() {
        Log.e(TAG, "webViewGoBack()");
        webView.goBack();
    }

    public boolean webViewCanGoBack() {
        Log.e(TAG, "webViewCanGoBack()");
        if (webView.canGoBack())
            return true;
        else
            return false;
    }

    //Überprüfen und Speichern der Testdaten
    public boolean storeResults(){


        return true;
    }
}