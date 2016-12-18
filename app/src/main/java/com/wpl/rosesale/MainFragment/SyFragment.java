package com.wpl.rosesale.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.wpl.rosesale.Activity.BuyOrderActivity;
import com.wpl.rosesale.Activity.ClassificationActivity;
import com.wpl.rosesale.Activity.ProductDetailsActicity;
import com.wpl.rosesale.Adapter.SY_ListViewAdapter;
import com.wpl.rosesale.Adapter.SY_RollPagerViewAdapter;
import com.wpl.rosesale.Adapter.ViewPagerAdapter;
import com.wpl.rosesale.Dialog.LoginDialog;
import com.wpl.rosesale.MVP.Presenter.SyJrtj_Presenter;
import com.wpl.rosesale.MVP.Presenter.SyListView_Presenter;
import com.wpl.rosesale.MVP.Presenter.SyVP_Presenter;
import com.wpl.rosesale.MVP.View.SyListView_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.ACache;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.Utils.ListUtils;
import com.wpl.rosesale.Utils.VolleyImageLoader;
import com.wpl.rosesale.View.SY_ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/7.
 */
public class SyFragment extends android.support.v4.app.Fragment implements View.OnClickListener, SyListView_View {
    private View view;
    private LinearLayout sy_searchBtn; //搜索
    private RollPagerView sy_rollViewPager; //图片轮播

    private SwipeRefreshLayout sy_refresh;
    private LinearLayout xh, ysh, xzh, tslp, gd; //导航栏
    private ViewPager sy_viewPager1;
    private List<View> viewList;
    private ViewPagerAdapter sy_viewPager_adapter;
    private ImageView sy_viewPager1_dot1, sy_viewPager1_dot2;//viewpager1下面的小圆点
    private TextView syViewPager1_1_title, syViewPager1_1_EnglishTitle, syViewPager1_1_price;
    private TextView syViewPager1_2_title, syViewPager1_2_EnglishTitle, syViewPager1_2_price;
    private ImageView syViewPager1_1_img, syViewPager1_2_img;
    private Button syViewPager1_1_buy, syViewPager1_2_buy;
    private LinearLayout syViewPager1_1, syViewPager1_2, jrtj1, jrtj2, jrtj3, jrtj4;
    private ACache cachePLI, cacheVP, cacheJRTJ;//本地缓存的组件

    private ImageView sy_jrtj_1_img, sy_jrtj_2_img, sy_jrtj_3_img, sy_jrtj_4_img;
    private TextView sy_jrtj_1_title, sy_jrtj_2_title, sy_jrtj_3_title, sy_jrtj_4_title;
    private TextView sy_jrtj_1_price, sy_jrtj_2_price, sy_jrtj_3_price, sy_jrtj_4_price;

    private SY_ListView sy_listView;  //listView

    private SyListView_Presenter syListView_presenter;
    private SyJrtj_Presenter syJrtj_presenter;
    private SyVP_Presenter syVP_presenter;
    private Handler handlerJrtj, handlerVP, handlerPLI;

