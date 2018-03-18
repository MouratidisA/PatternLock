package icsd.patternlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DisplayStatisticalAnalysisActivity extends AppCompatActivity {

    private TextView Longrun, ClosedCurves, LongCurves, LongEdges, ShortEdges, LongOrthogonalEdgesr, ShortOrthogonalEdges;
    private TextView c1,c2,c3,c4,c5,c6,c7,c8,c9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_statistical_analysis);

        Longrun = findViewById(R.id.LongRuns);
        Longrun.setText(Integer.toString(PatternLockView.LongrunCounter));
        ClosedCurves = findViewById(R.id.ClosedCurves);
        ClosedCurves.setText(Integer.toString(PatternLockView.ClosedCurvesCounter));
        LongCurves = findViewById(R.id.LongCurves);
        LongCurves.setText(Integer.toString(PatternLockView.LongCurvesCounter));
        LongEdges= findViewById(R.id.LongEdges);
        LongEdges.setText(Integer.toString(PatternLockView.LongEdgesCounter));
        ShortEdges=findViewById(R.id.ShortEdges);
        ShortEdges.setText(Integer.toString(PatternLockView.ShortEdgesCounter));
        LongOrthogonalEdgesr=findViewById(R.id.LongOrthogonaledges);
        LongOrthogonalEdgesr.setText(Integer.toString(PatternLockView.LongOrthogonalEdgesCounter));
        ShortOrthogonalEdges=findViewById(R.id.ShortOrthogonaledges);
        ShortOrthogonalEdges.setText(Integer.toString(PatternLockView.ShortOrthogonalEdgesCounter));

        c1=findViewById(R.id.c1);
        c1.setText(Integer.toString(PatternLockView.c1));
        c2=findViewById(R.id.c2);
        c2.setText(Integer.toString(PatternLockView.c2));
        c3=findViewById(R.id.c3);
        c3.setText(Integer.toString(PatternLockView.c3));
        c4=findViewById(R.id.c4);
        c4.setText(Integer.toString(PatternLockView.c4));
        c5=findViewById(R.id.c5);
        c5.setText(Integer.toString(PatternLockView.c5));
        c6=findViewById(R.id.c6);
        c6.setText(Integer.toString(PatternLockView.c6));
        c7=findViewById(R.id.c7);
        c7.setText(Integer.toString(PatternLockView.c7));
        c8=findViewById(R.id.c8);
        c8.setText(Integer.toString(PatternLockView.c8));
        c9=findViewById(R.id.c9);
        c9.setText(Integer.toString(PatternLockView.c9));
    }
}
