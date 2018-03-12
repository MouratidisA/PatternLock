package icsd.patternlock;

import icsd.patternlock.MainActivity.*;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.opencsv.CSVWriter;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static icsd.patternlock.MainActivity.username;

/**
 * PatternLockView support two layout mode:
 * PatternLockView 支持两种布局模式：
 * <p>
 * 1. SpacingPadding mode:
 * If lock_spacing is given, PatternLockView use lock_nodeSize, lock_spacing and lock_padding to layout the view.
 * Detail Rules:
 * a. Use exactly lock_nodeSize, spacing and lock_padding to layout. If insufficient space, try b.
 * b. Keep lock_nodeSize, reduce lock_spacing and lock_padding with equal proportion. If insufficient space, try c.
 * c. Keep lock_spacing and lock_padding, reduce lock_nodeSize. If insufficient space, try d.
 * d. Apply Identical-Area mode.
 * <p>
 * 如果设置了lock_spacing时，PatternLockView会使用lock_nodeSize, lock_spacing, lock_padding去布局
 * 具体布局规则如下：
 * a.精确按照lock_nodeSize, lock_spacing, lock_padding去布局进行布局，如果空间不足采用b规则；
 * b.保持lock_nodeSize大小不变，按比例缩小lock_spacing与lock_padding去布局，如果spacing与padding空间小于0，采用c规则；
 * c.保持lock_spacing与lock_padding，缩小lock_nodeSize，如果lock_nodeSize小于0，采用d规则；
 * d.采用Identical-Area mode；
 * <p>
 * 2. Identical-Area mode:
 * If lock_spacing is NOT given, PatternLockView only use lock_nodeSize to layout the view(lock_spacing and lock_padding are ignored).
 * It divides the whole area into n * n identical cells, and layout the node in the center of each cell
 * <p>
 * 如果未设置lock_spacing时，PatternLockView将只使用lock_nodeSize，而无视lock_spacing与lock_padding去布局。
 * 其会将空间等分为n * n个空间，并将节点居中放置
 *
 * @author xyxyLiu
 * @version 1.0
 */
public class PatternLockView extends ViewGroup {
    /**
     * password correct
     * 解锁正确
     */
    public static final int CODE_PASSWORD_CORRECT = 1;
    /**
     * password error
     * 解锁错误
     */
    public static final int CODE_PASSWORD_ERROR = 2;

    private static final String TAG = "PatternLockView";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    // attributes that can be configured with code (non-persistent)
    private boolean mIsTouchEnabled = true;
    private long mFinishTimeout = 1000;
    private boolean mIsFinishInterruptable = true;
    private boolean mIsAutoLink;

    private List<NodeView> mNodeList = new ArrayList<>();
    private NodeView currentNode;
    private float mPositionX;
    private float mPositionY;

    private Drawable mNodeSrc;
    private Drawable mNodeHighlightSrc;
    private Drawable mNodeCorrectSrc;
    private Drawable mNodeErrorSrc;

    private int mSize;

    private float mNodeAreaExpand;
    private int mNodeOnAnim;
    private float mLineWidth;

    private int mLineColor;
    private int mLineCorrectColor;
    private int mLineErrorColor;

    private float mNodeSize;
    // only used in Identical-Area mode, whether to keep each square
    private boolean mIsSquareArea = true;
    private float mPadding;
    private float mSpacing;
    private float mMeasuredPadding;
    private float mMeasuredSpacing;


    private Vibrator mVibrator;
    private boolean mEnableVibrate;
    private int mVibrateTime;

    private boolean mIsPatternVisible = true;

    private Paint mPaint;

    private CallBack mCallBack;

    private OnNodeTouchListener mOnNodeTouchListener;

    private Runnable mFinishAction = new Runnable() {
        @Override
        public void run() {
            reset();
            setTouchEnabled(true);
        }
    };


    /**
     * OUR CODE HERE!!
     **/
    public ArrayList<Point> PointList = new ArrayList<>();
    public ArrayList<Integer> NodeList = new ArrayList<>();
    public ArrayList<RawPatternModelClass> RawPatternList = new ArrayList<>();
    public ArrayList<SensorDataModelClass> SensorPatternList = new ArrayList<>();
    public ArrayList<PairMetadataModelClass> PairMetaDataList=new ArrayList<>();
    public static int Attempt=1;
    public long TimeStart, TimeEnd, TimeToComplete;


