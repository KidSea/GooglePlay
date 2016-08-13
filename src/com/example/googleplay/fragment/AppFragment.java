package com.example.googleplay.fragment;

import java.util.ArrayList;

import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.holder.AppHolder;
import com.example.googleplay.holder.BaseHolder;
import com.example.googleplay.http.protocl.AppProtocl;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.MyListView;
import com.example.googleplay.view.LoadingPage.ResultState;

import android.view.View;

/**
 * APP fragment
 * 
 * @author yuxuehai
 * 
 */
public class AppFragment extends BaseFragment {
	private ArrayList<AppInfo> data;

	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new AppAdapter(data));
		return view;
	}

	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		AppProtocl protocl = new AppProtocl();
		data = protocl.getData(0);
		return check(data);
	}

	class AppAdapter extends MyBaseAdapter<AppInfo> {

		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			// TODO Auto-generated method stub
			return new AppHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {
			// TODO Auto-generated method stub
			AppProtocl protocl = new AppProtocl();
			ArrayList<AppInfo> moredata = protocl.getData(getListSize());
			return moredata;
		}

	}
}
