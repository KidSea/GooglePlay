package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreHolder extends BaseHolder<Integer> {

	/**
	 * 加载更多的几种状态 1.可以加载更多 2.加载更多失败 3.没有加载更多
	 */
	public static final int STATE_MORE_MORE = 1;
	public static final int STATE_MORE_ERROR = 2;
	public static final int STATE_MORE_NONE = 3;
	private LinearLayout llLoadMore;
	private TextView tvLoadError;

	public MoreHolder(boolean hasMore) {
		// TODO Auto-generated constructor stub
		// 如果有更多数据状态为more_more,将此状态传递给父类的data,同时刷新界面
		if (hasMore) {
			setData(STATE_MORE_MORE);
		} else {
			setData(STATE_MORE_NONE);//最后会回调refreshView
		}
	}

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = UIUtils.inflate(R.layout.list_item_more);

		llLoadMore = (LinearLayout) view.findViewById(R.id.ll_loading_more);
		tvLoadError = (TextView) view.findViewById(R.id.tv_load_error);

		return view;
	}

	@Override
	public void refreshView(Integer data) {
		// TODO Auto-generated method stub
		switch (data) {
		case STATE_MORE_MORE:
			llLoadMore.setVisibility(View.VISIBLE);
			tvLoadError.setVisibility(View.GONE);
			break;
		case STATE_MORE_NONE:
			llLoadMore.setVisibility(View.GONE);
			tvLoadError.setVisibility(View.GONE);
			break;
		case STATE_MORE_ERROR:
			llLoadMore.setVisibility(View.GONE);
			tvLoadError.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}

}
