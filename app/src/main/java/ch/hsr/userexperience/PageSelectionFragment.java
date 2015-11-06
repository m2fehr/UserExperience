package ch.hsr.userexperience;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class PageSelectionFragment extends Fragment implements View.OnClickListener {

    private FragmentController fragmentController;
    private ImageButton zwanzigMinButton;

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
        String url;
        switch (v.getId()) {
            case R.id.zwanzigMinBtn: url = "http://20min.ch";
                Log.e("PAgeSel", "20min BUtton clicked");
                break;
        }

        if (fragmentController != null) {
            fragmentController.changeFragment(new SurfFragment());
        }

    }
}
