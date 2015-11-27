package ch.hsr.userexperience.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matthias on 13.11.15.
 */
public class TestResults {

//    private int _userID;
    private ArrayList<TestEntry> testResults;

    public TestResults() {
//        this._userID = userId;
        testResults = new ArrayList<>();
    }

    public void addEntry(TestEntry entry) {
        testResults.add(entry);
    }

    public List<TestEntry> getEntrys() {
        return Collections.unmodifiableList(testResults);
    }

//    public int get_userID() {
//        return _userID;
//    }

}
