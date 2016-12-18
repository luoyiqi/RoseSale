package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
*	订单表
*/
public class orderTab extends BmobObject{

	private String oBelongTo;			//用户
	private String oIncome_name;		//收货人姓名
	private String oIncome_phone;		//收货人电话
	private String oIncome_post;		//收货人邮编
	private String oIncome_province;	//省
	private String oIncome_city;		//市
	private String oIncome_county;		//县/区
	private String oIncome_address;		//详细地址
	private String oGoods_objId;		//商品ID(关联productList表ObjectId)
	private String oGoods_price;		//商品价格
	private String oGoods_number;		//商品数量
	private String oTime_commit;		//下单时间
	private String order_status;		//订单状态（待付款-->待发货-->待收货-->待评价-->完成）
	private String oBuyInfo;			//备注信息


	public String getoBelongTo() {
		return oBelongTo;
	}

	public void setoBelongTo(String oBelongTo) {
		this.oBelongTo = oBelongTo;
	}

	public String getoBuyInfo() {
		return oBuyInfo;
	}

	public void setoBuyInfo(String oBuyInfo) {
		this.oBuyInfo = oBuyInfo;
	}

	public String getoGoods_number() {
		return oGoods_number;
	}

	public void setoGoods_number(String oGoods_number) {
		this.oGoods_number = oGoods_number;
	}

	public String getoGoods_objId() {
		return oGoods_objId;
	}

	public void setoGoods_objId(String oGoods_objId) {
		this.oGoods_objId = oGoods_objId;
	}

	public String getoGoods_price() {
		return oGoods_price;
	}

	public void setoGoods_price(String oGoods_price) {
		this.oGoods_price = oGoods_price;
	}

	public String getoIncome_address() {
		return oIncome_address;
	}

	public void setoIncome_address(String oIncome_address) {
		this.oIncome_address = oIncome_address;
	}

	public String getoIncome_city() {
		return oIncome_city;
	}

	public void setoIncome_city(String oIncome_city) {
		this.oIncome_city = oIncome_city;
	}

	public String getoIncome_county() {
		return oIncome_county;
	}

	public void setoIncome_county(String oIncome_county) {
		this.oIncome_county = oIncome_county;
	}

	public String getoIncome_name() {
		return oIncome_name;
	}

	public void setoIncome_name(String oIncome_name) {
		this.oIncome_name = oIncome_name;
	}

	public String getoIncome_phone() {
		return oIncome_phone;
	}

	public void setoIncome_phone(String oIncome_phone) {
		this.oIncome_phone = oIncome_phone;
	}

	public String getoIncome_post() {
		return oIncome_post;
	}

	public void setoIncome_post(String oIncome_post) {
		this.oIncome_post = oIncome_post;
	}

	public String getoIncome_province() {
		return oIncome_province;
	}

	public void setoIncome_province(String oIncome_province) {
		this.oIncome_province = oIncome_province;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getoTime_commit() {
		return oTime_commit;
	}

	public void setoTime_commit(String oTime_commit) {
		this.oTime_commit = oTime_commit;
	}
}