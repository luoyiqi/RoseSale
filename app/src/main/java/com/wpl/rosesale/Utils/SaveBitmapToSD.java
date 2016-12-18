package com.wpl.rosesale.Utils;

import android.graphics.Bitmap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 将Bitmap对象保存到SD卡
 * Created by 培龙 on 2016/9/14.
 */

public class SaveBitmapToSD {

    /**
     * 将Bitmap对象保存到SD卡
     *
     * @param bitmap    bitmap对象
     * @param imageName 图片名称
     * @param path      保存路径
     * @return 文件保存的路径
     */
    public static String saveBitmapToSD(Bitmap bitmap, String imageName, String path) {
        String imagePath = path +"/"+ imageName + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, fos);
                fos.close();
            }
            return imagePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
