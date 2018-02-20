package icsd.patternlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                if(final_pattern.length()>=4){
                    Toast.makeText(MainActivity.this, final_pattern, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(final_pattern
                            );

                }
               else Toast.makeText(MainActivity.this, "Smol pattern.Use at least 4 nodes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
