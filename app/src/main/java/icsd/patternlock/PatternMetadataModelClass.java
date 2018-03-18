package icsd.patternlock;

import java.util.Arrays;

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
    private double AvgSpeed;
    private float AvgPressure;
    private float HighestPressure;
    private float LowestPressure;
    private int HnadNum;
    /**
     * (1)-->Thump till (5)-->Pinky
     **/
    private String FingerNum;

    /**
     * (1)-->Left (2)-->Right
     **/
    public PatternMetadataModelClass(
            String userName, int attemtNumber, int[] sequence,
            int seqLength, long timeToComplete, long patternLength,
            double avgSpeed, float avgPressure, float highestPressure,
            float lowestPressure, int hnadNum, String fingerNum) {
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

    public String[] getPatternMetadataModelClassToStringArray() {
        return new String[]{String.valueOf(UserName), String.valueOf(AttemtNumber),Arrays.toString(Arrays.toString(Sequence).split("[\\[\\]]")[1].split(", ")) ,
                String.valueOf(SeqLength), String.valueOf(TimeToComplete), String.valueOf(PatternLength),
                String.valueOf(AvgSpeed), String.valueOf(AvgPressure),
                String.valueOf(HighestPressure), String.valueOf(LowestPressure), String.valueOf(HnadNum),
                String.valueOf(FingerNum)};
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getAttemtNumber() {
        return AttemtNumber;
    }

    public void setAttemtNumber(int attemtNumber) {
        AttemtNumber = attemtNumber;
    }

    public int[] getSequence() {
        return Sequence;
    }

    public void setSequence(int[] sequence) {
        Sequence = sequence;
    }

    public int getSeqLength() {
        return SeqLength;
    }

    public void setSeqLength(int seqLength) {
        SeqLength = seqLength;
    }

    public long getTimeToComplete() {
        return TimeToComplete;
    }

    public void setTimeToComplete(long timeToComplete) {
        TimeToComplete = timeToComplete;
    }

    public long getPatternLength() {
        return PatternLength;
    }

    public void setPatternLength(long patternLength) {
        PatternLength = patternLength;
    }

    public double getAvgSpeed() {
        return AvgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        AvgSpeed = avgSpeed;
    }

    public float getAvgPressure() {
        return AvgPressure;
    }

    public void setAvgPressure(float avgPressure) {
        AvgPressure = avgPressure;
    }

    public float getHighestPressure() {
        return HighestPressure;
    }

    public void setHighestPressure(float highestPressure) {
        HighestPressure = highestPressure;
    }

    public float getLowestPressure() {
        return LowestPressure;
    }

    public void setLowestPressure(float lowestPressure) {
        LowestPressure = lowestPressure;
    }

    public int getHnadNum() {
        return HnadNum;
    }

    public void setHnadNum(int hnadNum) {
        HnadNum = hnadNum;
    }

    public String getFingerNum() {
        return FingerNum;
    }

    public void setFingerNum(String fingerNum) {
        FingerNum = fingerNum;
    }
}
