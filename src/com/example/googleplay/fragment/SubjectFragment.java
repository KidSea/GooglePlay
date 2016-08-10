package com.example.googleplay.fragment;

import java.util.ArrayList;

import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.domain.SubjectInfo;
import com.example.googleplay.holder.BaseHolder;
import com.example.googleplay.holder.SubjectHolder;
import com.example.googleplay.http.protocl.SubjectProtocle;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.LoadingPage.ResultState;
import com.example.googleplay.view.MyListView;

import android.view.View;

/**
 * 专题 fragment
 * 
 * @author yuxuehai
 * 
 */
public class SubjectFragment extends BaseFragment {
	private ArrayList<SubjectInfo> data;

	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		MyListView listView = new MyListView(UIUtils.getContext());
		listView.setAdapter(new SubjectAdapter(data));
		return listView;
	}

	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		SubjectProtocle protocle = new SubjectProtocle();
		data = protocle.getData(0);

		return check(data);
	}

	class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder() {
			// TODO Auto-generated method stub
			return new SubjectHolder();
		}

		@Override
		public ArrayList<SubjectInfo> onLoadMore() {
			// TODO Auto-generated method stub
			SubjectProtocle protocle = new SubjectProtocle();
			ArrayList<SubjectInfo> moreData = protocle.getData(getListSize());
			return moreData;
		}

	}
}
