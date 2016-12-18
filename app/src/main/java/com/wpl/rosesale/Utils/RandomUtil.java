package com.wpl.rosesale.Utils;

import java.util.Random;

/**
 * Created by 培龙 on 2016/9/10.
 */
public class RandomUtil {

    /**
     * 产生随机的六位数
     *
     * @return
     */
    public static String getSix() {

        Random rad = new Random();
        String result = rad.nextInt(1000000) + "";
        if (result.length() != 6) {
            return getSix();
        }
        return result;
    }
}
