package com.wpl.rosesale.MyOrderFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.roger.catloadinglibrary.CatLoadingView;
import com.wpl.rosesale.Adapter.MyOrder_ListViewAdapter;
import com.wpl.rosesale.MVP.Presenter.MyOrder_Presenter;
import com.wpl.rosesale.MVP.View.MyOrder_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.ACache;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.View.MyOrder_ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/21.
 */
public class AllFragment extends Fragment implements MyOrder_View {
    private View view;
    private String userPhone;
    private MyOrder_ListView listView;
    private MyOrder_ListViewAdapter adapter;
    private MyOrder_Presenter presenter;
    private Handler handler;
    private ACache aCache;
    private LinearLayout myOrder_all_notAdd;
    private CatLoadingView catLoadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all, container, false);

        initUI();
        initDate();
        return view;
    }

    private void initDate() {
        if (IsNetWorkStatus.getActiveNetwork(getContext()) != null) {//有网络
            presenter = new MyOrder_Presenter(userPhone, "", this);
            presenter.loadOrder(getContext());
        } else {
            if (aCache.getAsObject("loadOrderAll") != null) {
                ArrayList arrayList = (ArrayList) aCache.getAsObject("loadOrderAll");
                Message message = new Message();
                message.obj = arrayList;
                handler.sendMessage(message);
            }
        }
    }

    private void initUI() {
        catLoadingView = new CatLoadingView();
        catLoadingView.show(getFragmentManager(), getTag());
        SharedPreferences spf = getActivity().getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        userPhone = (spf.getString("phoneNumber", "")).replaceAll(" ", "");
        aCache = ACache.get(getActivity());
        myOrder_all_notAdd = (LinearLayout) view.findViewById(R.id.myOrder_all_notAdd);
        listView = (MyOrder_ListView) view.findViewById(R.id.myOrder_all_listView);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
                if (list.size() == 0) {
                    myOrder_all_notAdd.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    catLoadingView.dismiss();
                } else {
                    myOrder_all_notAdd.setVisibility(View.GONE);
                    adapter = new MyOrder_ListViewAdapter(getContext(), list, R.layout.listview_item_order_all);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    catLoadingView.dismiss();
                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 获取到的远程数据
     *
     * @param list
     */
    @Override
    public void loadOrder(List<Map<String, String>> list) {
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
            aCache.put("loadOrderAll", arrayList);
        }
        Message msg = new Message();
        msg.obj = list;
        handler.sendMessage(msg);
    }
}
