package ch.hsr.userexperience.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import ch.hsr.userexperience.utils.FragmentController;
import ch.hsr.userexperience.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class TestSelectionFragment extends Fragment {

    public final static String TAG = "TestSelectionFragment";

    private FragmentController fragmentController;
    private Button nextButton;
    private Spinner testSpinner;
    private TextView txtTestSelection;

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

        nextButton = (Button) activity.findViewById(R.id.TestSelectionBtnWeiter);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentController != null) {
                    if (storeValues()) {
                        fragmentController.changeFragment(new PageSelectionFragment());
                    }

                }
            }
        });

        txtTestSelection = (TextView) activity.findViewById(R.id.TestSelectionText1);

        testSpinner = (Spinner) activity.findViewById(R.id.TestSelectionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity, R.array.testSelection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testSpinner.setAdapter(adapter);
    }

    //Speichert und Überprüft die Testdaten
    private boolean storeValues() {
        boolean testSelection = testSpinner.getItemAtPosition(testSpinner.getSelectedItemPosition()).toString().equals("Bitte Auswählen");
        boolean testSelection1 = testSpinner.getSelectedItem() == null;

        if(testSelection || testSelection1){
            txtTestSelection.setError(getResources().getString(R.string.no_option_choosed_error));
            return false;
        }else{
            fragmentController.storeValue(FragmentController.ABO, testSpinner.getSelectedItem());
            return true;
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

}
