package icsd.patternlock;

/**
 * Created by Tasos on 3/6/2018.
 */

public class RawPatternModelClass {
    int number_of_activated_point;
    long timestamp;
    long x;
    long y;
    float pressure;

    public RawPatternModelClass(int number_of_activated_point, long timestamp, long x, long y, float pressure) {
        this.number_of_activated_point = number_of_activated_point;
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "RawPatternModelClass{" +
                "number_of_activated_point=" + number_of_activated_point +
                ", timestamp=" + timestamp +
                ", x=" + x +
                ", y=" + y +
                ", pressure=" + pressure +
                '}';
    }

     public String [] getRawPatternObjectToStringArray(){
         return new String []{ String.valueOf(number_of_activated_point),String.valueOf(timestamp),String.valueOf( x) ,String.valueOf( y ),String.valueOf(pressure)};

     }

    public int getNumber_of_activated_point() {
        return number_of_activated_point;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
