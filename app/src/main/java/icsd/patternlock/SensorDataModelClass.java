package icsd.patternlock;


public class SensorDataModelClass {
    long timestamp;
    double accel_x, accel_y, accel_z;   // these are the acceleration in x,y and z axis
    double gyro_x, gyro_y, gyro_z;
    double laccel_x, laccel_y, laccel_z;


    public SensorDataModelClass(long timestamp,double accel_x, double accel_y, double accel_z, double gyro_x, double gyro_y, double gyro_z, double laccel_x, double laccel_y, double laccel_z) {
        this.timestamp = timestamp;
        this.accel_x = accel_x;
        this.accel_y = accel_y;
        this.accel_z = accel_z;
        this.gyro_x = gyro_x;
        this.gyro_y = gyro_y;
        this.gyro_z = gyro_z;
        this.laccel_x = laccel_x;
        this.laccel_y = laccel_y;
        this.laccel_z = laccel_z;
    }

    @Override
    public String toString() {
        return "SensorDataModelClass{" +
                "timestamp=" + timestamp +
                "accel_x=" + accel_x +
                ", accel_y=" + accel_y +
                ", accel_z=" + accel_z +
                ", gyro_x=" + gyro_x +
                ", gyro_y=" + gyro_y +
                ", gyro_z=" + gyro_z +
                ", laccel_x=" + laccel_x +
                ", laccel_y=" + laccel_y +
                ", laccel_z=" + laccel_z +
                '}';
    }

    public double getAccel_x() {
        return accel_x;
    }

    public void setAccel_x(double accel_x) {
        this.accel_x = accel_x;
    }

    public double getAccel_y() {
        return accel_y;
    }

    public void setAccel_y(double accel_y) {
        this.accel_y = accel_y;
    }

    public double getAccel_z() {
        return accel_z;
    }

    public void setAccel_z(double accel_z) {
        this.accel_z = accel_z;
    }

    public double getGyro_x() {
        return gyro_x;
    }

    public void setGyro_x(double gyro_x) {
        this.gyro_x = gyro_x;
    }

    public double getGyro_y() {
        return gyro_y;
    }

    public void setGyro_y(double gyro_y) {
        this.gyro_y = gyro_y;
    }

    public double getGyro_z() {
        return gyro_z;
    }

    public void setGyro_z(double gyro_z) {
        this.gyro_z = gyro_z;
    }

    public double getLaccel_x() {
        return laccel_x;
    }

    public void setLaccel_x(double laccel_x) {
        this.laccel_x = laccel_x;
    }

    public double getLaccel_y() {
        return laccel_y;
    }

    public void setLaccel_y(double laccel_y) {
        this.laccel_y = laccel_y;
    }

    public double getLaccel_z() {
        return laccel_z;
    }

    public void setLaccel_z(double laccel_z) {
        this.laccel_z = laccel_z;
    }

    public String[] getSensorPatternObjectToStringArray() {

        return new String[]{String.valueOf(timestamp),String.valueOf(accel_y), String.valueOf(accel_y), String.valueOf(accel_z),
                String.valueOf(gyro_x), String.valueOf(gyro_y), String.valueOf(gyro_z),
                String.valueOf(laccel_x), String.valueOf(laccel_y), String.valueOf(laccel_z)};

    }
}
