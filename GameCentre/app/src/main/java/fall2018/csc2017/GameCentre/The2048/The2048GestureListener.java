package fall2018.csc2017.GameCentre.The2048;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Gesture listener for 2048
 */
class The2048GestureListener extends GestureDetector.SimpleOnGestureListener {

    /**
     * Min distance of fling
     */
    private float flingMin = 100;
    /**
     * min speed of fling
     */
    private float velocityMin = 100;
    /**
     * movement controller
     */
    private The2048MovementController mController;
    /**
     * context
     */
    private Context context;

    /**
     * set up the context
     *
     * @param context the context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * set up movement controller
     *
     * @param mController the movement controller
     */
    void setController(The2048MovementController mController) {
        this.mController = mController;
    }

    /**
     * unused method from the parent class
     *
     * @param e motion event
     * @return false
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * process the sliding movement
     *
     * @param e1        event 1
     * @param e2        event 2
     * @param velocityX velocity in x direction
     * @param velocityY velocity in y direction
     * @return true
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float horizontalDiff = e2.getX() - e1.getX();
        float verticalDiff = e2.getY() - e1.getY();
        float absHDiff = Math.abs(horizontalDiff);
        float absVDiff = Math.abs(verticalDiff);
        float absVelocityX = Math.abs(velocityX);
        float absVelocityY = Math.abs(velocityY);

        if (absHDiff > absVDiff && absHDiff > flingMin && absVelocityX > absVelocityY) {
            if (horizontalDiff > 0) {
                //swipe right
                mController.processMovement("row", true);
            } else {
                //swipe left
                mController.processMovement("row", false);
            }
        } else if (absVDiff > flingMin && absVelocityY > velocityMin) {
            if (verticalDiff > 0) {
                //swipe down
                mController.processMovement("column", true);
            } else {
                //swipe up
                mController.processMovement("column", false);
            }
        }
        return true;
    }
}
