package ch.hsr.userexperience;

import android.app.Fragment;

/**
 * Created by Matthias on 06.11.15.
 */
public interface FragmentController {

    public final static String key1 = "Key1";   //Keys for storeValue()

    public void changeFragment(Fragment fragment);
    public void storeValue(String key, long value);
}
