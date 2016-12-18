package com.wpl.rosesale.Utils;

import java.io.File;

/**
 * 检测文件是否存在
 * Created by 培龙 on 2016/9/12.
 */
public class IsHaveFile {

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
