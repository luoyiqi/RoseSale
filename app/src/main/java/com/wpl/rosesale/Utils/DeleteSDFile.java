package com.wpl.rosesale.Utils;

import java.io.File;

/**
 * 删除SD卡上的内容
 * Created by 培龙 on 2016/9/15.
 */

public class DeleteSDFile {

    /**
     * 删除文件
     *
     * @param filePath 文件的完整路径
     */
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }
}
