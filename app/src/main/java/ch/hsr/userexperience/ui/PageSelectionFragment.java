package ch.hsr.userexperience.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ch.hsr.userexperience.utils.FragmentController;
import ch.hsr.userexperience.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class PageSelectionFragment extends Fragment implements View.OnClickListener {

    private FragmentController fragmentController;
    private ImageButton zwanzigMinButton;
    private ImageButton blickButton;
    private ImageButton tagiButton;
    private ImageButton nzzButton;


    public PageSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_selection, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        zwanzigMinButton = (ImageButton) activity.findViewById(R.id.zwanzigMinBtn);
        zwanzigMinButton.setOnClickListener(this);
        blickButton = (ImageButton) activity.findViewById(R.id.blickBtn);
        blickButton.setOnClickListener(this);
        tagiButton = (ImageButton) activity.findViewById(R.id.tagiBtn);
        tagiButton.setOnClickListener(this);
        nzzButton = (ImageButton) activity.findViewById(R.id.nzzBtn);
        nzzButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        String url = null;
        switch (v.getId()) {
            case R.id.zwanzigMinBtn: url = "http://20min.ch";
                Log.e("PageSel", "20min Button clicked");
                break;
            case R.id.blickBtn: url = "http://blick.ch";
                Log.e("PageSel", "Blick Button clicked");
                break;
            case R.id.tagiBtn: url = "http://mobile2.tagesanzeiger.ch";
                Log.e("PageSel", "Tagi Button clicked");
                break;
            case R.id.nzzBtn: url = "http://nzz.ch";
                Log.e("PageSel", "NZZ Button clicked");
                break;
        }

        if (fragmentController != null && url != null) {
            fragmentController.storeValue(FragmentController.URL, url);
            fragmentController.changeFragment(new SurfFragment());
        }
    }
}