    private List<Map<String, String>> list_loadSyProductListItems = new ArrayList<>();
    private List<Map<String, String>> list_loadJrtj = new ArrayList<>();
    private List<Map<String, String>> list_loadVP = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sy, container, false);

        initData();
        initUI();
        initView();
        return view;
    }

    /**
     * 获取远程数据
     */
    private void initData() {
        if (IsNetWorkStatus.getActiveNetwork(getContext()) != null) {//有网络

            syListView_presenter = new SyListView_Presenter(SyFragment.this);
            syListView_presenter.loadSyProductListItems(getContext(), "近期热卖");

            syVP_presenter = new SyVP_Presenter(this);
            syVP_presenter.loadSyVP(getContext(), "首页ViewPager");

            syJrtj_presenter = new SyJrtj_Presenter(this);
            syJrtj_presenter.loadJrtj(getContext(), "今日推荐");

        } else {//网络不可用，则请求本地数据缓存
            if (cachePLI.getAsObject("loadSyProductListItems") != null) {
                ArrayList arrayList = (ArrayList) cachePLI.getAsObject("loadSyProductListItems");
                Message message = new Message();
                message.obj = arrayList;
                handlerPLI.sendMessage(message);

            }

            if (cacheVP.getAsObject("loadVP") != null) {
                ArrayList arrayList = (ArrayList) cacheVP.getAsObject("loadVP");
                Message message = new Message();
                message.obj = arrayList;
                handlerVP.sendMessage(message);
            }

            if (cacheJRTJ.getAsObject("loadJrtj") != null) {
                ArrayList arrayList = (ArrayList) cacheJRTJ.getAsObject("loadJrtj");
                Message message = new Message();
                message.obj = arrayList;
                handlerJrtj.sendMessage(message);
            }
        }

    }

    /**
     * 初始化UI组件
     */
    private void initUI() {
        //初始化本地缓存组件的对象
        cachePLI = ACache.get(getActivity());
        cacheVP = ACache.get(getActivity());
        cacheJRTJ = ACache.get(getActivity());
        sy_jrtj_1_img = (ImageView) view.findViewById(R.id.sy_jrtj_1_img);
        sy_jrtj_2_img = (ImageView) view.findViewById(R.id.sy_jrtj_2_img);
        sy_jrtj_3_img = (ImageView) view.findViewById(R.id.sy_jrtj_3_img);
        sy_jrtj_4_img = (ImageView) view.findViewById(R.id.sy_jrtj_4_img);
        sy_jrtj_1_title = (TextView) view.findViewById(R.id.sy_jrtj_1_title);
        sy_jrtj_2_title = (TextView) view.findViewById(R.id.sy_jrtj_2_title);
        sy_jrtj_3_title = (TextView) view.findViewById(R.id.sy_jrtj_3_title);
        sy_jrtj_4_title = (TextView) view.findViewById(R.id.sy_jrtj_4_title);
        sy_jrtj_1_price = (TextView) view.findViewById(R.id.sy_jrtj_1_price);
        sy_jrtj_2_price = (TextView) view.findViewById(R.id.sy_jrtj_2_price);
        sy_jrtj_3_price = (TextView) view.findViewById(R.id.sy_jrtj_3_price);
        sy_jrtj_4_price = (TextView) view.findViewById(R.id.sy_jrtj_4_price);
        sy_rollViewPager = (RollPagerView) view.findViewById(R.id.sy_rollViewPager);
        sy_searchBtn = (LinearLayout) view.findViewById(R.id.sy_searchBtn);
        xh = (LinearLayout) view.findViewById(R.id.sy_xh);
        ysh = (LinearLayout) view.findViewById(R.id.sy_ysh);
        xzh = (LinearLayout) view.findViewById(R.id.sy_xzh);
        tslp = (LinearLayout) view.findViewById(R.id.sy_tslp);
        gd = (LinearLayout) view.findViewById(R.id.sy_gd);
        jrtj1 = (LinearLayout) view.findViewById(R.id.jrtj1);
        jrtj2 = (LinearLayout) view.findViewById(R.id.jrtj2);
        jrtj3 = (LinearLayout) view.findViewById(R.id.jrtj3);
        jrtj4 = (LinearLayout) view.findViewById(R.id.jrtj4);
        sy_viewPager1 = (ViewPager) view.findViewById(R.id.sy_viewPager1);
        sy_viewPager1_dot1 = (ImageView) view.findViewById(R.id.sy_viewPager1_dot1);
        sy_viewPager1_dot2 = (ImageView) view.findViewById(R.id.sy_viewPager1_dot2);
        sy_listView = (SY_ListView) view.findViewById(R.id.sy_listView);
        sy_refresh = (SwipeRefreshLayout) view.findViewById(R.id.sy_refresh);
        xh.setOnClickListener(this);
        ysh.setOnClickListener(this);
        xzh.setOnClickListener(this);
        tslp.setOnClickListener(this);
        gd.setOnClickListener(this);
        jrtj1.setOnClickListener(this);
        jrtj2.setOnClickListener(this);
        jrtj3.setOnClickListener(this);
        jrtj4.setOnClickListener(this);
        sy_searchBtn.setOnClickListener(this);
        initRollViewPager();
    }

    /**
     * 初始化View
     */
    private void initView() {
        viewList = new ArrayList<View>();
        viewList.add(LayoutInflater.from(getActivity()).inflate(R.layout.view_sy_viewpager1_1, null));
        viewList.add(LayoutInflater.from(getActivity()).inflate(R.layout.view_sy_viewpager1_2, null));
        sy_viewPager_adapter = new ViewPagerAdapter(viewList);
        sy_viewPager1.setAdapter(sy_viewPager_adapter);
        sy_viewPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        sy_viewPager1_dot1.setImageResource(R.mipmap.rm);
                        sy_viewPager1_dot2.setImageResource(R.mipmap.a0h);
                        break;
                    case 1:
                        sy_viewPager1_dot1.setImageResource(R.mipmap.a0h);
                        sy_viewPager1_dot2.setImageResource(R.mipmap.rm);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        syViewPager1_1_title = (TextView) viewList.get(0).findViewById(R.id.syViewPager1_1_title);
        syViewPager1_1_EnglishTitle = (TextView) viewList.get(0).findViewById(R.id.syViewPager1_1_EnglishTitle);
        syViewPager1_1_price = (TextView) viewList.get(0).findViewById(R.id.syViewPager1_1_price);
        syViewPager1_1_img = (ImageView) viewList.get(0).findViewById(R.id.syViewPager1_1_img);
        syViewPager1_1_buy = (Button) viewList.get(0).findViewById(R.id.syViewPager1_1_buy);
        syViewPager1_1 = (LinearLayout) viewList.get(0).findViewById(R.id.syViewPager1_1);

        syViewPager1_2_title = (TextView) viewList.get(1).findViewById(R.id.syViewPager1_2_title);
        syViewPager1_2_EnglishTitle = (TextView) viewList.get(1).findViewById(R.id.syViewPager1_2_EnglishTitle);
        syViewPager1_2_price = (TextView) viewList.get(1).findViewById(R.id.syViewPager1_2_price);
        syViewPager1_2_img = (ImageView) viewList.get(1).findViewById(R.id.syViewPager1_2_img);
        syViewPager1_2_buy = (Button) viewList.get(1).findViewById(R.id.syViewPager1_2_buy);
        syViewPager1_2 = (LinearLayout) viewList.get(1).findViewById(R.id.syViewPager1_2);

        syViewPager1_1_buy.setOnClickListener(this);
        syViewPager1_2_buy.setOnClickListener(this);
        syViewPager1_1.setOnClickListener(this);
        syViewPager1_2.setOnClickListener(this);

        handlerVP = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                list_loadVP = (List<Map<String, String>>) msg.obj;
                if (list_loadVP.size() != 0) {
                    if (list_loadVP.size() >= 2) {
                        Map<String, String> map1 = list_loadVP.get(0);
                        Map<String, String> map2 = list_loadVP.get(1);
                        VolleyImageLoader.setBitmap(getContext(), map1.get("pSquare_Image"), syViewPager1_1_img);
                        VolleyImageLoader.setBitmap(getContext(), map2.get("pSquare_Image"), syViewPager1_2_img);
                        syViewPager1_1_title.setText(map1.get("pTitle"));
                        syViewPager1_2_title.setText(map2.get("pTitle"));
                        syViewPager1_1_EnglishTitle.setText(map1.get("pEnglishTitle"));
                        syViewPager1_2_EnglishTitle.setText(map2.get("pEnglishTitle"));
                        syViewPager1_1_price.setText(map1.get("pPrice"));
                        syViewPager1_2_price.setText(map2.get("pPrice"));
                    }
                    if (list_loadVP.size() == 1) {
                        Map<String, String> map1 = list_loadVP.get(0);
                        VolleyImageLoader.setImageView(getContext(), map1.get("pSquare_Image"), syViewPager1_1_img);
                        syViewPager1_1_title.setText(map1.get("pTitle"));
                        syViewPager1_1_EnglishTitle.setText(map1.get("pEnglishTitle"));
                        syViewPager1_1_price.setText(map1.get("pPrice"));
                    }
                }
                super.handleMessage(msg);
            }
        };

        handlerJrtj = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                list_loadJrtj = (List<Map<String, String>>) msg.obj;
                if (list_loadJrtj.size() != 0) {
                    if (list_loadJrtj.size() >= 4) {
                        Map<String, String> map1 = list_loadJrtj.get(0);
                        Map<String, String> map2 = list_loadJrtj.get(1);
                        Map<String, String> map3 = list_loadJrtj.get(2);
                        Map<String, String> map4 = list_loadJrtj.get(3);
                        VolleyImageLoader.setBitmap(getContext(), map1.get("pSquare_Image"), sy_jrtj_1_img);
                        VolleyImageLoader.setBitmap(getContext(), map2.get("pSquare_Image"), sy_jrtj_2_img);
                        VolleyImageLoader.setBitmap(getContext(), map3.get("pSquare_Image"), sy_jrtj_3_img);
                        VolleyImageLoader.setBitmap(getContext(), map4.get("pSquare_Image"), sy_jrtj_4_img);
                        sy_jrtj_1_title.setText(map1.get("pTitle"));
                        sy_jrtj_2_title.setText(map2.get("pTitle"));
                        sy_jrtj_3_title.setText(map3.get("pTitle"));
                        sy_jrtj_4_title.setText(map4.get("pTitle"));
                        sy_jrtj_1_price.setText(map1.get("pPrice"));
                        sy_jrtj_2_price.setText(map2.get("pPrice"));
                        sy_jrtj_3_price.setText(map3.get("pPrice"));
                        sy_jrtj_4_price.setText(map4.get("pPrice"));
                    }
                    if (list_loadJrtj.size() == 3) {
                        Map<String, String> map1 = list_loadJrtj.get(0);
                        Map<String, String> map2 = list_loadJrtj.get(1);
                        Map<String, String> map3 = list_loadJrtj.get(2);
                        VolleyImageLoader.setBitmap(getContext(), map1.get("pSquare_Image"), sy_jrtj_1_img);
                        VolleyImageLoader.setBitmap(getContext(), map2.get("pSquare_Image"), sy_jrtj_2_img);
                        VolleyImageLoader.setBitmap(getContext(), map3.get("pSquare_Image"), sy_jrtj_3_img);
                        sy_jrtj_1_title.setText(map1.get("pTitle"));
                        sy_jrtj_2_title.setText(map2.get("pTitle"));
                        sy_jrtj_3_title.setText(map3.get("pTitle"));
                        sy_jrtj_1_price.setText(map1.get("pPrice"));
                        sy_jrtj_2_price.setText(map2.get("pPrice"));
                        sy_jrtj_3_price.setText(map3.get("pPrice"));
                    }
                    if (list_loadJrtj.size() == 2) {
                        Map<String, String> map1 = list_loadJrtj.get(0);
                        Map<String, String> map2 = list_loadJrtj.get(1);
                        VolleyImageLoader.setBitmap(getContext(), map1.get("pSquare_Image"), sy_jrtj_1_img);
                        VolleyImageLoader.setBitmap(getContext(), map2.get("pSquare_Image"), sy_jrtj_2_img);
                        sy_jrtj_1_title.setText(map1.get("pTitle"));
                        sy_jrtj_2_title.setText(map2.get("pTitle"));
                        sy_jrtj_1_price.setText(map1.get("pPrice"));
                        sy_jrtj_2_price.setText(map2.get("pPrice"));
                    }
                    if (list_loadJrtj.size() == 1) {
                        Map<String, String> map1 = list_loadJrtj.get(0);
                        VolleyImageLoader.setBitmap(getContext(), map1.get("pSquare_Image"), sy_jrtj_1_img);
                        sy_jrtj_1_title.setText(map1.get("pTitle"));
                        sy_jrtj_1_price.setText(map1.get("pPrice"));
                    }
                }
                super.handleMessage(msg);
            }
        };

        handlerPLI = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
                SY_ListViewAdapter adapter = new SY_ListViewAdapter(getContext(), list, R.layout.listview_item_sy);
                sy_listView.setAdapter(adapter);
                ListUtils.setListViewHeightBasedOnChildren(sy_listView);

                sy_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (list.size() != 0) {
                            Map<String, String> map = list.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("objId", map.get("objId"));
                            bundle.putString("pTitle", map.get("pTitle"));
                            Intent intent = new Intent(getActivity(), ProductDetailsActicity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    }
                });
                super.handleMessage(msg);
            }
        };

        //下拉刷新组件
        sy_refresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light);
        sy_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sy_refresh.setRefreshing(false);
                        initData();
                    }
                }, 1500);
            }
        });


    }

    /**
     * 初始化图片轮播
     */
    private void initRollViewPager() {
        sy_rollViewPager.setPlayDelay(3000);
        sy_rollViewPager.setAnimationDurtion(500);
        sy_rollViewPager.setAdapter(new SY_RollPagerViewAdapter());
        sy_rollViewPager.setHintView(new ColorPointHintView(getContext(), Color.YELLOW, Color.WHITE));
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sy_searchBtn: // 搜索
                break;
            case R.id.sy_xh: //鲜花
                break;
            case R.id.sy_ysh: //永生花
                break;
            case R.id.sy_xzh: //香皂花
                break;
            case R.id.sy_tslp: //特色礼品
                break;
            case R.id.sy_gd: //更多
                startActivity(new Intent().setClass(getContext(), ClassificationActivity.class));
                break;
            case R.id.jrtj1: //今日推荐1
                if (list_loadJrtj.size() >= 1) {
                    Map<String, String> map_loadJrtj1 = list_loadJrtj.get(0);
                    Bundle bundle_jrtj1 = new Bundle();
                    bundle_jrtj1.putString("objId", map_loadJrtj1.get("objId"));
                    bundle_jrtj1.putString("pTitle", map_loadJrtj1.get("pTitle"));
                    Intent intent_jrtj1 = new Intent(getActivity(), ProductDetailsActicity.class);
                    intent_jrtj1.putExtras(bundle_jrtj1);
                    getActivity().startActivity(intent_jrtj1);
                }
                break;
            case R.id.jrtj2: //今日推荐2
                if (list_loadJrtj.size() >= 2) {
                    Map<String, String> map_loadJrtj2 = list_loadJrtj.get(1);
                    Bundle bundle_jrtj2 = new Bundle();
                    bundle_jrtj2.putString("objId", map_loadJrtj2.get("objId"));
                    bundle_jrtj2.putString("pTitle", map_loadJrtj2.get("pTitle"));
                    Intent intent_jrtj2 = new Intent(getActivity(), ProductDetailsActicity.class);
                    intent_jrtj2.putExtras(bundle_jrtj2);
                    getActivity().startActivity(intent_jrtj2);
                }
                break;
            case R.id.jrtj3: //今日推荐3
                if (list_loadJrtj.size() >= 3) {
                    Map<String, String> map_loadJrtj3 = list_loadJrtj.get(2);
                    Bundle bundle_jrtj3 = new Bundle();
                    bundle_jrtj3.putString("objId", map_loadJrtj3.get("objId"));
                    bundle_jrtj3.putString("pTitle", map_loadJrtj3.get("pTitle"));
                    Intent intent_jrtj3 = new Intent(getActivity(), ProductDetailsActicity.class);
                    intent_jrtj3.putExtras(bundle_jrtj3);
                    getActivity().startActivity(intent_jrtj3);
                }
                break;
            case R.id.jrtj4: //今日推荐4
                if (list_loadJrtj.size() >= 4) {
                    Map<String, String> map_loadJrtj4 = list_loadJrtj.get(3);
                    Bundle bundle_jrtj4 = new Bundle();
                    bundle_jrtj4.putString("objId", map_loadJrtj4.get("objId"));
                    bundle_jrtj4.putString("pTitle", map_loadJrtj4.get("pTitle"));
                    Intent intent_jrtj4 = new Intent(getActivity(), ProductDetailsActicity.class);
                    intent_jrtj4.putExtras(bundle_jrtj4);
                    getActivity().startActivity(intent_jrtj4);
                }
                break;
            case R.id.syViewPager1_1_buy: //viewPager1立即订购
                SharedPreferences spf = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                if (spf.getBoolean("login_status", false) == false) {//未登录
                    Toast.makeText(getContext(), "请先登录账号", Toast.LENGTH_SHORT).show();
                    (new LoginDialog(getContext(), R.style.loading_dialog)).show();
                } else {//已登录状态
                    if (list_loadVP.size() >= 1) {
                        Map<String, String> map = list_loadVP.get(0);
                        Bundle bundle = new Bundle();
                        bundle.putString("objId", map.get("objId"));
                        bundle.putString("pTitle", map.get("pTitle"));
                        Intent intent = new Intent(getActivity(), BuyOrderActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.syViewPager1_2_buy: //viewPager2立即订购
                SharedPreferences spf1 = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                if (spf1.getBoolean("login_status", false) == false) {//未登录
                    Toast.makeText(getContext(), "请先登录账号", Toast.LENGTH_SHORT).show();
                    (new LoginDialog(getContext(), R.style.loading_dialog)).show();
                } else {//已登录状态
                    if (list_loadVP.size() >= 2) {
                        Map<String, String> map = list_loadVP.get(1);
                        Bundle bundle = new Bundle();
                        bundle.putString("objId", map.get("objId"));
                        bundle.putString("pTitle", map.get("pTitle"));
                        Intent intent = new Intent(getActivity(), BuyOrderActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.syViewPager1_1: //viewpager1
                if (list_loadVP.size() >= 1) {
                    Map<String, String> map_loadVP_1 = list_loadVP.get(0);
                    Bundle bundle_VP1 = new Bundle();
                    bundle_VP1.putString("objId", map_loadVP_1.get("objId"));
                    bundle_VP1.putString("pTitle", map_loadVP_1.get("pTitle"));
                    Intent intent_VP1 = new Intent(getActivity(), ProductDetailsActicity.class);
                    intent_VP1.putExtras(bundle_VP1);
                    getActivity().startActivity(intent_VP1);
                }
                break;
            case R.id.syViewPager1_2: //viewpager2
                if (list_loadVP.size() >= 2) {
                    Map<String, String> map_loadVP_2 = list_loadVP.get(1);
                    Bundle bundle_VP2 = new Bundle();
                    bundle_VP2.putString("objId", map_loadVP_2.get("objId"));
                    bundle_VP2.putString("pTitle", map_loadVP_2.get("pTitle"));
                    Intent intent_VP2 = new Intent(getActivity(), ProductDetailsActicity.class);
                    intent_VP2.putExtras(bundle_VP2);
                    getActivity().startActivity(intent_VP2);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void loadSyProductListItems(List<Map<String, String>> list) {
        list_loadSyProductListItems = list;
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
            cachePLI.put("loadSyProductListItems", arrayList);//目的将一个Java对象存入到文件中
        }

        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message message = new Message();
            message.obj = list;
            handlerPLI.sendMessage(message);
        }

    }

    @Override
    public void loadJrtj(List<Map<String, String>> list) {
        list_loadJrtj = list;
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
            cacheJRTJ.put("loadJrtj", arrayList);
        }

        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message message = new Message();
            message.obj = list;
            handlerJrtj.sendMessage(message);
        }
    }

    @Override
    public void loadVP(List<Map<String, String>> list) {
        list_loadVP = list;
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
            cacheVP.put("loadVP", arrayList);
        }

        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message message = new Message();
            message.obj = list;
            handlerVP.sendMessage(message);
        }
    }

}
