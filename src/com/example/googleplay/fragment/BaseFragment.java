package com.example.googleplay.fragment;

import java.util.ArrayList;

import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.LoadingPage;
import com.example.googleplay.view.LoadingPage.ResultState;

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
	//对网络数据的合法性校验
	public ResultState check(Object object) {
		if (object != null) {
			if (object instanceof ArrayList) {
				ArrayList list = (ArrayList) object;
				if (list.isEmpty()) {
					return ResultState.STATE_EMPTY;
				} else {
					return ResultState.STATE_SUCCESS;
				}
			}
		}

		return ResultState.STATE_ERROR;

	}
}
