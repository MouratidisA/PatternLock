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

    public PairNodeModelClass(
            int pattern_number_Node, long xcoord_of_central_Point_of_Node, long ycoord_of_central_Point_of_Node,
            long first_Xcoord_of_Node, long first_Ycoord_of_Node, long last_Xcoord_of_Node, long last_Ycoord_of_Node) {
        Pattern_number_Node = pattern_number_Node;
        Xcoord_of_central_Point_of_Node = xcoord_of_central_Point_of_Node;
        Ycoord_of_central_Point_of_Node = ycoord_of_central_Point_of_Node;
        First_Xcoord_of_Node = first_Xcoord_of_Node;
        First_Ycoord_of_Node = first_Ycoord_of_Node;
        Last_Xcoord_of_Node = last_Xcoord_of_Node;
        Last_Ycoord_of_Node = last_Ycoord_of_Node;
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
                '}';
    }
}


