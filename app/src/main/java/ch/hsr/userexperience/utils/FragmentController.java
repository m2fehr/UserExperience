package ch.hsr.userexperience.utils;

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

    //Keys für TestSelectionFragment
    public final static String ABO = "Abo";

    //Keys für SurfFragment
    public final static String TESTENTRY = "TestEntry";

    public void changeFragment(Fragment fragment);
    public void storeValue(String key, Object value);
    public void storeValue(String key, int value);
    public void storeToDb();
    public Object getValue(String key);
}
