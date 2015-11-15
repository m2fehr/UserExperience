package ch.hsr.userexperience;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class FeedbackFragment extends Fragment {

    public final static String TAG = "FeedbackFragment";

    private FragmentController fragmentController;
    private Button weiterButton;
    private RadioGroup rg1;
    private RadioGroup rg2;
    private TextView tv1;
    private TextView tv2;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        weiterButton = (Button) activity.findViewById(R.id.feedbackFragmentWeiterBtn);
        weiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentController != null) {
                    if(storeValues()){
                        fragmentController.changeFragment(new TestSummaryFragment());
                    }
                }
            }
        });
        rg1 = (RadioGroup) activity.findViewById(R.id.radioGroup);
        rg2 = (RadioGroup) activity.findViewById(R.id.radioGroup2);
        tv1 = (TextView) activity.findViewById(R.id.textView);
        tv2 = (TextView) activity.findViewById(R.id.textView2);
    }

    //Speichert die Feedbacks ab.
    private boolean storeValues() {
        int r1 = rg1.getCheckedRadioButtonId();
        int r2 = rg2.getCheckedRadioButtonId();
        if(r1 == -1 || r2 == -1){
            if(r1 == -1){
                tv1.setError("Bitte wählen Sie eine Option aus");
            }
            if(r2 == -1){
                tv2.setError("Bitte wählen Sie eine Option aus");
            }
            return false;
        }else{
            fragmentController.storeValue(FragmentController.RGSURFGESCHW, rg1.getCheckedRadioButtonId());
            fragmentController.storeValue(FragmentController.RGABBRUCH, rg2.getCheckedRadioButtonId());
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
