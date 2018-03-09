package icsd.patternlock;
import android.util.Log;

/**
 * Created by Argiris Mouratidis on 09-Mar-18.
 */

public class SomeBackgroundProcess implements Runnable {

    Thread backgroundThread;

    public void start() {
        if( backgroundThread == null ) {
            backgroundThread = new Thread( this );
            backgroundThread.start();
        }
    }

    public void stop() {
        if( backgroundThread != null ) {
            backgroundThread.interrupt();
        }
    }

    public void run() {
      //  try {
            Log.d("my app ","Thread starting.");
            while( !backgroundThread.interrupted() ) {
                Log.d("my app ","aaaaaaaaa" );
            }
            Log.d("my app ","Thread stopping.");
       // } catch( InterruptedException ex ) {
            // important you respond to the InterruptedException and stop processing
            // when its thrown!  Notice this is outside the while loop.
            Log.d("my app ","Thread shutting down as it was requested to stop.");
      //  } finally {
       //     backgroundThread = null;
        }
    }
