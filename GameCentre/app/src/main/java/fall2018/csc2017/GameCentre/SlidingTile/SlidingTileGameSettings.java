package fall2018.csc2017.GameCentre.SlidingTile;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import fall2018.csc2017.GameCentre.Utility.FileSaver;
import fall2018.csc2017.GameCentre.Utility.ImageOperation;
import fall2018.csc2017.GameCentre.LoginActivity;
import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.TileNamingInterface;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

/**
 * The sliding tile game settings activity
 **/
public class SlidingTileGameSettings extends AppCompatActivity implements TileNamingInterface {

    /**
     * The request code to pick image.
     */
    public final static int PICK_IMAGE = 1046;
    /**
     * The edit text of number of undo user chose.
     */
    private EditText undoNum;

    /**
     * The edit text of user's choice of url address of the photo.
     */
    private EditText urlAddress;

    private UserAccManager accManager;

    /**
     * Activate all the buttons when SlidingTileGameSettings is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game_settings);
        accManager = (UserAccManager) FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        addConfirmUndoNumButtonListener();
        addUrlButtonListener();
        addChooseFromGalleryButtonListener();
        addSetDefaultTileBackgroundButtonListener();
        addSetBatmanTileBackgroundButtonListener();
        addSetSupermanTileBackgroundButtonListener();
    }

    /**
     * Activate Confirm Button for undo
     */
    private void addConfirmUndoNumButtonListener() {
        ImageButton confirmUndoButton = findViewById(R.id.undoConfirm);
        confirmUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoNum = findViewById(R.id.chooseUndo);

                try {
                    int num = Integer.parseInt(undoNum.getText().toString());
                    accManager.updateUndoTime(num);
                    FileSaver.saveToFile(getApplicationContext(), accManager, LoginActivity.ACC_INFO);
                    makeToastSetUndo(num);
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please type in an Integer",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    /**
     * Activate URL Button for undo
     */
    private void addUrlButtonListener() {
        ImageButton confirmURLButton = findViewById(R.id.URLConfirm);
        confirmURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAddress = findViewById(R.id.urlInput);
                final String address = urlAddress.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(address);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            Bitmap resized = ImageOperation.resizeSourceImage(bmp);
                            cropAndSave(resized);
                            accManager.getAccountMap().get(accManager.getCurrentUser()).
                                    setImageType(UserAccount.ImageType.Imported);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }


    /**
     * Activate set default tile Button
     */
    private void addSetDefaultTileBackgroundButtonListener() {
        Button setDefaultButton = findViewById(R.id.SetDefault);
        setDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accManager.getAccountMap().get(accManager.getCurrentUser()).
                        setImageType(UserAccount.ImageType.Default);
                makeToastChangeBackground();
            }
        });
    }

    /**
     * Activate set batman tile Button
     */
    private void addSetBatmanTileBackgroundButtonListener() {
        Button setBatmanButton = findViewById(R.id.SetBatman);
        setBatmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap batman = BitmapFactory.decodeResource(getResources(), R.drawable.z_batman);
                cropAndSave(batman);
                accManager.getAccountMap().get(accManager.getCurrentUser()).
                        setImageType(UserAccount.ImageType.Resource);
                makeToastChangeBackground();
            }
        });
    }

    /**
     * Activate set Superman tile Button.
     */
    private void addSetSupermanTileBackgroundButtonListener() {
        Button setSupermanButton = findViewById(R.id.SetSuperman);
        setSupermanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap superman = BitmapFactory.decodeResource(getResources(), R.drawable.z_superman);
                cropAndSave(superman);
                accManager.getAccountMap().get(accManager.getCurrentUser()).
                        setImageType(UserAccount.ImageType.Resource);
                makeToastChangeBackground();
            }
        });
    }

    /**
     * Activate choose from gallery Button.
     */
    private void addChooseFromGalleryButtonListener() {
        Button chooseFromGalleryButton = findViewById(R.id.ChooseFromGalleryButton);
        chooseFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });
    }

    /**
     * Recieve pick image request
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri photoUri = data.getData();
                Bitmap selectedImage = MediaStore.Images.Media.
                        getBitmap(this.getContentResolver(), photoUri);
                Bitmap resized = ImageOperation.resizeSourceImage(selectedImage);
                cropAndSave(resized);
                accManager.getAccountMap().get(accManager.getCurrentUser()).
                        setImageType(UserAccount.ImageType.Imported);
                makeToastChangeBackground();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cropAndSave(Bitmap bitmap) {
        for (int gridSize = 3; gridSize <= 5; gridSize++) {
            List<Bitmap> bitmaps = ImageOperation.cropImage(bitmap, gridSize, gridSize);
            for (int id = 1; id <= bitmaps.size(); id++) {
                String tileName = createTileName(gridSize, gridSize, id);
                savePNGToInternalStorage(bitmaps.get(id - 1), accManager.getCurrentUser(), tileName);
            }
        }
    }

    /**
     * Save a given bitmap to internal storage as a PNG file.
     *
     * @param bitmapImage bitmap of the image
     * @param userName    name of the data directory
     * @param fileName    name of the png
     */
    public void savePNGToInternalStorage(Bitmap bitmapImage, String userName, String fileName) {
        ContextWrapper cw = new ContextWrapper(this.getApplicationContext());
        File directory = cw.getDir(userName, MODE_PRIVATE);
        File myPath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath, false);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * create the name of a specific tile.
     *
     * @param numRows board's num rows
     * @param numCols board's num cols
     * @param tileId  tile's id
     * @return generated tile's file name
     */
    @Override
    @NonNull
    public String createTileName(int numRows, int numCols, int tileId) {
        String grid = numRows + "x" + numCols;
        return "tile_" + grid + "_" + tileId + ".png";
    }

    /**
     * Save user preferences when closing.
     */
    @Override
    protected void onStop() {
        super.onStop();
        FileSaver.saveToFile(getApplicationContext(), accManager,
                LoginActivity.ACC_INFO);
        //UserAccManager.getInstance().writeAccManager(getApplicationContext());
    }

    /**
     * Make toast for the background change
     */
    private void makeToastChangeBackground() {
        Toast.makeText(getApplicationContext(),
                "Tile Background Changed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make toast for undo SetUndo
     */
    private void makeToastSetUndo(int i) {
        Toast.makeText(this, "Undo Set To " + i, Toast.LENGTH_SHORT).show();
    }
}
