package icsd.patternlock;

/**
 * Created by Tasos on 3/6/2018.
 */

public class PairMetadataModelClass {
    String Username;
    int Attempt_number;
    String Screen_resolution;
    /**
     * Must deside type of that var
     **/
    int Pattern_number_A;
    int Pattern_number_B;
    long Xcoord_of_central_Point_of_A;
    long Ycoord_of_central_Point_of_A;
    long Xcoord_of_central_Point_of_B;
    long Ycoord_of_central_Point_of_B;
    long First_Xcoord_of_A;
    long First_Ycoord_of_A;
    long Last_Xcoord_of_B;
    long Last_Ycoord_of_B;
    long Distance_AB;
    long Intertime_AB;
    long Avg_speeadAB;
    long Avg_pressure;

    public PairMetadataModelClass(
            String username, int attempt_number, String screen_resolution, int pattern_number_A,
            int pattern_number_B, long xcoord_of_central_Point_of_A, long ycoord_of_central_Point_of_A,
            long xcoord_of_central_Point_of_B, long ycoord_of_central_Point_of_B, long first_Xcoord_of_A,
            long first_Ycoord_of_A, long last_Xcoord_of_B, long last_Ycoord_of_B, long distance_AB, long intertime_AB,
            long avg_speeadAB, long avg_pressure) {
        Username = username;
        Attempt_number = attempt_number;
        Screen_resolution = screen_resolution;
        Pattern_number_A = pattern_number_A;
        Pattern_number_B = pattern_number_B;
        Xcoord_of_central_Point_of_A = xcoord_of_central_Point_of_A;
        Ycoord_of_central_Point_of_A = ycoord_of_central_Point_of_A;
        Xcoord_of_central_Point_of_B = xcoord_of_central_Point_of_B;
        Ycoord_of_central_Point_of_B = ycoord_of_central_Point_of_B;
        First_Xcoord_of_A = first_Xcoord_of_A;
        First_Ycoord_of_A = first_Ycoord_of_A;
        Last_Xcoord_of_B = last_Xcoord_of_B;
        Last_Ycoord_of_B = last_Ycoord_of_B;
        Distance_AB = distance_AB;
        Intertime_AB = intertime_AB;
        Avg_speeadAB = avg_speeadAB;
        Avg_pressure = avg_pressure;
    }

}