    public PatternLockView(Context context) {
        this(context, null);
    }

    public PatternLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PatternLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromAttributes(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PatternLockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFromAttributes(context, attrs, defStyleAttr);
    }

    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public void setOnNodeTouchListener(OnNodeTouchListener callBack) {
        this.mOnNodeTouchListener = callBack;
    }

    public void setTouchEnabled(boolean isEnabled) {
        mIsTouchEnabled = isEnabled;
    }

    public boolean isPatternVisible() {
        return mIsPatternVisible;
    }

    public void setPatternVisible(boolean isVisible) {
        mIsPatternVisible = isVisible;
        invalidate();
    }

    /**
     * time delayed of the lock view resetting after user finish input password
     *
     * @param timeout timeout
     */
    public void setFinishTimeout(long timeout) {
        if (timeout < 0)
            timeout = 0;
        mFinishTimeout = timeout;
    }

    /**
     * whether user can start a new password input in the period of FinishTimeout
     *
     * @param isInterruptable if true, the lock view will be reset when user touch a new node.
     *                        if false, the lock view will be reset only when the finish timeout expires
     * @see #setFinishTimeout(long)
     */
    public void setFinishInterruptable(boolean isInterruptable) {
        mIsFinishInterruptable = isInterruptable;
    }

    /**
     * whether the nodes in the path of two selected nodes will be automatic linked
     *
     * @param isEnabled enabled
     */
    public void setAutoLinkEnabled(boolean isEnabled) {
        mIsAutoLink = isEnabled;
    }

    public void setSize(int size) {
        mSize = size;
        setupNodes(size);
    }

    /**
     * reset the view, reset nodes states and clear all lines.
     */
    public void reset() {
        mNodeList.clear();
        currentNode = null;

        for (int n = 0; n < getChildCount(); n++) {
            NodeView node = (NodeView) getChildAt(n);
            node.setState(NodeView.STATE_NORMAL);
        }

        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);

