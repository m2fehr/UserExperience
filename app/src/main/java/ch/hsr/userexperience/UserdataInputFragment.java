package ch.hsr.userexperience;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController} interface
 * to handle interaction events.
 */
public class UserdataInputFragment extends Fragment {

    public final static String TAG = "UserdataInputFragment";

    private FragmentController fragmentController;
    private Button weiterButton;
    private TextView txtAlter;
    private TextView txtGeschlecht;
    private TextView txtWohnort;
    private TextView txtGeduld;
    private Spinner spinnerAlter;
    private RadioGroup RGGeschlecht;
    private RadioGroup RGGeduld;
    private RadioGroup RGWohnort;


    public UserdataInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userdata_input, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        weiterButton = (Button) activity.findViewById(R.id.userdataInputBtnWeiter);
        weiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentController != null) {
                    if (storeValues()) {
                        fragmentController.changeFragment(new TestSelectionFragment());
                    }
                }
            }
        });
        txtAlter = (TextView) activity.findViewById(R.id.userdataInputTxtAlter);
        txtGeschlecht = (TextView) activity.findViewById(R.id.userdataInputTxtGeschlecht);
        txtWohnort = (TextView) activity.findViewById(R.id.userdataInputTxtWohnort);
        txtGeduld = (TextView) activity.findViewById(R.id.userdataInputTxtGeduld);

        spinnerAlter = (Spinner) activity.findViewById(R.id.userdataInputAlterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity, R.array.userdataInputSpinnerAlter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlter.setAdapter(adapter);

        RGGeschlecht = (RadioGroup) activity.findViewById(R.id.userdataInputRGGeschlecht);
        RGGeduld = (RadioGroup) activity.findViewById(R.id.userdataInputRGGeduld);
        RGWohnort = (RadioGroup) activity.findViewById(R.id.userdataInputRGWohnort);
    }

    //Speichert und Überprüft die Testdaten
    private boolean storeValues() {
        boolean alter = spinnerAlter.getItemAtPosition(spinnerAlter.getSelectedItemPosition()).toString().equals("Bitte Auswählen");
        boolean alter1 = spinnerAlter.getSelectedItem() == null;
        boolean geschlecht = RGGeschlecht.getCheckedRadioButtonId() == -1;
        boolean geduld = RGGeduld.getCheckedRadioButtonId() == -1;
        boolean wohnort = RGWohnort.getCheckedRadioButtonId() == -1;

        if(alter || alter1 || geschlecht || geduld || wohnort){
            if(alter || alter1){
                txtAlter.setError("Bitte wählen Sie Ihre Alterskategorie aus.");
            }
            if(geschlecht){
                txtGeschlecht.setError("Bitte wählen Sie Ihr Geschlecht aus.");
            }
            if(geduld){
                txtGeduld.setError("Bitte stufen Sie Ihre Geduld ein.");
            }
            if(wohnort){
                txtWohnort.setError("Bitte wählen Sie die auf Ihren Wohnort zutreffende Beschreibung aus.");
            }
            return false;
        }else{
            storeValueAlter();
            storeValueGeschlecht();
            storeValueGeduld();
            storeValueWohnort();
//            fragmentController.storeValue(fragmentController.ALTER, spinnerAlter.getSelectedItem());
//            fragmentController.storeValue(fragmentController.GESCHLECHT, RGGeschlecht.getCheckedRadioButtonId());
//            fragmentController.storeValue(fragmentController.GEDULD, RGGeduld.getCheckedRadioButtonId());
//            fragmentController.storeValue(fragmentController.WOHNORT, RGWohnort.getCheckedRadioButtonId());
            return true;
        }
    }

    private void storeValueWohnort() {
        switch (RGWohnort.getCheckedRadioButtonId()){
            case R.id.userdataInputRBtnStadt:
                fragmentController.storeValue(fragmentController.WOHNORT, 0);
                break;
            case R.id.userdataInputRBtnAgg:
                fragmentController.storeValue(fragmentController.WOHNORT, 1);
                break;
            case R.id.userdataInputRBtnAusser:
                fragmentController.storeValue(fragmentController.WOHNORT, 2);
                break;
            default:
                Log.e("storeValueWohnort", "Fehler bei auslesen des Wohnortes");
        }
    }

    private void storeValueGeduld() {
        switch(RGGeduld.getCheckedRadioButtonId()){
            case R.id.userdataInputRBtnGed:
                fragmentController.storeValue(fragmentController.GEDULD, 0);
                break;
            case R.id.userdataInputRBtnMitt:
                fragmentController.storeValue(fragmentController.GEDULD, 1);
                break;
            case R.id.userdataInputRBtnUnged:
                fragmentController.storeValue(fragmentController.GEDULD, 2);
                break;
            default:
                Log.e("storeValueGeduld", "Fehler bei auslesen der Geduld");
        }
    }

    private void storeValueGeschlecht() {
        switch(RGGeschlecht.getCheckedRadioButtonId()){
            case R.id.userdataInputRBtnM:
                fragmentController.storeValue(fragmentController.GESCHLECHT, 0);
                break;
            case R.id.userdataInputRBtnW:
                fragmentController.storeValue(fragmentController.GESCHLECHT, 1);
                break;
            default:
                Log.e("storeValueGeschlecht", "Fehler bei auslesen des Geschlechtes");
        }
    }

    private void storeValueAlter() {
        switch(spinnerAlter.getSelectedItemPosition()){
            case 1:
                fragmentController.storeValue(fragmentController.ALTER, 0);
                Log.e("StoreAlter", "0-15");
                break;
            case 2:
                fragmentController.storeValue(fragmentController.ALTER, 1);
                Log.e("StoreAlter", "16-30");
                break;
            case 3:
                fragmentController.storeValue(fragmentController.ALTER, 2);
                Log.e("StoreAlter", "31-45");
                break;
            case 4:
                fragmentController.storeValue(fragmentController.ALTER, 3);
                Log.e("StoreAlter", "46-");
                break;
            default:
                Log.e("StoreAlter", "Fehler bei auslesen der Altersgruppe aus dem Spinner");
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
