package com.example.googleplay.fragment;

import com.example.googleplay.view.LoadingPage.ResultState;
import com.example.utils.UIUtils;

import android.view.View;
import android.widget.TextView;

/**
 * 主页fragment
 * 
 * @author yuxuehai
 * 
 */
public class HomeFragment extends BaseFragment {

	// 加载成功就会回掉这个方法，加载布局，这个方法运行在住线程
	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		TextView textView = new TextView(UIUtils.getContext());

		textView.setText(getClass().getSimpleName());
		return textView;
	}

	// 运行在子线程，可以直接访问网络
	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		return ResultState.STATE_SUCCESS;
	}

}
