package icsd.patternlock;

import android.util.Log;

import java.util.TimerTask;

/**
 * Created by Argiris Mouratidis on 09-Mar-18.
 */

public class Get_TouchCordsThread extends TimerTask {
    private PatternLockView patternLockView;

    public Get_TouchCordsThread(PatternLockView patternLockView) {
        this.patternLockView = patternLockView;

    }

    @Override
    public void run() {
        Log.d("Thread", "Data:" + String.valueOf(patternLockView.getX()) + String.valueOf(patternLockView.getY()));
    }
}
