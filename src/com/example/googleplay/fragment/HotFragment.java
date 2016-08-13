package com.example.googleplay.fragment;

import java.util.ArrayList;
import java.util.Random;

import com.example.googleplay.http.protocl.HotProtocol;
import com.example.googleplay.utils.DrawableUtils;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.FlowLayout;
import com.example.googleplay.view.LoadingPage.ResultState;
import com.example.googleplay.view.MyFlowLayout;



import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * APP fragment
 * 
 * @author yuxuehai
 * 
 */
public class HotFragment extends BaseFragment {
	private ArrayList<String> data;

	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		// 为了使布局可以上下滑动,需要用ScrollView包装起来
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
		int padding = UIUtils.dip2px(10);
		// 设置ScrollView边距
		scrollView.setPadding(padding, padding, padding, padding);
//		// 初始化自定义控件
		MyFlowLayout flow = new MyFlowLayout(UIUtils.getContext());
		
		// 根据接口返回的数据个数,动态添加TextView
		for (final String str : data) {
			TextView view = new TextView(UIUtils.getContext());
			view.setText(str);
			view.setTextColor(Color.WHITE);
			view.setGravity(Gravity.CENTER);
			view.setPadding(padding, padding, padding, padding);
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

			// 设置随机文字颜色
			Random random = new Random();
			int r = 30 + random.nextInt(210);
			int g = 30 + random.nextInt(210);
			int b = 30 + random.nextInt(210);

			int color = 0xffcecece;// 按下后偏白的背景色

			// 根据默认颜色和按下颜色, 生成圆角矩形的状态选择器
			Drawable selector = DrawableUtils.getStateListDrawable(
					Color.rgb(r, g, b), color, UIUtils.dip2px(6));

			// 给TextView设置背景
			view.setBackgroundDrawable(selector);

			// 必须设置点击事件, TextView按下后颜色才会变化
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), str,
							Toast.LENGTH_SHORT).show();
				}
			});

			// 给自定义控件添加view对象
			flow.addView(view);
		}
		
		scrollView.addView(flow);
		return scrollView;
	}

	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		HotProtocol protocol = new HotProtocol();
		data = protocol.getData(0);
		return check(data);
	}

}
