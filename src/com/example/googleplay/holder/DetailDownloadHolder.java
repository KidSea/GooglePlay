package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.MyListView;

import android.view.View;

public class DetailDownloadHolder extends BaseHolder<AppInfo> {

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(UIUtils.getContext(), R.layout.layout_detail_download, null);
		
		
		return view;
	}



	@Override
	public void refreshView(AppInfo data) {
		// TODO Auto-generated method stub
		
	}

}
