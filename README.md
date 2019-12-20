# README

This is a guide on how to run our programs, as well as introducing multiple features implement it
and how it is implemented.

## How to run the app
After the application BatGameCenter is installed on the phone, you can register as a new user.
Just simply type in your username and password to register. Then, click on to Login to get into the
game center. You can select three games which are Sliding Tiles(1), Sudoku(2) and 2048(3) by swiping screen.

Notice the top joker image is the back button, you can log out from the game selection activity or
you can use it to go back to the previous activity. After you have selected a game and enter the game center, the top right gif button will
lead you to the score board.
(1). In Sliding Tile, you can select three sizes of the Sliding Tile game to play. If you want to change the in-game tile
picture or change the undo numbers, you can go to settings to change them. In game, you can tab the tiles that are beside
the empty space to swap the tile. Your goal is to sort out the picture or the number given to you.
If you want to redo a step, just click on the empty tile to undo, but you will lose one undo chance in the mean time.
(2). In Sudoku, you will see a 9 by 9 board begin with several numbers given to you. You can press any blank position to make
it be number one, press one more time to make it be number two and so on. Once you have reached nine,
you can tab that position again to go back to one. Your goal is to make each horizontal line
and vertical line(total 81 lines, each line has 9 positions)'s position has all of number from 1 to 9.
(3). In 2048, you will see a 4 by 4 board begin with two randomly distributed tiles with the value 2 or 4. Then, you can swipe the board in
four directions (up, down, right, left) and the tiles will swipe to this direction. When two tiles have the same value, they will be
merged to one tile that has doubled value. After each move, one of the blank position will randomly appear a 2 or 4 tiles.
Your goal is to merge enough tiles as you can, the score at the top will show how many tiles of value you merged (when two tiles 
merged to a new tile, this new tile's value will be added to your score). Moreover, if you want to undo your move, just press 
the undo button below. You can undo as many as you want; however, your score will not only minus the score added by this move, 
but also minus 100 as the penalty in case some users will always undo the same move in oder to get a "great" randomly position 
tile as they want (the score will not below 0). It follows relatively the same as the commonly known 2048.

All the players that are register on the same device will share the same scoreboard. If you haven't play nor won a game,
you will not have your score shown on the scoreboard. The Scoreboard is located in GameCenter,
you will have to click on the bat on the moon to access it.


## Features
Overall app description: We have a batman theme GameCenter application, after the user logged in,
the player can select 3 sizes of sliding tile game on the right to play from.
When the user returns back to the game center, user can choose to load or
save the previous game. If the player is not pleased with the look of the sliding tile game,
he/she can go to setting and select either default, superman, batman, to change the look of the sliding tile game.
The player can also choose upload to use the image from image gallery on the phone, or to paste an url of a picture to set the image of the sliding tile games.
Furthermore, the player can also select the numbers of the undo they want in this setting page.
After the player have won a game, the scoreboard will show the highest scores of the player.

### Game Launch Centre:
#### Login/Register: 
There are 3 parts involved in a successful login/register process. 1, Each user's account is an UserAccount object, which has a password and username. Their score for each game is initially set at -1, so that their score won't appear on the scoreboard. They are able to update their score.
2, UserAccManager, the manager is in charge of fetching and updating data for all the users, it can also check if a user exists for login purposes. Each user's username is mapped to its UserAccount in a HashMap stored in UserAccManager. It can load/populate its own hashMap from the local account storage file.
3, LoginActivity, which is an activity class that are in charge of getting user's input from EditText, and translate it into Strings and stored them into UserAccManager, and store UserAccManager object into local storage.

Registering with a same username is not allowed.

Registering with blank username/password is not allowed.

#### Start new game/Load previous game
After logging in, user can choose to start a new game by selecting game complexity at the top, or load previously saved game by clicking the "load game" button.

#### Auto save
Auto save is also implemented, in the update method in GameActivity class, every time when the observable board updated, the observer GameActivity will be notified and save the game into local storage. And since number of undos left is stored inside the board object as well, it will also be stored.
But auto save is only updated when the board is updated, therefore the first time creating a new game, without making any move will not auto save the board's status. In order to save a untouched board, user will have to manually click the save button on the game center menu. Every time when a board is solved, the game save will be erased since a solved board is meaningless, if the user really want to save it, again, user will have to manually save it.


#### Undo method:
We implement stack in java. When we make moves, the stack will save some information from previous board (information depends on games you choose)
In sliding tile. When user chose the number of undos they want in settings, unlike choosing background tile image in settings, the number of undos is user-specific, meaning the maximum undo number is saved along with other information in this user's user account, and is saved in local storage.
In 2048, users can make infinity undo.

#### Scoring System:
In sliding tile:
User make one movement => move + 1 (start from 0)
User undo one move => score unchanged
When game over, calculate the score, the score is calculated by 1000\*(grid_size)/moves, for example if a user made 3 moves plus 3 undos on a 3X3 sliding tile game, his/her score will be 1000\*9/3 = 3000
Therefore, user who gets the highest score is the best player, and score board will display in a descending order.
And scores don't differ by a lot when amount of moves made are large, but differ by a quite significant amount if amount of moves made are small.

In Sudoku:
Since each board has similar difficulties (same number of tiles removed), every time when player solve one sudoku, they gain 1 point as their score.
First time solving a Sudoku will made your score 0 and start displaying your name on the score board.

In 2048:
User make one movement => score added by sum of all merged new tile's value
User undo one move => score goes back to previous board's score and minus 100 for penalty 
For example, user makes a move that merges two tiles have value of 16 to one tile has value of 32 and nothing else merges. The score will 
add 32. Assume before this move, the score is 200. Then after this move, score will be 232. When user press undo, the score will be last 
score 200 and minus 100 as penalty, that is, 100.

#### Select Different Image:
In the in-game-setting page, we have three buttons called DEFAULT, BATMAN and SUPERMAN respectively. Each of these buttons links to a default source image (or a set of tile images).
After clicking the button, the source image will be cropped into 8, 15 and 24 pieces and these images will overwrite tile_gridSize x gridSize_id.png, and thus it will be set as new image background.

#### Select Image from local gallery:
First, we need an image from the local gallery. By clicking "UPLOAD UR OWN PHOTO" you can access the selecting page. After clicking the image you want to use, it will be cropped and the
tile images will be saved into device's internal storage. After going back to the GameCenter, and clicking 3x3, 4x4 or 5x5, you will find all the tile backgrounds are updated.

#### Select Image using URL:
We can copy a random image path from internet, then paste it to the corresponding edit text location. By clicking the click button below, the image will be cropped as cropping image from gallery.
However, the change will not be show on toast. Nonetheless, the tile background image will still be updated if it is a valid image URL.
