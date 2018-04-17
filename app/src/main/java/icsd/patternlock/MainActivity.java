package icsd.patternlock;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    public static double accel_x, accel_y, accel_z;     // these are the acceleration in x,y and z axis
    public static double gyro_x, gyro_y, gyro_z;        //gyroscope axis
    public static double laccel_x, laccel_y, laccel_z;  //linear acceleration
    public static int screenheight, screenwidth;
    public static ArrayList<String> Longrun = new ArrayList<>();
    public static ArrayList<String> ClosedCurves = new ArrayList<>();
    public static ArrayList<String> LongCurves = new ArrayList<>();
    public static ArrayList<String> LongEdges = new ArrayList<>();
    public static ArrayList<String> ShortEdges = new ArrayList<>();
    public static ArrayList<String> LongOrthogonalEdges = new ArrayList<>();
    public static ArrayList<String> ShortOrthogonalEdges = new ArrayList<>();
    public static ArrayList<String> CommonPatterns=new ArrayList<>();
    public static String username;
    public static int handnum;               //Number corresponding to users hand 1-2
    public static String fingernum;          //Number corresponding to users finger 1-5
    private float[] gravity = new float[3];  //auxiliary array to calculate linear acceleration
    private SensorManager mSensorManager;
    private Sensor mAccelerometer, mGyroscope;


    public EditText UserName;
    public static TextView Attempt;
    private static final String TAG = "PatternLock";
    public static PatternLockView mCircleLockView;
    private PatternLockView mCurLockView;
    private Switch hand;                     //Hand toggle switch
    private RadioGroup radioGroup;          //Finger button radio group
    private Button submit, statisticalAnalysis;
    private RadioButton radioButton;
    public static void setAttempt(int attempts) {
        Attempt.setText(String.valueOf(attempts));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Statistical analysis lists
        Longrun.add("123");
        Longrun.add("321");
        Longrun.add("456");
        Longrun.add("654");
        Longrun.add("789");
        Longrun.add("987");
        Longrun.add("147");
        Longrun.add("741");
        Longrun.add("258");
        Longrun.add("852");
        Longrun.add("369");
        Longrun.add("963");
        Longrun.add("159");
        Longrun.add("951");
        Longrun.add("357");
        Longrun.add("753");

        ClosedCurves.add("1452");
        ClosedCurves.add("2541");
        ClosedCurves.add("1254");
        ClosedCurves.add("4521");
        ClosedCurves.add("4125");
        ClosedCurves.add("5214");
        ClosedCurves.add("5412");
        ClosedCurves.add("2145");
        ClosedCurves.add("2563");
        ClosedCurves.add("3652");
        ClosedCurves.add("2365");
        ClosedCurves.add("5632");
        ClosedCurves.add("5236");
        ClosedCurves.add("6325");
        ClosedCurves.add("3256");
        ClosedCurves.add("6523");
        ClosedCurves.add("4785");
        ClosedCurves.add("5874");
        ClosedCurves.add("7458");
        ClosedCurves.add("8547");
        ClosedCurves.add("5478");
        ClosedCurves.add("8745");
        ClosedCurves.add("4587");
        ClosedCurves.add("7854");
        ClosedCurves.add("5896");
        ClosedCurves.add("6985");
        ClosedCurves.add("8560");
        ClosedCurves.add("0658");
        ClosedCurves.add("6580");
        ClosedCurves.add("0856");
        ClosedCurves.add("8065");
        ClosedCurves.add("5608");

        LongCurves.add("147852");
        LongCurves.add("258741");
        LongCurves.add("123654");
        LongCurves.add("456321");
        LongCurves.add("258963");
        LongCurves.add("369852");
        LongCurves.add("456987");
        LongCurves.add("789654");

        LongEdges.add("741258");
        LongEdges.add("32147");
        LongEdges.add("14753");
        LongEdges.add("35741");
        LongEdges.add("75321");
        LongEdges.add("12357");
        LongEdges.add("35789");
        LongEdges.add("98753");
        LongEdges.add("75369");
        LongEdges.add("96357");
        LongEdges.add("78963");
        LongEdges.add("36987");

        ShortEdges.add("451");
        ShortEdges.add("154");
        ShortEdges.add("542");
        ShortEdges.add("245");
        ShortEdges.add("142");
        ShortEdges.add("241");
        ShortEdges.add("415");
        ShortEdges.add("514");
        ShortEdges.add("124");
        ShortEdges.add("421");
        ShortEdges.add("215");
        ShortEdges.add("512");
        ShortEdges.add("425");
        ShortEdges.add("524");
        ShortEdges.add("152");
        ShortEdges.add("251");
        ShortEdges.add("265");//
        ShortEdges.add("562");
        ShortEdges.add("356");
        ShortEdges.add("653");
        ShortEdges.add("253");
        ShortEdges.add("352");
        ShortEdges.add("526");
        ShortEdges.add("625");
        ShortEdges.add("235");
        ShortEdges.add("532");
        ShortEdges.add("326");
        ShortEdges.add("623");
        ShortEdges.add("635");
        ShortEdges.add("536");
        ShortEdges.add("263");
        ShortEdges.add("362");
        ShortEdges.add("487");//
        ShortEdges.add("784");
        ShortEdges.add("578");
        ShortEdges.add("875");
        ShortEdges.add("475");
        ShortEdges.add("574");
        ShortEdges.add("748");
        ShortEdges.add("847");
        ShortEdges.add("457");
        ShortEdges.add("754");
        ShortEdges.add("548");
        ShortEdges.add("845");
        ShortEdges.add("758");
        ShortEdges.add("857");
        ShortEdges.add("485");
        ShortEdges.add("584");
        ShortEdges.add("598");//
        ShortEdges.add("895");
        ShortEdges.add("689");
        ShortEdges.add("986");
        ShortEdges.add("586");
        ShortEdges.add("685");
        ShortEdges.add("859");
        ShortEdges.add("958");
        ShortEdges.add("568");
        ShortEdges.add("865");
        ShortEdges.add("659");
        ShortEdges.add("956");
        ShortEdges.add("869");
        ShortEdges.add("968");
        ShortEdges.add("596");
        ShortEdges.add("695");


        LongOrthogonalEdges.add("14789");
        LongOrthogonalEdges.add("98741");
        LongOrthogonalEdges.add("12369");
        LongOrthogonalEdges.add("96321");
        LongOrthogonalEdges.add("32147");
        LongOrthogonalEdges.add("74123");
        LongOrthogonalEdges.add("78963");
        LongOrthogonalEdges.add("36987");

        ShortOrthogonalEdges.add("145");
        ShortOrthogonalEdges.add("541");
        ShortOrthogonalEdges.add("412");
        ShortOrthogonalEdges.add("214");
        ShortOrthogonalEdges.add("125");
        ShortOrthogonalEdges.add("521");
        ShortOrthogonalEdges.add("254");
        ShortOrthogonalEdges.add("452");
        ShortOrthogonalEdges.add("256");
        ShortOrthogonalEdges.add("652");
        ShortOrthogonalEdges.add("523");
        ShortOrthogonalEdges.add("325");
        ShortOrthogonalEdges.add("236");
        ShortOrthogonalEdges.add("632");
        ShortOrthogonalEdges.add("365");
        ShortOrthogonalEdges.add("563");
        ShortOrthogonalEdges.add("478");
        ShortOrthogonalEdges.add("874");
        ShortOrthogonalEdges.add("745");
        ShortOrthogonalEdges.add("547");
        ShortOrthogonalEdges.add("458");
        ShortOrthogonalEdges.add("854");
        ShortOrthogonalEdges.add("587");
        ShortOrthogonalEdges.add("785");
        ShortOrthogonalEdges.add("589");
        ShortOrthogonalEdges.add("985");
        ShortOrthogonalEdges.add("856");
        ShortOrthogonalEdges.add("658");
        ShortOrthogonalEdges.add("569");
        ShortOrthogonalEdges.add("965");
        ShortOrthogonalEdges.add("698");
        ShortOrthogonalEdges.add("896");

        CommonPatterns.add("12369");
        CommonPatterns.add("1236");
        CommonPatterns.add("14789");
        CommonPatterns.add("5789");
        CommonPatterns.add("2369");
        CommonPatterns.add("4789");
        CommonPatterns.add("2486");
        CommonPatterns.add("35789");
        CommonPatterns.add("1478");
        CommonPatterns.add("2589");


        UserName = (EditText) findViewById(R.id.UserName);
        Attempt = findViewById(R.id.Attempt);
        hand = findViewById(R.id.hand);
        radioGroup = (RadioGroup) findViewById(R.id.finger);
        statisticalAnalysis = findViewById(R.id.StatisticalAnalysis);
        statisticalAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, DisplayStatisticalAnalysisActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selection = null;
                if (!hand.isChecked()) {
                    //If the toggle switch is off then the users choose to use his left hand
                    handnum = 1;
                    Log.d("handnum", Integer.toString(handnum));

                } else {
                    //If the toggle switch is on then the users choose to use his righthand hand
                    handnum = 2;
                    Log.d("handnum", Integer.toString(handnum));
                }
                username = UserName.getText().toString();
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    //Radio button grooup for selecting
                    //1-Thumb 2-Index 3-Middle finger 4-Ring finger 5-Pinky
                    int id = radioGroup.getCheckedRadioButtonId();
                    View radioButton = radioGroup.findViewById(id);
                    int radioId = radioGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                    selection = (String) btn.getText();
                    fingernum = selection;
                }
                int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(checkedRadioButton);

            }

        });

        //Initialize sensors
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);


        mCircleLockView = (PatternLockView) findViewById(R.id.lock_view_circle);
        mCurLockView = mCircleLockView;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenheight = metrics.heightPixels;
        screenwidth = metrics.widthPixels;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        /**
         _____ ______ _   _  _____  ____  _____   _____
         / ____|  ____| \ | |/ ____|/ __ \|  __ \ / ____|
         | (___ | |__  |  \| | (___ | |  | | |__) | (___
         \___ \|  __| | . ` |\___ \| |  | |  _  / \___ \
         ____) | |____| |\  |____) | |__| | | \ \ ____) |
         |_____/|______|_| \_|_____/ \____/|_|  \_|_____/
         **/
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accel_x = event.values[0];
            accel_y = event.values[1];
            accel_z = event.values[2];


            //https://stackoverflow.com/questions/20935587/how-to-properly-calculate-linear-acceleration-using-accelerometer-in-android
            // alpha is calculated as t / (t + dT)
            // with t, the low-pass filter's time-constant
            // and dT, the event delivery rate
            final float alpha = 0.8f;

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            laccel_x = event.values[0] - gravity[0];
            laccel_y = event.values[1] - gravity[1];
            laccel_z = event.values[2] - gravity[2];

        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyro_x = event.values[0];
            gyro_y = event.values[1];
            gyro_z = event.values[2];

        }

    }

    public static SensorDataModelClass GetSensors() {
        SensorDataModelClass sensorDataModelClass = new SensorDataModelClass(SystemClock.elapsedRealtimeNanos(), accel_x, accel_y, accel_z, gyro_x, gyro_y, gyro_z, laccel_x, laccel_y, laccel_z);
        return sensorDataModelClass;
    }

    public static String GetUsername() {
        return username;
    }

    public static int GetHandNumber() {
        return handnum;
    }

    public static String GetFingerNumber() {
        return fingernum;
    }

    public static String GetScreenResolution() {
        return Integer.toString(screenheight) + "x" + Integer.toString(screenwidth);
    }
    
}
