package ch.hsr.userexperience;

import android.app.Fragment;

/**
 * Created by Matthias on 06.11.15.
 */
public interface FragmentController {
    //Keys for storeValue()

    //Keys für PageSelectionFragment
    public final static String URL = "url";

    //Keys für FeedbackFragment
    public final static String RGSURFGESCHW = "Feedback Surfgeschwindigkeit";
    public final static String RGABBRUCH = "Abbruchgrund";

    //Keys für UserDataInputFragment
    public final static String ALTER = "alter";
    public final static String GEDULD = "geduld";
    public final static String GESCHLECHT = "geschlecht";
    public final static String WOHNORT = "wohnort";

    public void changeFragment(Fragment fragment);
    public void storeValue(String key, Object value);
    public Object getValue(String key);
}
