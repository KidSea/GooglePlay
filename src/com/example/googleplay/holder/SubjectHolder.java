package com.example.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleplay.R;
import com.example.googleplay.domain.SubjectInfo;
import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;


import com.lidroid.xutils.BitmapUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {
	private ImageView ivPic;
	private TextView tvDes;
	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(UIUtils.getContext(),
				R.layout.list_item_subject, null);
		ivPic = (ImageView) view.findViewById(R.id.iv_pic);
		tvDes = (TextView) view.findViewById(R.id.tv_des);

		mBitmapUtils = BitmapHelper.getBitmapUtils();
		mBitmapUtils.configDefaultLoadingImage(R.drawable.subject_default);
		return view;
	}

	@Override
	public void refreshView(SubjectInfo data) {
		// TODO Auto-generated method stub
		if (data != null) {
			tvDes.setText(data.des);
			mBitmapUtils.display(ivPic, HttpHelper.URL + "image?name="
					+ data.url);
		}
	}

}
