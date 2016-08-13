package com.example.googleplay.fragment;

import java.util.ArrayList;

import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.domain.CategoryInfo;
import com.example.googleplay.holder.BaseHolder;
import com.example.googleplay.holder.CategoryHolder;
import com.example.googleplay.holder.TitleHolder;
import com.example.googleplay.http.protocl.CategoryProtocol;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.MyListView;
import com.example.googleplay.view.LoadingPage.ResultState;


import android.view.View;

/**
 * 分类 fragment
 * @author yuxuehai
 *
 */
public class CategoryFragment extends BaseFragment {

	private ArrayList<CategoryInfo> data;

	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		MyListView listView = new MyListView(UIUtils.getContext());
		listView.setAdapter(new CategoryAdapter(data));
		return listView;
	}

	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		CategoryProtocol protocol = new CategoryProtocol();
		data = protocol.getData(0);
		return check(data);
	}
	class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{

		public CategoryAdapter(ArrayList<CategoryInfo> data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public BaseHolder<CategoryInfo> getHolder(int position) {
			// TODO Auto-generated method stub
			CategoryInfo info = getItem(position);
			if (info.isTitle) {
				return new TitleHolder();// 标题栏holder
			}
			return new CategoryHolder();// 普通类型holer

		}
		@Override
		public boolean hasMore() {
			// TODO Auto-generated method stub
			return false;//隐藏加载更多
		}
		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return super.getViewTypeCount() + 1;
		}
		@Override
		public int getInnerType(int position) {
			// TODO Auto-generated method stub
			CategoryInfo info = getItem(position);
			if (info.isTitle) {// 标题栏类型
				return super.getInnerType(position) + 1;
			} else {// 普通类型
				return super.getInnerType(position);
			}
		}
		@Override
		public ArrayList<CategoryInfo> onLoadMore() {
			// TODO Auto-generated method stub
			return null;
		}

		
	}
}
