package fall2018.csc2017.GameCentre;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Custom adapter
 */
public class CustomAdapter extends BaseAdapter {
    /**
     * Buttons.
     */
    private ArrayList<? extends View> mButtons = null;

    /**
     * Column's width and height.
     */
    private int mColumnWidth, mColumnHeight;

    /**
     * Initialize the custom adapter.
     *
     * @param buttons      the buttons
     * @param columnWidth  column's width
     * @param columnHeight column's height
     */
    public CustomAdapter(ArrayList<? extends View> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (View) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
