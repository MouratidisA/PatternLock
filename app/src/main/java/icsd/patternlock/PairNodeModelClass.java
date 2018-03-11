package icsd.patternlock;

/**
 * Created by Argiris Mouratidis on 11-Mar-18.
 */

public class PairNodeModelClass {
    int Pattern_number_Node;
    long Xcoord_of_central_Point_of_Node;
    long Ycoord_of_central_Point_of_Node;
    long First_Xcoord_of_Node;
    long First_Ycoord_of_Node;
    long Last_Xcoord_of_Node;
    long Last_Ycoord_of_Node;
    long FirstTimestamp;
    long LastTimestamp;
    float NodePressure;
    int TouchPointsCount;


    public PairNodeModelClass(int pattern_number_Node, long xcoord_of_central_Point_of_Node, long ycoord_of_central_Point_of_Node, long first_Xcoord_of_Node, long first_Ycoord_of_Node, long last_Xcoord_of_Node, long last_Ycoord_of_Node, long firstTimestamp, long lastTimestamp, float nodePressure, int touchPointsCount) {
        Pattern_number_Node = pattern_number_Node;
        Xcoord_of_central_Point_of_Node = xcoord_of_central_Point_of_Node;
        Ycoord_of_central_Point_of_Node = ycoord_of_central_Point_of_Node;
        First_Xcoord_of_Node = first_Xcoord_of_Node;
        First_Ycoord_of_Node = first_Ycoord_of_Node;
        Last_Xcoord_of_Node = last_Xcoord_of_Node;
        Last_Ycoord_of_Node = last_Ycoord_of_Node;
        FirstTimestamp = firstTimestamp;
        LastTimestamp = lastTimestamp;
        NodePressure = nodePressure;
        TouchPointsCount = touchPointsCount;
    }

    public int getPattern_number_Node() {
        return Pattern_number_Node;
    }

    public void setPattern_number_Node(int pattern_number_Node) {
        Pattern_number_Node = pattern_number_Node;
    }

    public long getXcoord_of_central_Point_of_Node() {
        return Xcoord_of_central_Point_of_Node;
    }

    public void setXcoord_of_central_Point_of_Node(long xcoord_of_central_Point_of_Node) {
        Xcoord_of_central_Point_of_Node = xcoord_of_central_Point_of_Node;
    }

    public long getYcoord_of_central_Point_of_Node() {
        return Ycoord_of_central_Point_of_Node;
    }

    public void setYcoord_of_central_Point_of_Node(long ycoord_of_central_Point_of_Node) {
        Ycoord_of_central_Point_of_Node = ycoord_of_central_Point_of_Node;
    }

    public long getFirst_Xcoord_of_Node() {
        return First_Xcoord_of_Node;
    }

    public void setFirst_Xcoord_of_Node(long first_Xcoord_of_Node) {
        First_Xcoord_of_Node = first_Xcoord_of_Node;
    }

    public long getFirst_Ycoord_of_Node() {
        return First_Ycoord_of_Node;
    }

    public void setFirst_Ycoord_of_Node(long first_Ycoord_of_Node) {
        First_Ycoord_of_Node = first_Ycoord_of_Node;
    }

    public long getLast_Xcoord_of_Node() {
        return Last_Xcoord_of_Node;
    }

    public void setLast_Xcoord_of_Node(long last_Xcoord_of_Node) {
        Last_Xcoord_of_Node = last_Xcoord_of_Node;
    }

    public long getLast_Ycoord_of_Node() {
        return Last_Ycoord_of_Node;
    }

    public void setLast_Ycoord_of_Node(long last_Ycoord_of_Node) {
        Last_Ycoord_of_Node = last_Ycoord_of_Node;
    }

    public long getFirstTimestamp() {
        return FirstTimestamp;
    }

    public void setFirstTimestamp(long firstTimestamp) {
        FirstTimestamp = firstTimestamp;
    }

    public long getLastTimestamp() {
        return LastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        LastTimestamp = lastTimestamp;
    }

    public float getNodePressure() {
        return NodePressure;
    }

    public void setNodePressure(float nodePressure) {
        NodePressure = nodePressure;
    }

    public int getTouchPointsCount() {
        return TouchPointsCount;
    }

    public void setTouchPointsCount(int touchPointsCount) {
        TouchPointsCount = touchPointsCount;
    }

    @Override
    public String toString() {
        return "PairNodeModelClass{" +
                "Pattern_number_Node=" + Pattern_number_Node +
                ", Xcoord_of_central_Point_of_Node=" + Xcoord_of_central_Point_of_Node +
                ", Ycoord_of_central_Point_of_Node=" + Ycoord_of_central_Point_of_Node +
                ", First_Xcoord_of_Node=" + First_Xcoord_of_Node +
                ", First_Ycoord_of_Node=" + First_Ycoord_of_Node +
                ", Last_Xcoord_of_Node=" + Last_Xcoord_of_Node +
                ", Last_Ycoord_of_Node=" + Last_Ycoord_of_Node +
                ", FirstTimestamp=" + FirstTimestamp +
                ", LastTimestamp=" + LastTimestamp +
                ", NodePressure=" + NodePressure +
                ", TouchPointsCount=" + TouchPointsCount +
                '}';
    }
}


