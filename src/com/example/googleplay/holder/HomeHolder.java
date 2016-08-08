package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.utils.UIUtils;

import android.view.View;
import android.widget.TextView;
/**
 * 首页Holder
 * @author yuxuehai
 *
 */
public class HomeHolder extends BaseHolder<String> {

	private TextView tvContent;

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		// 1.加载布局
		View view = UIUtils.inflate(R.layout.list_item_home);
		tvContent = (TextView) view.findViewById(R.id.tv_content);
		
		return view;
	}

	@Override
	public void refreshView(String data) {
		// TODO Auto-generated method stub
		tvContent.setText(data);
	}

}
