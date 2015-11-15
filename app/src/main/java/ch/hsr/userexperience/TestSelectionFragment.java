package ch.hsr.userexperience;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class TestSelectionFragment extends Fragment {

    public final static String TAG = "TestSelectionFragment";

    private FragmentController fragmentController;
    private Button weiterButton;

    public TestSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_selection, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        weiterButton = (Button) activity.findViewById(R.id.TestSelectionBtnWeiter);
        weiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentController != null) {
                    if(storeValues()){
                        fragmentController.changeFragment(new PageSelectionFragment());
                    }

                }
            }
        });
    }

    //Speichert und Überprüft die Testdaten
    private boolean storeValues() {


        return true;
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

}
