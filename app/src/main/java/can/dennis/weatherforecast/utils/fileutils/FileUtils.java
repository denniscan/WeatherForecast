package can.dennis.weatherforecast.utils.fileutils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import can.dennis.weatherforecast.utils.app.MyApp;
import can.dennis.weatherforecast.utils.closeableutils.CloseableUtils;
import can.dennis.weatherforecast.utils.constants.Constants;
/**
 * File utils
 * Created by Dennis Can on 2017-07-03.
 */
public class FileUtils {
	public static final String TEMP_FILE_END = ".temp";

	private static FileUtils instance = new FileUtils();

	public static FileUtils getInstance() { return instance; }

	private FileUtils() { }

	@NonNull public File getBingImageFile() {
		return new File(MyApp.CACHE_IMAGE_PATH, Constants.BING_CACHE_IMAGE_FILE_NAME);
	}

	public boolean saveBitmapAsFile(Bitmap bitmap, File targetFile) {
		FileOutputStream out = null;
		File tempFile = new File(targetFile.getAbsolutePath() + TEMP_FILE_END);
		try {
			out = new FileOutputStream(tempFile);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out))
				return !(targetFile.exists() && !targetFile.delete()) && tempFile.renameTo(targetFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.close(out);
		}
		return false;
	}
}
