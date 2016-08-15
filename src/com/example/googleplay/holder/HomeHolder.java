package com.example.googleplay.holder;

import java.text.Format;

import com.example.googleplay.R;
import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.domain.DownloadInfo;
import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.manager.DownloadManager.DownloadObserver;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.ProgressArc;

import com.lidroid.xutils.BitmapUtils;

import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 首页Holder
 * 
 * @author yuxuehai
 * 
 */
public class HomeHolder extends BaseHolder<AppInfo> implements
		DownloadObserver, OnClickListener {
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
	private DownloadManager mDownloadManager;
	private FrameLayout flProgress;
	private ProgressArc pbProgress;

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
		mBitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

		pbProgress = new ProgressArc(UIUtils.getContext());
		// 设置圆形进度的直径
		pbProgress.setArcDiameter(UIUtils.dip2px(26));
		// 设置进度条延时
		pbProgress.setProgressColor(UIUtils.getColor(R.color.progress));
		// 设置进度条高度布局参数
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				UIUtils.dip2px(27), UIUtils.dip2px(27));
		flDownload.addView(pbProgress,params);

		flDownload.setOnClickListener(this);
		mDownloadManager = DownloadManager.getInstance();
		// 监听下载进度
		mDownloadManager.registerObserver(this);

		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		// TODO Auto-generated method stub
		// tvContent.setText(data.name);
		
		if (data != null) {
			tvName.setText(data.name);
			tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),
					data.size));
			tvDesc.setText(data.des);

			rbStar.setRating((float) data.stars);
			mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name="
					+ data.iconUrl);
			mAppInfo = data;
			
			DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data);
			if (downloadInfo == null) {
				// 没有下载过
				mCurrentState = DownloadManager.STATE_NONE;
				mProgress = 0;
			} else {
				// 之前下载过, 以内存中的对象的状态为准
				mCurrentState = downloadInfo.currentState;
				mProgress = downloadInfo.getProgress();
			}

			// 根据对象状态刷新界面
			refreshUI(mProgress, mCurrentState, data.id);
		}
	}

	/**
	 * 刷新界面
	 * 
	 * @param progress
	 * @param state
	 * @param id 
	 */
	private void refreshUI(float progress, int state, String id) {
		// TODO Auto-generated method stub
		// 判断要刷新的下载对象是否是当前的应用
		//由于LISTVIEW的重用机制，要确保刷新之前，确实是同一个应用
		if(!getData().id.equals(id)){
			return;
		}
		mCurrentState = state;
		mProgress = progress;
		switch (state) {
		case DownloadManager.STATE_NONE:
			// 自定义进度条背景
			pbProgress.setBackgroundResource(R.drawable.ic_download);
			// 没有进度
			pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			tvDownload.setText(UIUtils.getString(R.string.app_state_download));
			break;
		case DownloadManager.STATE_WAITING:
			pbProgress.setBackgroundResource(R.drawable.ic_download);
			// 等待模式
			pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
			tvDownload.setText(UIUtils.getString(R.string.app_state_waiting));
			break;
		case DownloadManager.STATE_DOWNLOAD:
			pbProgress.setBackgroundResource(R.drawable.ic_pause);
			// 下载模式
			pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
			pbProgress.setProgress(progress, true);
			tvDownload.setText((int) (progress * 100) + "%");
			break;
		case DownloadManager.STATE_PAUSE:
			pbProgress.setBackgroundResource(R.drawable.ic_resume);
			// 暂停模式
			pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			flDownload.setVisibility(View.VISIBLE);
			break;
		case DownloadManager.STATE_ERROR:
			pbProgress.setBackgroundResource(R.drawable.ic_redownload);
			pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			tvDownload.setText(UIUtils.getString(R.string.app_state_error));
			break;
		case DownloadManager.STATE_SUCCESS:
			pbProgress.setBackgroundResource(R.drawable.ic_install);
			pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			tvDownload
					.setText(UIUtils.getString(R.string.app_state_downloaded));
			break;

		default:
			break;
		}
	}

	@Override
	public void onDownloadStateChanged(DownloadInfo info) {
		// TODO Auto-generated method stub
		refreshOnMainThread(info);
	}

	@Override
	public void onDownloadProgressChanged(DownloadInfo info) {
		// TODO Auto-generated method stub
		refreshOnMainThread(info);
	}

	// 主线程刷新ui
	private void refreshOnMainThread(final DownloadInfo info) {
		// 判断要刷新的下载对象是否是当前的应用
		AppInfo appInfo = getData();
		if (info.id.equals(appInfo.id)) {
			UIUtils.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					refreshUI(info.getProgress(), info.currentState,info.id);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fl_download:
			// 根据当前状态来决定相关操作
			if (mCurrentState == DownloadManager.STATE_NONE
					|| mCurrentState == DownloadManager.STATE_PAUSE
					|| mCurrentState == DownloadManager.STATE_ERROR) {
				// 开始下载
				mDownloadManager.download(mAppInfo);
			} else if (mCurrentState == DownloadManager.STATE_DOWNLOAD
					|| mCurrentState == DownloadManager.STATE_WAITING) {
				// 暂停下载
				mDownloadManager.pause(mAppInfo);
			} else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
				// 开始安装
				mDownloadManager.install(mAppInfo);
			}
			break;

		default:
			break;
		}
	}

}
