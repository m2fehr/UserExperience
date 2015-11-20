package ch.hsr.userexperience;

/**
 * Created by Matthias on 13.11.15.
 */
public class TestEntry {

    private String site;
    private long time;
    private int nofElements;
    private int nofServers;
    private boolean finishedLoading;

    public TestEntry(String site, long time, int nofElements, int nofServers, boolean finishedLoading) {
        this.site = site;
        this.time = time;
        this.nofElements = nofElements;
        this.nofServers = nofServers;
        this.finishedLoading = finishedLoading;
    }

    public String getSite() {
        return site;
    }

    public long getTime() {
        return time;
    }

    public int getNofElements() {
        return nofElements;
    }

    public int getNofServers() {
        return nofServers;
    }

    public boolean isFinishedLoading() {
        return finishedLoading;
    }

    @Override
    public String toString() {
        return "TestEntry{" +
                "site='" + site + '\'' +
                ", time=" + time +
                ", nofElements=" + nofElements +
                ", nofServers=" + nofServers +
                ", finishedLoading=" + finishedLoading +
                '}';
    }
}
