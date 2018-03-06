package icsd.patternlock;

/**
 * Created by Tasos on 3/6/2018.
 */

public class MetadataModelClass {
    String usernmae;
    int Attempt_number;
    int Sequence;
    int Seq_length;
    long Time_to_complete;
    long PatternLenght;
    long Avg_speed;
    float Avg_pressure;
    double Highest_pressure;
    double Lowest_pressure;
    double HandNum; /** 1)left handed 2 right handed OR boolean true -> RightHanded false -> LeftHanded **/
    double FingerNum;/** 1-5 from Tump till Pinky **/
}
