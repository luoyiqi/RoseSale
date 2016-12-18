package com.wpl.rosesale.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.Activity.MeAddressManagerActivity;
import com.wpl.rosesale.Activity.MeInfoActivity;
import com.wpl.rosesale.Activity.MyCollectActivity;
import com.wpl.rosesale.Activity.MyOrderActivity;
import com.wpl.rosesale.MVP.Presenter.GetMeData_Presenter;
import com.wpl.rosesale.MVP.View.GetMeData_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsHaveFile;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.Utils.RoundImageView;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/7.
 */
public class WdFragment extends android.support.v4.app.Fragment implements View.OnClickListener, GetMeData_View {
    private View view;

    private RelativeLayout me_title, me_oderAll;
    private LinearLayout me_oderDFK, me_oderDFH, me_oderDSH, me_oderDPJ;
    private RelativeLayout me_pj, me_sc, me_dz;
    private RelativeLayout me_fx, me_sz;
    private RelativeLayout me_xy, me_lx;
    private String phoneNumber;
    private TextView me_nickName;
    private RoundImageView me_image;
    private GetMeData_Presenter getMeData_presenter;
    private Handler setViewHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wd, container, false);
        initUI();
        return view;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        me_title = (RelativeLayout) view.findViewById(R.id.me_title);
        me_oderAll = (RelativeLayout) view.findViewById(R.id.me_oderAll);
        me_oderDFK = (LinearLayout) view.findViewById(R.id.me_oderDFK);
        me_oderDFH = (LinearLayout) view.findViewById(R.id.me_oderDFH);
        me_oderDSH = (LinearLayout) view.findViewById(R.id.me_oderDSH);
        me_oderDPJ = (LinearLayout) view.findViewById(R.id.me_oderDPJ);

        me_pj = (RelativeLayout) view.findViewById(R.id.me_pj);
        me_sc = (RelativeLayout) view.findViewById(R.id.me_sc);
        me_dz = (RelativeLayout) view.findViewById(R.id.me_dz);
        me_fx = (RelativeLayout) view.findViewById(R.id.me_fx);
        me_sz = (RelativeLayout) view.findViewById(R.id.me_sz);
        me_xy = (RelativeLayout) view.findViewById(R.id.me_xy);
        me_lx = (RelativeLayout) view.findViewById(R.id.me_lx);

        me_nickName = (TextView) view.findViewById(R.id.me_nickName);
        me_image = (RoundImageView) view.findViewById(R.id.me_image);

        SharedPreferences sf = getContext().getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        phoneNumber = (sf.getString("phoneNumber", "")).replaceAll(" ", "");

        me_title.setOnClickListener(this);
        me_oderAll.setOnClickListener(this);
        me_oderDFK.setOnClickListener(this);
        me_oderDFH.setOnClickListener(this);
        me_oderDSH.setOnClickListener(this);
        me_oderDPJ.setOnClickListener(this);

        me_pj.setOnClickListener(this);
        me_sc.setOnClickListener(this);
        me_dz.setOnClickListener(this);
        me_fx.setOnClickListener(this);
        me_sz.setOnClickListener(this);
        me_xy.setOnClickListener(this);
        me_lx.setOnClickListener(this);

        initView();
    }

    private void initView() {
        setViewHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> resultMap = (Map<String, String>) msg.obj;
                //头像的设置
                if (IsNetWorkStatus.getActiveNetwork(getContext()) != null) {
                    //有网络
                    VolleyImageLoader.setImageView(getContext(), resultMap.get("image"), me_image);
                } else {
                    //无网络
                    if (me_image.getDrawable().getCurrent().getConstantState()
                            == getResources().getDrawable(R.mipmap.ic_rose_icon_no).getConstantState()) {
                        Toast.makeText(getContext(), "请检查你的手机网络", Toast.LENGTH_SHORT).show();
                        if (IsHaveFile.fileIsExists(resultMap.get("imagePath")) == true) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 10;
                            Bitmap bm = BitmapFactory.decodeFile(resultMap.get("imagePath"), options);
                            me_image.setImageBitmap(bm);
                        } else {
                            //本地文件不存在
                        }
                    } else {

                    }
                }

                //设置昵称
                if (resultMap.get("nickName").equals("")) {
                    me_nickName.setText("Rose_");
                } else {
                    me_nickName.setText(resultMap.get("nickName"));
                }

                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_title://个人资料
                startActivity(new Intent().setClass(getContext(), MeInfoActivity.class));
                break;
            case R.id.me_oderAll://全部订单
                Bundle bundle_0 = new Bundle();
                bundle_0.putInt("index", 0);
                Intent intent_0 = new Intent().setClass(getContext(), MyOrderActivity.class);
                intent_0.putExtras(bundle_0);
                startActivity(intent_0);
                break;
            case R.id.me_oderDFK://待付款
                Bundle bundle_1 = new Bundle();
                bundle_1.putInt("index", 1);
                Intent intent_1 = new Intent().setClass(getContext(), MyOrderActivity.class);
                intent_1.putExtras(bundle_1);
                startActivity(intent_1);
                break;
            case R.id.me_oderDFH://待发货
                Bundle bundle_2 = new Bundle();
                bundle_2.putInt("index", 2);
                Intent intent_2 = new Intent().setClass(getContext(), MyOrderActivity.class);
                intent_2.putExtras(bundle_2);
                startActivity(intent_2);
                break;
            case R.id.me_oderDSH://待收货
                Bundle bundle_3 = new Bundle();
                bundle_3.putInt("index", 3);
                Intent intent_3 = new Intent().setClass(getContext(), MyOrderActivity.class);
                intent_3.putExtras(bundle_3);
                startActivity(intent_3);
                break;
            case R.id.me_oderDPJ://待评价
                Bundle bundle_4 = new Bundle();
                bundle_4.putInt("index", 4);
                Intent intent_4 = new Intent().setClass(getContext(), MyOrderActivity.class);
                intent_4.putExtras(bundle_4);
                startActivity(intent_4);
                break;
            case R.id.me_pj://我的评价
                break;
            case R.id.me_sc://我的收藏
                startActivity(new Intent().setClass(getActivity(), MyCollectActivity.class));
                break;
            case R.id.me_dz://我的地址
                startActivity(new Intent().setClass(getActivity(), MeAddressManagerActivity.class));
                break;
            case R.id.me_fx://分享有礼
                break;
            case R.id.me_sz://设置
                break;
            case R.id.me_xy://用户协议
                break;
            case R.id.me_lx://联系我们
                break;
            default:
                break;
        }
    }


    @Override
    public void getMeData(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message msg = new Message();
            msg.obj = map;
            setViewHandler.sendMessage(msg);
        }
    }

    @Override
    public void onResume() {
        getMeData_presenter = new GetMeData_Presenter(phoneNumber, WdFragment.this);
        getMeData_presenter.getMeData(WdFragment.this.getContext());
        super.onResume();
    }
}
