package icsd.patternlock;

/**
 * Created by Argiris Mouratidis on 10-Mar-18.
 */

public class PatternMetadataModelClass {

    private String UserName;
    private int AttemtNumber;
    private int[] Sequence;
    private int SeqLength;
    private long TimeToComplete;
    private long PatternLength;
    /**
     * 60% of the recorded points
     **/
    private long AvgSpeed;
    private float AvgPressure;
    private float HighestPressure;
    private float LowestPressure;
    private int HnadNum;
    /**
     * (1)-->Thump till (5)-->Pinky
     **/
    private int FingerNum;

    /**
     * (1)-->Left (2)-->Right
     **/
    public PatternMetadataModelClass(
            String userName, int attemtNumber, int[] sequence,
            int seqLength, long timeToComplete, long patternLength,
            long avgSpeed, float avgPressure, float highestPressure,
            float lowestPressure, int hnadNum, int fingerNum) {
        UserName = userName;
        AttemtNumber = attemtNumber;
        Sequence = sequence;
        SeqLength = seqLength;
        TimeToComplete = timeToComplete;
        PatternLength = patternLength;
        AvgSpeed = avgSpeed;
        AvgPressure = avgPressure;
        HighestPressure = highestPressure;
        LowestPressure = lowestPressure;
        HnadNum = hnadNum;
        FingerNum = fingerNum;
    }
}