        invalidate();
    }

    private void initFromAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PatternLockView, defStyleAttr, 0);

        mSize = a.getInt(R.styleable.PatternLockView_lock_size, 3);
        mNodeSrc = a.getDrawable(R.styleable.PatternLockView_lock_nodeSrc);
        mNodeHighlightSrc = a.getDrawable(R.styleable.PatternLockView_lock_nodeHighlightSrc);
        mNodeCorrectSrc = a.getDrawable(R.styleable.PatternLockView_lock_nodeCorrectSrc);
        mNodeErrorSrc = a.getDrawable(R.styleable.PatternLockView_lock_nodeErrorSrc);
        mNodeSize = a.getDimension(R.styleable.PatternLockView_lock_nodeSize, 0);
        mNodeAreaExpand = a.getDimension(R.styleable.PatternLockView_lock_nodeTouchExpand, 0);
        mNodeOnAnim = a.getResourceId(R.styleable.PatternLockView_lock_nodeOnAnim, 0);
        mLineColor = a.getColor(R.styleable.PatternLockView_lock_lineColor, Color.argb(0xb2, 0xff, 0xff, 0xff));
        mLineCorrectColor = a.getColor(R.styleable.PatternLockView_lock_lineCorrectColor, mLineColor);
        mLineErrorColor = a.getColor(R.styleable.PatternLockView_lock_lineErrorColor, mLineColor);
        mLineWidth = a.getDimension(R.styleable.PatternLockView_lock_lineWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        mPadding = a.getDimension(R.styleable.PatternLockView_lock_padding, 0);
        mSpacing = a.getDimension(R.styleable.PatternLockView_lock_spacing, -1);
        mIsAutoLink = a.getBoolean(R.styleable.PatternLockView_lock_autoLink, false);

        mEnableVibrate = a.getBoolean(R.styleable.PatternLockView_lock_enableVibrate, false);
        mVibrateTime = a.getInt(R.styleable.PatternLockView_lock_vibrateTime, 20);

        a.recycle();

        if (mNodeSize <= 0) {
            throw new IllegalStateException("nodeSize must be provided and larger than zero!");
        }

        if (mEnableVibrate) {
            mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }

        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);

        setupNodes(mSize);

        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean needRemeasure = false;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int gaps = mSize - 1;
        float nodesize = mNodeSize;
        mMeasuredPadding = mPadding;
        mMeasuredSpacing = mSpacing;
        float maxNodeWidth, maxNodeHeight, maxNodeSize;

        if (DEBUG) {
            Log.v(TAG, String.format("onMeasure(), raw width = %d, height = %d)", width, height));
        }

        // Spacing&Padding mode:
        if (mSpacing >= 0) {
            maxNodeWidth = ((width - mPadding * 2 - mSpacing * gaps) / mSize);
            maxNodeHeight = ((height - mPadding * 2 - mSpacing * gaps) / mSize);
            maxNodeSize = maxNodeWidth < maxNodeHeight ? maxNodeWidth : maxNodeHeight;
            if (DEBUG) {
                Log.v(TAG, String.format("maxNodeWidth = %f, maxNodeHeight = %f, maxNodeSize = %f)",
                        maxNodeWidth, maxNodeHeight, maxNodeSize));
            }

            // if maximum available nodesize if smaller than desired nodesize with paddings & spacing unchanged
            if (nodesize > maxNodeSize) {
                int xRemains = (int) (width - mSize * nodesize);
                int yRemains = (int) (height - mSize * nodesize);
                int minRemains = xRemains < yRemains ? xRemains : yRemains;
                int paddingsAndSpacings = (int) (mPadding * 2 + mSpacing * gaps);

                if (DEBUG) {
                    Log.d(TAG, String.format("xRemains = %d, yRemains = %d, before shrink: mPadding = %f, mSpacing = %f",
                            xRemains, yRemains, mPadding, mSpacing));
                }
                // keep nodesize & shrink paddings and spacing if there are enough space
                if (minRemains > 0 && paddingsAndSpacings > 0) {
                    float shrinkRatio = (float) minRemains / paddingsAndSpacings;
                    mMeasuredPadding *= shrinkRatio;
                    mMeasuredSpacing *= shrinkRatio;
                    if (DEBUG) {
                        Log.d(TAG, String.format("shrinkRatio = %f, mMeasuredPadding = %f, mMeasuredSpacing = %f",
                                shrinkRatio, mMeasuredPadding, mMeasuredSpacing));
                    }
                } else { // otherwise shrink nodesize & keep paddings and spacing
                    nodesize = maxNodeSize;
                }
            } else {
                if (!isMeasureModeExactly(widthMode)) {
                    width = (int) (mPadding * 2 + mSpacing * gaps + mSize * nodesize);
                }

                if (!isMeasureModeExactly(heightMode)) {
                    height = (int) (mPadding * 2 + mSpacing * gaps + mSize * nodesize);
                }
            }


            // if result nodesize is smaller than zero, remeasure without using spacings.
            if (nodesize <= 0) {
                needRemeasure = true;
                // remeasure without using mSpacing
                if (DEBUG) {
                    Log.v(TAG, String.format("remeasure without using mSpacing"));
                }
            }
        }

        // Identical-Area mode:
        // if no spacing is provided, divide the whole area into 9 identical area for each node
        if (needRemeasure || mSpacing < 0) {
            mMeasuredSpacing = -1;
            nodesize = mNodeSize;
            maxNodeWidth = width / mSize;
            maxNodeHeight = height / mSize;
            maxNodeSize = maxNodeWidth < maxNodeHeight ? maxNodeWidth : maxNodeHeight;
            if (DEBUG) {
                Log.v(TAG, String.format("maxNodeWidth = %f, maxNodeHeight = %f, maxNodeSize = %f)",
                        maxNodeWidth, maxNodeHeight, maxNodeSize));
            }

            // if maximum available nodesize if smaller than desired nodesize
            if (nodesize > maxNodeSize) {
                nodesize = maxNodeSize;
            }
        }

        if (DEBUG) {
            Log.v(TAG, String.format("measured nodeSize = %f)", nodesize));
        }

        if (width > height && !isMeasureModeExactly(widthMode)) {
            width = height;
        } else if (width < height && !isMeasureModeExactly(heightMode)) {
            height = width;
        }

        setMeasuredDimension(width, height);

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int widthSpec = MeasureSpec.makeMeasureSpec((int) nodesize, MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec.makeMeasureSpec((int) nodesize, MeasureSpec.EXACTLY);
            v.measure(widthSpec, heightSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int gaps = mSize - 1;
        int height = bottom - top;
        int width = right - left;
        float nodeSize = getChildAt(0).getMeasuredWidth();

        // Identical-Area mode:
        if (mMeasuredSpacing < 0) {
            float areaWidth = width / mSize;
            float areaHeight = height / mSize;
            float areaSize = areaWidth < areaHeight ? areaWidth : areaHeight;
            float widthPadding = 0f;
            float heightPadding = 0f;
            // whether to keep each cell as square (width = height)
            if (mIsSquareArea) {
                areaWidth = areaSize;
                areaHeight = areaSize;
                widthPadding = (width - mSize * areaSize) / 2;
                heightPadding = (height - mSize * areaSize) / 2;
            }
            if (DEBUG) {
                Log.v(TAG, String.format("nodeSize = %f, areaWidth = %f, areaHeight = %f, widthPadding = %f, heightPadding = %f",
                        nodeSize, areaWidth, areaHeight, widthPadding, heightPadding));
            }

            for (int n = 0; n < mSize * mSize; n++) {
                NodeView node = (NodeView) getChildAt(n);
                int row = n / mSize;
                int col = n % mSize;
                int l = (int) (widthPadding + col * areaWidth + (areaWidth - node.getMeasuredWidth()) / 2);
                int t = (int) (heightPadding + row * areaHeight + (areaHeight - node.getMeasuredHeight()) / 2);
                int r = (int) (l + node.getMeasuredWidth());
                int b = (int) (t + node.getMeasuredHeight());
                node.layout(l, t, r, b);
            }
        } else { // Spacing&Padding mode:
            float widthPadding = (width - mSize * nodeSize - mMeasuredSpacing * gaps) / 2;
            float heightPadding = (height - mSize * nodeSize - mMeasuredSpacing * gaps) / 2;
            if (DEBUG) {
                Log.v(TAG, String.format("nodeSize = %f, widthPadding = %f, heightPadding = %f",
                        nodeSize, widthPadding, heightPadding));
            }
            for (int n = 0; n < mSize * mSize; n++) {
                NodeView node = (NodeView) getChildAt(n);
                int row = n / mSize;
                int col = n % mSize;
                int l = (int) (widthPadding + col * (nodeSize + mMeasuredSpacing));
                int t = (int) (heightPadding + row * (nodeSize + mMeasuredSpacing));
                int r = (int) (l + nodeSize);
                int b = (int) (t + nodeSize);
                node.layout(l, t, r, b);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Touch Point (x,y)", String.valueOf(event.getRawX()) + "---" + String.valueOf(event.getRawY()));
        PointList.add(new Point(event.getRawX(), event.getRawY(), event.getPressure()));

        if (!mIsTouchEnabled || !isEnabled()) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mIsFinishInterruptable && mFinishAction != null) {
                    removeCallbacks(mFinishAction);
                    mFinishAction.run();
                }
            case MotionEvent.ACTION_MOVE:
                mPositionX = event.getX();
                mPositionY = event.getY();
                /**Start time of Pattern**/
                TimeStart = SystemClock.elapsedRealtimeNanos();

                NodeView nodeAt = getNodeAt(mPositionX, mPositionY);

                if (currentNode == null) {
                    if (nodeAt != null) {
                        Log.d("EEEEEEEEEEEEEE", "nodeAt-->" + nodeAt.getNodeId());
                        NodeList.add(nodeAt.getNodeId() + 1);


                        currentNode = nodeAt;
                        currentNode.setState(NodeView.STATE_HIGHLIGHT);
                        addNodeToList(currentNode);
                        invalidate();
                    }
                } else {
                    if (nodeAt != null && !nodeAt.isHighLighted()) {
                        Log.d("oooooooooooooo", "nodeAt-->" + nodeAt.getNodeId());
                        NodeList.add(nodeAt.getNodeId() + 1);

                        if (mIsAutoLink) {
                            autoLinkNode(currentNode, nodeAt);
                        }
                        currentNode = nodeAt;
                        currentNode.setState(NodeView.STATE_HIGHLIGHT);
                        addNodeToList(currentNode);
                    }
                    /**Addind Raw Data  Here !!**/
                    SensorPatternList.add(MainActivity.GetSensors());
                    RawPatternList.add(new RawPatternModelClass(NodeList.get(NodeList.size() - 1), SystemClock.elapsedRealtimeNanos(), (long) event.getRawX(), (long) event.getRawY(), event.getPressure()));
                    invalidate();

                }

                break;
            case MotionEvent.ACTION_UP:
                if (mNodeList.size() > 0) {

                    if (!mIsFinishInterruptable) {
                        setTouchEnabled(false);
                    }

                    if (mCallBack != null) {
                        int result = mCallBack.onFinish(new Password(mNodeList));
                        setFinishState(result);
                    }

                    currentNode = null;
                    invalidate();
                    postDelayed(mFinishAction, mFinishTimeout);

                    /****/
                    /**End time of Pattern**/
                    TimeEnd = SystemClock.elapsedRealtimeNanos();
                    TimeToComplete = TimeEnd - TimeStart;
                    // NodeSequence from NodeList without duplicates for PatternMetadataModeClass
                    Set<Integer> hs = new LinkedHashSet<>(NodeList);
                    hs.addAll(NodeList);
                    ArrayList<Integer> tempList = new ArrayList<>();
                    tempList.addAll(hs);
                    int[] NodeSequence = new int[hs.size()];
                    for (int i = 0; i < hs.size(); i++) {
                        NodeSequence[i] = tempList.get(i);
                        Log.d("AIAIAIAIAIIAIA", String.valueOf(NodeSequence[i]));
                    }
                    int SequenceLength = NodeSequence.length;
                    String username= MainActivity.GetUsername();
                    Log.d("oooooooooooooo", "nodeAt-->" + RawPatternList.toString());
                    /** Write RawPattern to a file **/
                    /**Get users name as filename **/
                    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

                    String fileName = username+"_"+Attempt+"_"+"raw.csv";
                    String filePath = baseDir + File.separator + fileName;

                    /** Writing Raw Pattern data to CSV file**/
                    for (int i = 0; i < RawPatternList.size(); i++) {
                        writeCSV(filePath, RawPatternList.get(i).getRawPatternObjectToStringArray());
                    }
                    /**Writing Sensor data to CSV file**/
                    String SensorfileName = username+"_"+Attempt+"_"+"sensors.csv";
                    String SensorfilePath = baseDir + File.separator + SensorfileName;
                    for (int i = 0; i < SensorPatternList.size(); i++) {
                        writeCSV(SensorfilePath, SensorPatternList.get(i).getSensorPatternObjectToStringArray());
                    }
                    /**CLEANING the list for next Pattern**/
                    Attempt++;
                    /**TODO Make attempt number change every pattern that has at least 4 nodes!!
                     /* PairNode Data for PairNodeModelClass!*/
                    ArrayList<PairNodeModelClass> PairNodeModelClassList = new ArrayList<>();
                    RawPatternModelClass first_record_of_a_node = null, last_record_of_a_node = null;


                    for (int i = 0; i < RawPatternList.size(); i++) {
                        first_record_of_a_node = RawPatternList.get(i); // The first record of a Node
                        //searching till the last record of the node
                        int PressureCount = 0; // count the number of recorder points' pressure
                        float PressureSum = 0; // sum of all points' pressure on the same Node
                        int j = i;
                        while (RawPatternList.get(j).getNumber_of_activated_point() == first_record_of_a_node.getNumber_of_activated_point()) {
                            // if next RawPatternModelClass Number of Node differs from the current Number of node then pass current to temp2 as the last record of that Node
                            i = j;
                            PressureCount++;
                            PressureSum += RawPatternList.get(j).getPressure();
                            last_record_of_a_node = RawPatternList.get(j);
                            int tp = j;
                            tp++;
                            if (tp == RawPatternList.size()) {
                                //i = j;
                                break;
                            } else {
                                j++;
                            }
                        }
                        /**Adding The first and the last record of the Node to a PairNodeModelClass**/
                        PairNodeModelClassList.add(
                                new PairNodeModelClass(
                                        first_record_of_a_node.getNumber_of_activated_point(),
                                        GetCenterNodeX(first_record_of_a_node.getNumber_of_activated_point()),
                                        GetCenterNodeY(first_record_of_a_node.getNumber_of_activated_point()),
                                        first_record_of_a_node.getX(),
                                        first_record_of_a_node.getY(),
                                        last_record_of_a_node.getX(),
                                        last_record_of_a_node.getY(),
                                        last_record_of_a_node.getTimestamp(),
                                        first_record_of_a_node.getTimestamp(),
                                        PressureSum, PressureCount));


                    }
                    /** Here we will take the list with PairNodeModeClass items to create the PairMetadata file **/
                    CreatePairMetadataCsv(PairNodeModelClassList);
                    /**Writing PairMetadata to CSV file**//*
                    String PairMetadatafileName = "PairMetadatafile.csv";
                    String PairMetadatafilePath = baseDir + File.separator + PairMetadatafileName;
                    for (int i = 0; i < PairMetaDataList.size(); i++) {
                        writeCSV(PairMetadatafilePath, PairMetaDataList.get(i).getPairMetadataModelClassToStringArray()) ;
                    }*/
                    for (int i = 0; i < PairNodeModelClassList.size(); i++) {
                        Log.d("Last Record of Node", PairNodeModelClassList.get(i).toString());
                    }
                    /**Molis teleiwsoun ta 26 attempts tha prepei na midenizete to Attempt kai na adeiazoun ta stoixeia tou xrisi**/
                    MainActivity.setAttempt(Attempt);
                    SensorPatternList.clear();
                    RawPatternList.clear();
                    NodeList.clear();


                }
                break;
        }
        return true;
    }

    public void CreatePairMetadataCsv(ArrayList<PairNodeModelClass> PairNodeModelClassList) {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        //TODO Note that for each user, your app should create only one CSV file that
        //TODO has as many rows as the inserted patterns. For example, if the user inserted 30 patterns then the
        //TODO metadata file should have 30 rows with the generated metrics of each pattern (+1 for the header
        String PairMetadatafileName = username+"_"+Attempt+"_"+"PairMetadatafile.csv";
        String PairMetadatafilePath = baseDir + File.separator + PairMetadatafileName;

        PairMetadataModelClass pairMetadataModelClass = new PairMetadataModelClass();
        //UserName
        pairMetadataModelClass.setUsername(MainActivity.GetUsername());
        // Attempt Number
        pairMetadataModelClass.setAttempt_number(Attempt);
        //Get Screen Resolution
        pairMetadataModelClass.setScreen_resolution(MainActivity.GetScreenResolution());
        //Getting information about Pairs in list NodeA to NodeB
        for (int i = 0; i < PairNodeModelClassList.size() - 1; i++) {
            //Pattern Number of NodeA and NodeB
            pairMetadataModelClass.setPattern_number_A(PairNodeModelClassList.get(i).getPattern_number_Node());
            pairMetadataModelClass.setPattern_number_B(PairNodeModelClassList.get(i + 1).getPattern_number_Node());
            //Central Point for Node A
            pairMetadataModelClass.setXcoord_of_central_Point_of_A(PairNodeModelClassList.get(i).getXcoord_of_central_Point_of_Node());
            pairMetadataModelClass.setYcoord_of_central_Point_of_A(PairNodeModelClassList.get(i).getYcoord_of_central_Point_of_Node());
            //Central Point for Node B
            pairMetadataModelClass.setXcoord_of_central_Point_of_B(PairNodeModelClassList.get(i + 1).getXcoord_of_central_Point_of_Node());
            pairMetadataModelClass.setYcoord_of_central_Point_of_B(PairNodeModelClassList.get(i + 1).getYcoord_of_central_Point_of_Node());
            //First point of Node A
            pairMetadataModelClass.setFirst_Xcoord_of_A(PairNodeModelClassList.get(i).getFirst_Xcoord_of_Node());
            pairMetadataModelClass.setFirst_Ycoord_of_A(PairNodeModelClassList.get(i).getFirst_Ycoord_of_Node());
            //Last point of Node B
            pairMetadataModelClass.setLast_Xcoord_of_B(PairNodeModelClassList.get(i + 1).getLast_Xcoord_of_Node());
            pairMetadataModelClass.setLast_Ycoord_of_B(PairNodeModelClassList.get(i + 1).getLast_Ycoord_of_Node());

            double x2 = PairNodeModelClassList.get(i + 1).getLast_Xcoord_of_Node();
            double x1 = PairNodeModelClassList.get(i).getFirst_Xcoord_of_Node();
            double y2 = PairNodeModelClassList.get(i + 1).getLast_Ycoord_of_Node();
            double y1 = PairNodeModelClassList.get(i).getFirst_Ycoord_of_Node();
            double distance = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
            pairMetadataModelClass.setDistance_AB(Math.sqrt(distance));

            long intertime = PairNodeModelClassList.get(i + 1).getLastTimestamp() - PairNodeModelClassList.get(i).getFirstTimestamp();
            pairMetadataModelClass.setIntertime_AB(intertime);

            pairMetadataModelClass.setAvg_speeadAB((long) distance / intertime);

            pairMetadataModelClass.setAvg_pressure((long) (PairNodeModelClassList.get(i).getNodePressure() + PairNodeModelClassList.get(i + 1).getNodePressure()) / (PairNodeModelClassList.get(i).getTouchPointsCount() + PairNodeModelClassList.get(i + 1).getTouchPointsCount()));
            /**Writing PairMetadata row to CSV file**/
                writeCSV(PairMetadatafilePath,  pairMetadataModelClass.getPairMetadataModelClassToStringArray()) ;
        }

    }

    private void setupNodes(int size) {
        removeAllViews();
        for (int n = 0; n < size * size; n++) {
            NodeView node = new NodeView(getContext(), n);
            addView(node);
        }
    }

    private void setFinishState(int result) {
        int nodeState = -1;
        int lineColor = mLineColor;

        if (result == CODE_PASSWORD_CORRECT) {
            nodeState = NodeView.STATE_CORRECT;
            lineColor = mLineCorrectColor;
        } else if (result == CODE_PASSWORD_ERROR) {
            nodeState = NodeView.STATE_ERROR;
            lineColor = mLineErrorColor;
        }

        if (nodeState >= 0) {
            for (NodeView nodeView : mNodeList) {
                nodeView.setState(nodeState);
            }
        }

        if (lineColor != mLineColor) {
            mPaint.setColor(lineColor);
        }
    }

    private void addNodeToList(NodeView nodeView) {
        mNodeList.add(nodeView);
        if (mOnNodeTouchListener != null) {
            mOnNodeTouchListener.onNodeTouched(nodeView.getNodeId());
        }
    }

    /**
     * auto link the nodes between first and second
     * 检测两个节点间的中间检点是否未连接，否则按顺序连接。
     *
     * @param first
     * @param second
     */
    private void autoLinkNode(NodeView first, NodeView second) {
        if (DEBUG) {
            Log.d(TAG, String.format("autoLinkNode(%s, %s)", first, second));
        }
        int xDiff = second.getColumn() - first.getColumn();
        int yDiff = second.getRow() - first.getRow();
        if (yDiff == 0 && xDiff == 0) {
            return;
        } else if (yDiff == 0) {
            int row = first.getRow();
            int step = xDiff > 0 ? 1 : -1;
            int column = first.getColumn();
            while ((column += step) != second.getColumn()) {
                tryAppendMidNode(row, column);
            }
        } else if (xDiff == 0) {
            int column = first.getColumn();
            int step = yDiff > 0 ? 1 : -1;
            int row = first.getRow();
            while ((row += step) != second.getRow()) {
                tryAppendMidNode(row, column);
            }
        } else {
            float tan = yDiff / (float) xDiff;
            int xstep = xDiff > 0 ? 1 : -1;
            if (DEBUG) {
                Log.d(TAG, String.format("tan = %f, xstep = %d", tan, xstep));
            }
            int xDelta = 0;
            float yDelta = 0f;
            while ((xDelta += xstep) != xDiff) {
                yDelta = xDelta * tan;
                int yDeltaRounded = Math.round(yDelta);
                if (DEBUG) {
                    Log.d(TAG, String.format("xDelta = %d, yDelta = %f", xDelta, yDelta));
                }
                if (Math.abs(yDelta - yDeltaRounded) < 1e-6) {
                    tryAppendMidNode(first.getRow() + yDeltaRounded, first.getColumn() + xDelta);
                    break;
                }
            }
        }
    }

    private void tryAppendMidNode(int row, int column) {
        if (DEBUG) {
            Log.d(TAG, String.format("tryAppendMidNode(row = %d, column = %d)", row, column));
        }
        NodeView mid = (NodeView) getChildAt(row * mSize + column);
        if (mNodeList.contains(mid))
            return;
        mid.setState(NodeView.STATE_HIGHLIGHT);
        addNodeToList(mid);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mNodeList.size() - 1; i++) {
            NodeView first = mNodeList.get(i);
            NodeView second = mNodeList.get(i + 1);
            drawPatternLine(canvas, first.getCenterX(), first.getCenterY(), second.getCenterX(), second.getCenterY());
        }
        if (currentNode != null) {
            drawPatternLine(canvas, currentNode.getCenterX(), currentNode.getCenterY(), mPositionX, mPositionY);
        }
    }

    private void drawPatternLine(Canvas canvas, float startX, float startY, float endX, float endY) {
        if (mIsPatternVisible) {
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
    }

    private NodeView getNodeAt(float x, float y) {
        for (int n = 0; n < getChildCount(); n++) {
            NodeView node = (NodeView) getChildAt(n);
            if (!(x >= node.getLeft() - mNodeAreaExpand && x < node.getRight() + mNodeAreaExpand)) {
                continue;
            }
            if (!(y >= node.getTop() - mNodeAreaExpand && y < node.getBottom() + mNodeAreaExpand)) {
                continue;
            }
            return node;
        }
        return null;
    }

    private boolean isMeasureModeExactly(int measureMode) {
        return measureMode == MeasureSpec.EXACTLY;
    }

    /**
     * Callback to handle pattern input complete event
     * 密码处理返回接口
     */
    public interface CallBack {
        /**
         * @param password password
         * @return return value 解锁结果返回值：
         * {@link #CODE_PASSWORD_CORRECT},
         * {@link #CODE_PASSWORD_ERROR},
         * @see com.reginald.patternlockview.PatternLockView.Password
         */
        int onFinish(Password password);
    }

    /**
     * Callback to handle node touch event
     * 节点点击回调监听器接口
     */
    public interface OnNodeTouchListener {
        void onNodeTouched(int NodeId);

    }

    private class NodeView extends View {

        public static final int STATE_NORMAL = 0;
        public static final int STATE_HIGHLIGHT = 1;
        public static final int STATE_CORRECT = 2;
        public static final int STATE_ERROR = 3;

        private int mId;
        private int mState = STATE_NORMAL;

        public NodeView(Context context, int num) {
            super(context);
            this.mId = num;
            setBackgroundDrawable(mNodeSrc);
        }

        public boolean isHighLighted() {
            return mState == STATE_HIGHLIGHT;
        }

        public void setState(int state) {

            if (mState == state)
                return;

            switch (state) {

                case STATE_NORMAL:
                    setBackgroundDrawable(mNodeSrc);
                    clearAnimation();
                    break;
                case STATE_HIGHLIGHT:
                    if (mNodeHighlightSrc != null) {
                        setBackgroundDrawable(mNodeHighlightSrc);
                    }
                    if (mNodeOnAnim != 0) {
                        startAnimation(AnimationUtils.loadAnimation(getContext(), mNodeOnAnim));
                    }

                    if (mEnableVibrate) {
                        mVibrator.vibrate(mVibrateTime);
                    }
                    break;
                case STATE_CORRECT:
                    if (mNodeCorrectSrc != null) {
                        setBackgroundDrawable(mNodeCorrectSrc);
                    }
                    break;
                case STATE_ERROR:
                    if (mNodeErrorSrc != null) {
                        setBackgroundDrawable(mNodeErrorSrc);
                    }
                    break;
            }
            mState = state;
        }

        public int getCenterX() {
            return (getLeft() + getRight()) / 2;
        }

        public int getCenterY() {
            return (getTop() + getBottom()) / 2;
        }

        public int getNodeId() {
            return mId;
        }

        public int getRow() {
            return mId / mSize;
        }

        public int getColumn() {
            return mId % mSize;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NodeView && mId == ((NodeView) obj).getNodeId()) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("NodeView[mId = %d, row = %d, column = %d]", mId, getRow(), getColumn());
        }
    }

    public static class Password {
        public final List<Integer> list;
        public final String string;

        public Password(List<NodeView> nodeViewList) {
            // build password id list
            list = new ArrayList<>();
            for (NodeView node : nodeViewList) {
                list.add(node.getNodeId());
            }

            // build password string
            string = buildPasswordString(nodeViewList);
        }

        protected String buildPasswordString(List<NodeView> nodeViewList) {
            StringBuilder passwordBuilder = new StringBuilder("[");
            for (int i = 0; i < nodeViewList.size(); i++) {
                int id = nodeViewList.get(i).getNodeId();
                if (i != 0) {
                    passwordBuilder.append("-");
                }
                passwordBuilder.append(id);

            }
            passwordBuilder.append("]");
            return passwordBuilder.toString();
        }
    }

    public void writeCSV(String filePath, String[] data) {
        //https://stackoverflow.com/questions/17645092/export-my-data-on-csv-file-from-app-android
        /* String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "AnalysisData.csv";
        String filePath = baseDir + File.separator + fileName;*/
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(filePath, true));
            writer.writeNext(data);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public long GetCenterNodeX(int NodeNum) {
        return (long) 0.001;
    }

    public long GetCenterNodeY(int NodeNum) {
        return (long) 0.002;
    }

}