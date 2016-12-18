package com.wpl.rosesale.MVP.Listener;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/1.
 */

public interface SyListView_Listener {
    void setSyProductListItems(List<Map<String, String>> list);

    void setJrtj(List<Map<String, String>> list);

    void setVP(List<Map<String, String>> list);
}
