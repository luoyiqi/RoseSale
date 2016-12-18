package com.wpl.rosesale.MainFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wpl.rosesale.Adapter.FX_ListViewAdapter;
import com.wpl.rosesale.MVP.Presenter.Find_Presenter;
import com.wpl.rosesale.MVP.View.Find_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.ACache;
import com.wpl.rosesale.Utils.IsNetWorkStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/7.
 */
public class FxFragment extends Fragment implements Find_View {
    private View view;
    private SwipeRefreshLayout refresh;
    private ListView listView;
    private Find_Presenter presenter;
    private ACache aCache;
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fx, container, false);
        initUI();
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (IsNetWorkStatus.getActiveNetwork(getContext()) != null) {//有网络
            presenter = new Find_Presenter(this);
            presenter.loadFindData(getContext());
        } else {

            if (aCache.getAsObject("findData") != null) {
                ArrayList arrayList = (ArrayList) aCache.getAsObject("findData");
                Message msg = new Message();
                msg.obj = arrayList;
                handler.sendMessage(msg);
            }
        }

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        aCache = ACache.get(getContext());
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.fx_refresh);
        listView = (ListView) view.findViewById(R.id.fx_listView);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
                FX_ListViewAdapter adapter = new FX_ListViewAdapter(getContext(), list, R.layout.listview_item_fx);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                super.handleMessage(msg);
            }
        };

        refresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light
        );
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        initData();
                    }
                }, 1500);
            }
        });
    }

    @Override
    public void getFindData(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            if (list instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) list;
                aCache.put("findData", arrayList);
            }

            Message msg = new Message();
            msg.obj = list;
            handler.sendMessage(msg);
        }
    }
}
