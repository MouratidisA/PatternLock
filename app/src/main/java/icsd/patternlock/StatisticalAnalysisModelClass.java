package icsd.patternlock;

/**
 * Created by Tasos on 3/15/2018.
 */

public class StatisticalAnalysisModelClass {
    private int LongRuns;
    private int Closedcurves;
    private int Longcurves;
    private int LongEdges;
    private int ShortEdges;
    private int LongOrthogonaledges;
    private int ShortOrthogonaledges;

    public StatisticalAnalysisModelClass(int longRuns, int closedcurves, int longcurves, int longEdges, int shortEdges, int longOrthogonaledges, int shortOrthogonaledges) {
        LongRuns = longRuns;
        Closedcurves = closedcurves;
        Longcurves = longcurves;
        LongEdges = longEdges;
        ShortEdges = shortEdges;
        LongOrthogonaledges = longOrthogonaledges;
        ShortOrthogonaledges = shortOrthogonaledges;
    }

    @Override
    public String toString() {
        return "StatisticalAnalysisModelClass{" +
                "LongRuns=" + LongRuns +
                ", Closedcurves=" + Closedcurves +
                ", Longcurves=" + Longcurves +
                ", LongEdges=" + LongEdges +
                ", ShortEdges=" + ShortEdges +
                ", LongOrthogonaledges=" + LongOrthogonaledges +
                ", ShortOrthogonaledges=" + ShortOrthogonaledges +
                '}';
    }

    public int getLongRuns() {
        return LongRuns;
    }

    public void setLongRuns(int longRuns) {
        LongRuns = longRuns;
    }

    public int getClosedcurves() {
        return Closedcurves;
    }

    public void setClosedcurves(int closedcurves) {
        Closedcurves = closedcurves;
    }

    public int getLongcurves() {
        return Longcurves;
    }

    public void setLongcurves(int longcurves) {
        Longcurves = longcurves;
    }

    public int getLongEdges() {
        return LongEdges;
    }

    public void setLongEdges(int longEdges) {
        LongEdges = longEdges;
    }

    public int getShortEdges() {
        return ShortEdges;
    }

    public void setShortEdges(int shortEdges) {
        ShortEdges = shortEdges;
    }

    public int getLongOrthogonaledges() {
        return LongOrthogonaledges;
    }

    public void setLongOrthogonaledges(int longOrthogonaledges) {
        LongOrthogonaledges = longOrthogonaledges;
    }

    public int getShortOrthogonaledges() {
        return ShortOrthogonaledges;
    }

    public void setShortOrthogonaledges(int shortOrthogonaledges) {
        ShortOrthogonaledges = shortOrthogonaledges;
    }
}
