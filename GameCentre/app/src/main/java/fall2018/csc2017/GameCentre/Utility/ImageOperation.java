package fall2018.csc2017.GameCentre.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.SudokuTile;
import fall2018.csc2017.GameCentre.tiles.Tile;

/**
 * Some image operation functions.
 */
public class ImageOperation {
    /**
     * The default source image width.
     */
    private static final int SourceImageWidth = 992;
    /**
     * The default source image height.
     */
    private static final int SourceImageHeight = 1276;

    /**
     * Resize the source image into 992x1276 and return the resized image bitmap.
     *
     * @param image the bitmap of the given image
     * @return the resized source image
     */
    public static Bitmap resizeSourceImage(Bitmap image) {
        return Bitmap.createScaledBitmap(image,
                SourceImageWidth, SourceImageHeight, false);
    }

    /**
     * Superpose one bitmap above the centre of one another.
     *
     * @param back  back image
     * @param front front image
     * @return the superposed image
     */
    public static Bitmap superpose(Bitmap back, Bitmap front) {
        Bitmap superposed = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(superposed);
        canvas.drawBitmap(back, new Matrix(), null);
        canvas.drawBitmap(front,
                (back.getWidth() - front.getWidth()) / 2,
                (back.getHeight() - front.getHeight()) / 2,
                null);
        return superposed;
    }

    /**
     * Crop the given image into a given numRows x numCols grid. Then save it into tileDir for later
     * gaming access.
     *
     * @param image   the image bitmap about to be cropped
     * @param numRows the # of rows
     * @param numCols the # of cols
     */
    public static List<Bitmap> cropImage(Bitmap image, int numRows, int numCols) {
        int segWidth = SourceImageWidth / numCols;
        int segHeight = SourceImageHeight / numRows;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Bitmap imgTile = Bitmap.createBitmap(image,
                        j * segWidth, i * segHeight,
                        segWidth, segHeight);
                bitmaps.add(imgTile);
                if (i == numRows - 1 && j == numCols - 2) break;
            }
        }
        return bitmaps;
    }

    /**
     * Get the updated bitmap.
     *
     * @param context the context
     * @param tile    the tile
     * @return the updated bitmap.
     */
    public static Bitmap getUpdatedSudokuTileBackground(Context context, SudokuTile tile) {
        Bitmap blankTile = BitmapFactory.decodeResource(context.getResources(),
                Tile.FirstSudokuTileId + tile.getId() - 1);
        if (!tile.getIsMutable()) {
            Bitmap number = BitmapFactory.decodeResource(context.getResources(),
                    Tile.FirstSudokuNumberId + tile.getValue() - 1);
            return ImageOperation.superpose(blankTile, number);
        } else {
            if (tile.getValue() != 0) {
                Bitmap number = BitmapFactory.decodeResource(context.getResources(),
                        Tile.FirstSudokuEditNumberId + tile.getValue() - 1);
                return ImageOperation.superpose(blankTile, number);
            }
        }
        return blankTile;
    }
}
