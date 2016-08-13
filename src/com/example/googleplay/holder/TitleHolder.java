package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.domain.CategoryInfo;
import com.example.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.TextView;



/**
 * 分类页标题栏holder
 * 
 * @author yuxuehai
 */
public class TitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tvTitle;

	@Override
	public View initView() {
		View view = View.inflate(UIUtils.getContext(),
				R.layout.list_item_title, null);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView(CategoryInfo data) {
		tvTitle.setText(data.title);
	}

}
