package com.example.googleplay.adapter;

import java.util.ArrayList;

import com.example.googleplay.holder.BaseHolder;
import com.example.googleplay.holder.MoreHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	private static final int TYPE_NORMAL = 0;
	private static final int TYPE_LoadMore = 1;

	private ArrayList<T> data;

	public MyBaseAdapter(ArrayList<T> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size() + 1;// +1表示加载更多
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 返回当前布局类型个数,
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		// 2种：普通加+加载更多
		return 2;
	}

	// 返回当前布局类型
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (position == (getCount() - 1)) {
			return TYPE_LoadMore;
		} else {
			return getInnerType();
		}

	}

	// 子类可以重写此方法
	public int getInnerType() {
		return TYPE_NORMAL;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		BaseHolder holder;
		if (convertView == null) {
			// 1.加载控件
			// 2.加载布局
			// 3.打个标记
			if (getItemViewType(position) == TYPE_LoadMore) {
				// 加载更多
				holder = new MoreHolder(hasMore());
			} else {
				holder = getHolder();
			}

		} else {
			holder = (BaseHolder) convertView.getTag();
		}

		if (getItemViewType(position) != TYPE_LoadMore) {
			// 刷新数据
			holder.setData(getItem(position));
		} else {

		}

		return holder.getRootView();

	}
	//子类可以重写方法来决定是否加载更多
	public boolean hasMore(){
		return true;
	}
	// 返回当前页面的HOLDER对象，子类实现
	public abstract BaseHolder<T> getHolder();
}
