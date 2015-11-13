package ch.hsr.userexperience;

/**
 * Created by Matthias on 13.11.15.
 */
public class TestEntry {

    private String site;
    private long time;

    public TestEntry(String site, long time) {
        this.site = site;
        this.time = time;
    }

    public String getSite() {
        return site;
    }

    public long getTime() {
        return time;
    }
}
