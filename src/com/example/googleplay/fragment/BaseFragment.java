package com.example.googleplay.fragment;

import com.example.googleplay.view.LoadingPage;
import com.example.googleplay.view.LoadingPage.ResultState;
import com.example.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 基类
 * 
 * @author yuxuehai
 * 
 */
public abstract class BaseFragment extends Fragment {

	private LoadingPage mLoadingPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// TextView textView = new TextView(UIUtils.getContext());
		//
		// textView.setText(getClass().getSimpleName());

		mLoadingPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View onCreateSuccessView() {
				// TODO Auto-generated method stub
				// 注意此处一定要调用basefragment的oncreatesuccessview();
				return BaseFragment.this.onCreateSuccessView();
			}

			@Override
			public ResultState onLoad() {
				// TODO Auto-generated method stub
				return BaseFragment.this.onLoad();
			}

		};

		return mLoadingPage;
	}

	// 加载成功后显示的布局，必须由调用者来实现
	public abstract View onCreateSuccessView();

	// 加载网络数据,返回值为反问成功的值
	public abstract ResultState onLoad();

	// 开始加载数据
	public void loadData() {
		if (mLoadingPage != null) {
			mLoadingPage.loadData();
		}
	}
}
