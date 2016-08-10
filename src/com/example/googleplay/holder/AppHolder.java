package com.example.googleplay.holder;

import java.text.Format;

import com.example.googleplay.R;
import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;

import com.lidroid.xutils.BitmapUtils;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * App Holder
 * 
 * @author yuxuehai
 * 
 */
public class AppHolder extends BaseHolder<AppInfo> {
	private TextView tvName;
	private ImageView ivIcon;
	private TextView tvSize;
	private TextView tvDesc;
	private RatingBar rbStar;
	private FrameLayout flDownload;
	private TextView tvDownload;
	// private ProgressArc pbProgress;

	private BitmapUtils mBitmapUtils;

	// private DownloadManager mDownloadManager;
	private float mProgress;// 当前下载进度
	private int mCurrentState;// 当前下载状态

	private AppInfo mAppInfo;

	// private TextView tvContent;

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		// 1.加载布局
		View view = UIUtils.inflate(R.layout.list_item_home);
		// tvContent = (TextView) view.findViewById(R.id.tv_content);
		// 2.初始化控件
		tvName = (TextView) view.findViewById(R.id.tv_name);
		ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
		tvSize = (TextView) view.findViewById(R.id.tv_size);
		tvDesc = (TextView) view.findViewById(R.id.tv_desc);
		rbStar = (RatingBar) view.findViewById(R.id.rb_star);
		flDownload = (FrameLayout) view.findViewById(R.id.fl_download);
		tvDownload = (TextView) view.findViewById(R.id.tv_download);

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		// TODO Auto-generated method stub
		// tvContent.setText(data.name);
		tvName.setText(data.name);
		tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
		tvDesc.setText(data.des);

		rbStar.setRating((float) data.stars);
		mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name="
				+ data.iconUrl);
	}

}
