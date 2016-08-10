package com.example.googleplay.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}


	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		this.setSelector(new ColorDrawable());// 设置选择器默认为透明
		this.setDivider(null);// 去掉分割线
		this.setCacheColorHint(Color.TRANSPARENT);// 有时候滑动listview背景会变成黑色
													// 此方法会将背景变成全透明
	}

}
