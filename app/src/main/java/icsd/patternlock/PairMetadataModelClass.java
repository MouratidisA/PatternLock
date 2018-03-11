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
    double Distance_AB;
    long Intertime_AB;
    long Avg_speeadAB;
    long Avg_pressure;

    public PairMetadataModelClass(){}

    public PairMetadataModelClass(
            String username, int attempt_number, String screen_resolution, int pattern_number_A,
            int pattern_number_B, long xcoord_of_central_Point_of_A, long ycoord_of_central_Point_of_A,
            long xcoord_of_central_Point_of_B, long ycoord_of_central_Point_of_B, long first_Xcoord_of_A,
            long first_Ycoord_of_A, long last_Xcoord_of_B, long last_Ycoord_of_B, double distance_AB, long intertime_AB,
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

    public String[] getPairMetadataModelClassToStringArray() {

        return new String[]{String.valueOf(Username), String.valueOf(Attempt_number), String.valueOf(Screen_resolution),
                String.valueOf(Pattern_number_A), String.valueOf(Pattern_number_B), String.valueOf(Xcoord_of_central_Point_of_A),
                String.valueOf(Ycoord_of_central_Point_of_A), String.valueOf(Xcoord_of_central_Point_of_B),
                String.valueOf(Ycoord_of_central_Point_of_B),String.valueOf(First_Xcoord_of_A),String.valueOf(First_Ycoord_of_A),
               String.valueOf(Last_Xcoord_of_B), String.valueOf(Last_Ycoord_of_B), String.valueOf(Distance_AB), String.valueOf(Intertime_AB), String.valueOf(Avg_speeadAB),String.valueOf(Avg_pressure)};

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getAttempt_number() {
        return Attempt_number;
    }

    public void setAttempt_number(int attempt_number) {
        Attempt_number = attempt_number;
    }

    public String getScreen_resolution() {
        return Screen_resolution;
    }

    public void setScreen_resolution(String screen_resolution) {
        Screen_resolution = screen_resolution;
    }

    public int getPattern_number_A() {
        return Pattern_number_A;
    }

    public void setPattern_number_A(int pattern_number_A) {
        Pattern_number_A = pattern_number_A;
    }

    public int getPattern_number_B() {
        return Pattern_number_B;
    }

    public void setPattern_number_B(int pattern_number_B) {
        Pattern_number_B = pattern_number_B;
    }

    public long getXcoord_of_central_Point_of_A() {
        return Xcoord_of_central_Point_of_A;
    }

    public void setXcoord_of_central_Point_of_A(long xcoord_of_central_Point_of_A) {
        Xcoord_of_central_Point_of_A = xcoord_of_central_Point_of_A;
    }

    public long getYcoord_of_central_Point_of_A() {
        return Ycoord_of_central_Point_of_A;
    }

    public void setYcoord_of_central_Point_of_A(long ycoord_of_central_Point_of_A) {
        Ycoord_of_central_Point_of_A = ycoord_of_central_Point_of_A;
    }

    public long getXcoord_of_central_Point_of_B() {
        return Xcoord_of_central_Point_of_B;
    }

    public void setXcoord_of_central_Point_of_B(long xcoord_of_central_Point_of_B) {
        Xcoord_of_central_Point_of_B = xcoord_of_central_Point_of_B;
    }

    public long getYcoord_of_central_Point_of_B() {
        return Ycoord_of_central_Point_of_B;
    }

    public void setYcoord_of_central_Point_of_B(long ycoord_of_central_Point_of_B) {
        Ycoord_of_central_Point_of_B = ycoord_of_central_Point_of_B;
    }

    public long getFirst_Xcoord_of_A() {
        return First_Xcoord_of_A;
    }

    public void setFirst_Xcoord_of_A(long first_Xcoord_of_A) {
        First_Xcoord_of_A = first_Xcoord_of_A;
    }

    public long getFirst_Ycoord_of_A() {
        return First_Ycoord_of_A;
    }

    public void setFirst_Ycoord_of_A(long first_Ycoord_of_A) {
        First_Ycoord_of_A = first_Ycoord_of_A;
    }

    public long getLast_Xcoord_of_B() {
        return Last_Xcoord_of_B;
    }

    public void setLast_Xcoord_of_B(long last_Xcoord_of_B) {
        Last_Xcoord_of_B = last_Xcoord_of_B;
    }

    public long getLast_Ycoord_of_B() {
        return Last_Ycoord_of_B;
    }

    public void setLast_Ycoord_of_B(long last_Ycoord_of_B) {
        Last_Ycoord_of_B = last_Ycoord_of_B;
    }

    public double getDistance_AB() {
        return Distance_AB;
    }

    public void setDistance_AB(double distance_AB) {
        Distance_AB = distance_AB;
    }

    public long getIntertime_AB() {
        return Intertime_AB;
    }

    public void setIntertime_AB(long intertime_AB) {
        Intertime_AB = intertime_AB;
    }

    public long getAvg_speeadAB() {
        return Avg_speeadAB;
    }

    public void setAvg_speeadAB(long avg_speeadAB) {
        Avg_speeadAB = avg_speeadAB;
    }

    public long getAvg_pressure() {
        return Avg_pressure;
    }

    public void setAvg_pressure(long avg_pressure) {
        Avg_pressure = avg_pressure;
    }
}
