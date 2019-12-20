package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Scores class that categorize and sort user's scores
 */
public class Scores {

    /**
     * Position of name
     */
    static final int namePos = 0;

    /**
     * Position of user's scores corresponding to the user's name.
     */
    static final int scoresPos = 1;

    /**
     * List of account's usernames.
     */
    private List<String> accountNames;

    /**
     * List of scores corresponding to each user in accountNames.
     */
    private List<String> accountScores;

    /**
     * The user account manager.
     */
    private UserAccManager userAccManager;


    /**
     * Construct all the scores for a specific game.
     */
    public Scores(String game, UserAccManager userAccManager) {
        this.userAccManager = userAccManager;
        ArrayList<UserAccount> accList = sortingAccountsScores(game, loadAllAccountInfo());
        List<List<String>> namesAndScores = accountsGetNamesScores(accList, game);
        accountNames = namesAndScores.get(namePos);
        accountScores = namesAndScores.get(scoresPos);
    }

    /**
     * Return the accountNames
     *
     * @return An array list of all account names that have played  and won the game
     */
    public List<String> getAccountNames() {
        return accountNames;
    }

    /**
     * Return the accountScores
     *
     * @return An array list of all the scores of the accounts in accountNames
     */
    public List<String> getAccountScores() {
        return accountScores;
    }


    /**
     * Load all the account information
     *
     * @return an array list of all user accounts
     */
    ArrayList<UserAccount> loadAllAccountInfo() {
        HashMap<String, UserAccount> map = userAccManager.getAccountMap();
        ArrayList<UserAccount> accountList = new ArrayList<>(map.size());
        for (String name : map.keySet()) {
            accountList.add(map.get(name));
        }
        return accountList;
    }

    /**
     * Get user names and their scores in descending order.
     *
     * @param accounts the ArrayList of UserAccount
     * @param gametype the type of the game
     * @return an List of 2 ArrayLists, one representing the name and one representing the scores
     */
    List<List<String>> accountsGetNamesScores(ArrayList<UserAccount> accounts,
                                              String gametype) {
        List<String> names = new ArrayList<>(accounts.size());
        List<String> scores = new ArrayList<>(accounts.size());
        for (UserAccount acc : accounts) {
            names.add(acc.getName());
            scores.add(acc.getScores(gametype).toString());
        }
        List<List<String>> result = new ArrayList<>(2);
        result.add(names);
        result.add(scores);
        return result;
    }

    /**
     * Return a sorted array list of accounts based on the scores from
     * lowest to highest of a particular game.
     *
     * @param game     the name of the game.
     * @param accounts all the accounts of GameCenter
     * @return The sorted list of accounts based on their scores of the game
     */
    ArrayList<UserAccount> sortingAccountsScores(String game, ArrayList<UserAccount> accounts) {
        ArrayList<UserAccount> players = playersOfGame(game, accounts);
        ArrayList<UserAccount> ranks = new ArrayList<>(players.size());
        ArrayList<Integer> playersScoresSorted = new ArrayList<>(players.size());
        //get a sorted list of player's scores
        for (UserAccount acc : players) {
            playersScoresSorted.add(acc.getScores().get(game));
        }
        Collections.sort(playersScoresSorted, Collections.<Integer>reverseOrder());
        addPlayersByRank(game, players, ranks, playersScoresSorted);
        return ranks;

    }

    private void addPlayersByRank(String game, List<UserAccount> players,
                                  List<UserAccount> ranks, List<Integer> playersScoresSorted) {
        for (Integer score : playersScoresSorted) {
            for (UserAccount player : players) {
                if (player.getScores().get(game).equals(score) && !ranks.contains(player)) {
                    ranks.add(player);
                }
            }
        }
    }

    /**
     * Return a ArrayList of UserAccount that have played the game
     *
     * @param game     the game type
     * @param accounts all the accounts of GameCenter
     * @return An array list of accounts that have played this game.
     */
    ArrayList<UserAccount> playersOfGame(String game, ArrayList<UserAccount> accounts) {
        ArrayList<UserAccount> players = new ArrayList<>(accounts.size());
        for (UserAccount acc : accounts) {
            if (acc.getScores().get(game) != -1) {
                players.add(acc);
            }
        }
        return players;
    }


}
