package fall2018.csc2017.GameCentre.Sudoku;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Gesture detect grid view for Sudoku
 */
public class SudokuGestureDetectGridView extends GridView {

    /**
     * The minimum swipe distance to activate
     */
    public static final int SWIPE_MIN_DISTANCE = 100;

    /**
     * The gesture detector
     */
    private GestureDetector gDetector;

    /**
     * The movement controller
     */
    private SudokuMovementController mController;

    /**
     * The fling confirmed detector
     */
    private boolean mFlingConfirmed = false;

    /**
     * Movement on the X axis
     */
    private float mTouchX;

    /**
     * Movement on the Y axis
     */
    private float mTouchY;

    /**
     * Constructor that initialize the grid view.
     *
     * @param context current context
     */
    public SudokuGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Another constructor that takes in attribute set
     *
     * @param context context
     * @param attrs   attribute set
     */
    public SudokuGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Another constructor that takes in attribute set and style attribute
     *
     * @param context      context
     * @param attrs        attribute set
     * @param defStyleAttr style attribute
     */
    public SudokuGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * initialize grid view(based on API 21)
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr int representing the style attribute.
     * @param defStyleRes  int representing the style Res.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public SudokuGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                       int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * helper method that help initializing the grid view
     *
     * @param context the context
     */
    private void init(final Context context) {
        mController = new SudokuMovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = SudokuGestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    /**
     * Touch event on intercept
     *
     * @param ev touch event
     * @return whether it's canceled or not on intercept.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * processing touch event.
     *
     * @param ev touch event on the grid view
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Set the board manager.
     *
     * @param boardManager board manager to set
     */
    public void setBoardManager(SudokuBoardManager boardManager) {
        mController.setBoardManager(boardManager);
    }
}
