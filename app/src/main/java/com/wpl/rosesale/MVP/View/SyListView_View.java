package com.wpl.rosesale.MVP.View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/1.
 */

public interface SyListView_View {
    void loadSyProductListItems(List<Map<String, String>> list);

    void loadJrtj(List<Map<String, String>> list);

    void loadVP(List<Map<String, String>> list);
}
