package fall2018.csc2017.GameCentre.Utility;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * The file save utility class.
 */
public class FileSaver {

    /**
     * Save an object to file in fileLocation.
     *
     * @param context      the context.
     * @param obj          the object to be saved,
     * @param fileLocation the file location to be saved to.
     */
    public static void saveToFile(Context context, Object obj, String fileLocation) {
        try {
            ObjectOutputStream os =
                    new ObjectOutputStream(context.openFileOutput(fileLocation, MODE_PRIVATE));
            os.writeObject(obj);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load an object from fileLocation
     *
     * @param context      the context
     * @param fileLocation the file location
     * @return that object that's loaded from fileLocation.
     */
    public static Object loadFromFile(Context context, String fileLocation) {
        try {
            return loadFile(context, fileLocation);
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return null;
        } catch (IOException ioe) {
            Log.e("login activity", "Can not read file: " + ioe.toString());
            return null;
        } catch (ClassNotFoundException cne) {
            Log.e("login activity", "File contained unexpected data type: " +
                    cne.toString());
            return null;
        }
    }

    /**
     * The helper method for loadFromFile(), that loads an object.
     *
     * @param c            context
     * @param fileLocation the file location.
     * @return the object that's being loaded.
     * @throws IOException            In/Out exception.
     * @throws ClassNotFoundException class not found exception.
     */
    public static Object loadFile(Context c, String fileLocation) throws IOException,
            ClassNotFoundException {
        Object temp = new Object();
        InputStream inputStream = c.openFileInput(fileLocation);
        if (inputStream != null) {
            ObjectInputStream input = new ObjectInputStream(inputStream);
            temp = input.readObject();
            inputStream.close();
        } else {
            Toast.makeText(c, "saves not found, loaded default", Toast.LENGTH_LONG).show();
        }
        return temp;
    }


}
