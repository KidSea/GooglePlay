package com.example.googleplay.adapter;

import java.util.ArrayList;

import com.example.googleplay.holder.BaseHolder;
import com.example.googleplay.holder.MoreHolder;
import com.example.googleplay.utils.UIUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	private static final int TYPE_NORMAL = 1;//正常加载
	private static final int TYPE_LoadMore = 0;//加载更多

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
			return getInnerType(position);
		}

	}

	// 子类可以重写此方法
	public int getInnerType(int position) {
		return TYPE_NORMAL;
	}
	@SuppressWarnings("unchecked")
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
				// 返回加载更多Holder对象
				// 因为所有界面加载更多的UI显示效果都是一致的,所以加载更多的业务逻辑可以做详细处理
				holder = new MoreHolder(hasMore());
			} else {
				holder = getHolder(position);
			}

		} else {
			holder = (BaseHolder) convertView.getTag();
		}

		if (getItemViewType(position) != TYPE_LoadMore) {
			// 刷新数据
			holder.setData(getItem(position));
		} else {
			//加载更多的类型
			//一旦加载更多布局展现出来，就开始加载更多
			//只有在更多数据的状态下才加载更多
			MoreHolder moreHolder = (MoreHolder) holder;
			if(moreHolder.getData() == MoreHolder.STATE_MORE_MORE){
				loadMore(moreHolder);
			}
		}

		return holder.getRootView();

	}

	// 子类可以重写方法来决定是否加载更多
	public boolean hasMore() {
		return true;
	}

	// 返回当前页面的HOLDER对象，子类实现
	public abstract BaseHolder<T> getHolder(int position);

	// 标记是否正在加载更多
	private boolean isLoadMore = false;

	// 加载更多方法
	public void loadMore(final MoreHolder holder) {
		if (!isLoadMore) {
			isLoadMore = true;
			new Thread() {
				@Override
				public void run() {
					final ArrayList<T> moreData = onLoadMore();
					UIUtils.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (moreData != null) {
								// 每一页有20条数据，如果返回的数据小于20条，就认为到了最后一个了
								if (moreData.size() < 20) {
									holder.setData(MoreHolder.STATE_MORE_NONE);
									Toast.makeText(UIUtils.getContext(),
											"没有更多数据了", Toast.LENGTH_SHORT)
											.show();

								} else {
									// 还有更多数据
									holder.setData(MoreHolder.STATE_MORE_MORE);
								}
								//将数据加入集合
								data.addAll(moreData);
								//刷新页面
								MyBaseAdapter.this.notifyDataSetChanged();
								
							} else {
								// 加载更多失败
								holder.setData(MoreHolder.STATE_MORE_ERROR);
							}
							isLoadMore = false;
						}
					});

				}
			}.start();
		}

	}

	// 加载更多数据，必须由子类实现
	public abstract ArrayList<T> onLoadMore();
	//返回集合大小
	public int getListSize(){
		return data.size();
		
	}
}
