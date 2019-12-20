package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fall2018.csc2017.GameCentre.GameSelectionActivity;
import fall2018.csc2017.GameCentre.R;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

/**
 * Game selection adapter class
 */
class GameSelectionAdapter extends PagerAdapter{

    /**Context of GameSelectionActivity.*/
    private Context context;

    /**
     * The adapter that will display all the games in game center for players to choose
     * from
     *
     * @param context Context for GameSelectionActivity
     */
    GameSelectionAdapter(Context context){
        this.context = context;
    }

    /**An int array of all the game icon drawable id*/
    private int[] slide_game = {
            R.drawable.game_sliding_tile,
            R.drawable.game2048,
            R.drawable.batman_icon_gif
    };

    /**An String array of the description of all games in game center*/
     private String[] slide_headings = {
            "Classic Sliding Tile Game",
            "Can't stop that 2048! ",
            "Oh, is math time!"
    };
    /**An String array of the description of all games in game center*/
    private String[] slideGameName = {
            GameSelectionActivity.GameSlidingTile,
            GameSelectionActivity.Game2048,
            GameSelectionActivity.GameSudoku
    };

    /**
     * Count the number of positions of the Adapter
     */
    @Override
    public int getCount(){return slide_game.length;}

    /**
     * Check if the object is current view
     *
     * @param view the view of ScoreBoardPublicActivity
     * @param o a constrain layout
     */
    @Override
    public boolean isViewFromObject(View view, Object o){
        return view == (ConstraintLayout) o;
    }

    /**
     * Instantiate all the games in  position
     *
     * @param container the container that holds the adapter
     * @param position current position in the container
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.game_selection_pager, container, false);
        ConstraintLayout layoutSlide = (ConstraintLayout) view.findViewById(R.id.slidelinearLayout);
        GifImageButton slideGameButton = (GifImageButton) view.findViewById(R.id.selectGame);
        TextView slideHeader = (TextView) view.findViewById(R.id.gameHeader);
        slideGameButton.setImageResource(slide_game[position]);
        setGameCenterButton(slideGameButton, slideGameName[position]);
        slideHeader.setText(slide_headings[position]);
        container.addView(view);

        return view;
    }

    /**
     * Setup game center button
     *
     * @param button the gif button
     * @param curr_game current_game in the page
     */
    private void setGameCenterButton(GifImageButton button, final String curr_game){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, GameCenterActivity.class);
                i.putExtra("GAME", curr_game);
                context.startActivity(i);
            }
        });
    }

    /**
     * destroy the item in the container at the position given.
     *
     * @param container the container that holds the adapter
     * @param position current position in the container
     * @param object a constrain layout
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }
}
