package fall2018.csc2017.GameCentre.The2048;

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
import android.widget.TextView;

import fall2018.csc2017.GameCentre.R;

/**
 * Gesture detect grid view for 2048
 */
public class The2048GestureDetectGridView extends GridView {

    /**
     * The gesture detector
     */
    private GestureDetector gDetector;

    /**
     * The movement controller
     */
    private The2048MovementController mController;

    /**
     * The board manager
     */
    private The2048BoardManager boardManager;

    /**
     * initialize grid view
     *
     * @param context the context
     */
    public The2048GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * initialize grid view
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public The2048GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * initialize grid view
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr int representing the style attribute.
     */
    public The2048GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public The2048GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * helper method that help initializing the grid view
     *
     * @param context the context
     */
    private void init(Context context) {
        mController = new The2048MovementController();
        The2048GestureListener gListener = new The2048GestureListener();
        gListener.setContext(context);
        gListener.setController(mController);
        gDetector = new GestureDetector(context, gListener);
    }


    /**
     * processing touch event.
     *
     * @param event touch event on the grid view
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * setup board manager for the controller
     *
     * @param boardManager the board manager
     */
    public void setBoardManager(The2048BoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }

    /**
     * process undo
     */
    public void processUndo() {
        mController.processUndo();
    }

    /**
     * update the score if needed.
     */
    public void UpdateScore() {
        TextView score = ((The2048GameActivity) getContext()).findViewById(R.id.scoreTextView);
        score.setText(getResources().getString(R.string.Score,
                String.valueOf(boardManager.getBoard().getScore())));
    }
}

