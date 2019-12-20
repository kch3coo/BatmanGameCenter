package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.GameCentre.Utility.FileSaver;
import pl.droidsonroids.gif.GifImageButton;

/**
 * Score adapter class
 */
class ScoreAdapter extends PagerAdapter{

    /**
     * Context of ScoreBoardPublicActivity.
     */
    Context context;

    /**
     * LayoutInflater
     */
    LayoutInflater layoutInflater;

    /**
     * The adapter that will display players in score board with their names and
     * scores.
     *
     * @param context Context for ScoreBoardPublicActivity
     */
    public ScoreAdapter(Context context){
        this.context = context;
    }

    /**
     * The name array of all the games in GameCenter
     */
    String[] games = UserAccount.GAMES;

    /**
     * Count the number of positions of the Adapter
     */
    @Override
    public int getCount(){return UserAccount.GAMES.length;}

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
     * instantiate all the items in  position
     *
     * @param container the container that holds the adapter
     * @param position current position in the container
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //
        View view = layoutInflater.inflate(R.layout.score_selection_pager, container, false);
        UserAccManager userAccManager = (UserAccManager) FileSaver.
                loadFromFile(context, LoginActivity.ACC_INFO);
        Scores scores = new Scores(games[position], userAccManager);
        TextView slideHeader = (TextView) view.findViewById(R.id.gametype);
        slideHeader.setText(games[position]);
        castNameToView((ArrayList<String>) scores.getAccountNames(), view);
        castScoresToView((ArrayList<String>) scores.getAccountScores(), view);

        container.addView(view);

        return view;
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

    /**
     * It will cast the ArrayList listName to the name listView.
     * @param listName list of user names.
     * @param view this view
     */
    private void castNameToView(ArrayList<String> listName, View view){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, listName);
        ListView list = view.findViewById(R.id.gameViewName);
        list.setAdapter(adapter);
    }

    /**
     *
     * It will cast the ArrayList listScore to the score listView.
     * @param listScores the list of scores
     * @param view this view
     */
    private void castScoresToView (ArrayList<String> listScores, View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, listScores);
        ListView list = view.findViewById(R.id.gameViewScores);
        list.setAdapter(adapter);
    }

}
