package com.example.googleplay.activity;

import com.example.googleplay.R;
import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.holder.DetailAppDesHolder;
import com.example.googleplay.holder.DetailAppInfoHolder;
import com.example.googleplay.holder.DetailAppPicsHolder;
import com.example.googleplay.holder.DetailAppSafeHolder;
import com.example.googleplay.holder.DetailDownloadHolder;
import com.example.googleplay.http.protocl.HomeDetailProtocol;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.LoadingPage;
import com.example.googleplay.view.LoadingPage.ResultState;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.textservice.SentenceSuggestionsInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

/**
 * 应用详情页
 * 
 * @author yuxuehai
 * 
 */
public class HomeDetailActivity extends BaseActivity {

	private LoadingPage mLoadingPage;
	private String packageName;
	private FrameLayout flDetailAppInfo;
	private AppInfo data;
	private FrameLayout flDetailSaveInfo;
	private HorizontalScrollView hsvDetailPics;
	private FrameLayout flDetailDesInfo;
	private FrameLayout flDetailDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 初始化加载页面
		mLoadingPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View onCreateSuccessView() {
				return HomeDetailActivity.this.onCreateSuccessView();
			}

			@Override
			public ResultState onLoad() {
				return HomeDetailActivity.this.onLoad();
			}

		};
		// 直接将一个VIEW传给它
		setContentView(mLoadingPage);

		packageName = getIntent().getStringExtra("package");

		mLoadingPage.loadData();
		initActionBar();
	}

	protected ResultState onLoad() {
		// TODO Auto-generated method stub
		// 请求网络，加载数据
		HomeDetailProtocol protocol = new HomeDetailProtocol(packageName);
		data = protocol.getData(0);

		if (data != null) {
			return ResultState.STATE_SUCCESS;
		} else {
			return ResultState.STATE_EMPTY;
		}

	}

	protected View onCreateSuccessView() {
		// TODO Auto-generated method stub
		// 初始化成功的布局
		View view = View.inflate(UIUtils.getContext(),
				R.layout.layout_home_detail, null);
		// 初始化帧布局
		flDetailAppInfo = (FrameLayout) view
				.findViewById(R.id.fl_detail_appinfo);

		// 给布局动态加载数据
		DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
		flDetailAppInfo.addView(appInfoHolder.getRootView());
		appInfoHolder.setData(data);

		// 动态加载安全布局
		flDetailSaveInfo = (FrameLayout) view
				.findViewById(R.id.fl_detail_safeinfo);
		DetailAppSafeHolder saveInfoHolder = new DetailAppSafeHolder();
		flDetailSaveInfo.addView(saveInfoHolder.getRootView());
		saveInfoHolder.setData(data);

		// 初始化图片布局
		hsvDetailPics = (HorizontalScrollView) view
				.findViewById(R.id.hsv_detail_pics);
		DetailAppPicsHolder appPicsHolder = new DetailAppPicsHolder();
		hsvDetailPics.addView(appPicsHolder.getRootView());
		appPicsHolder.setData(data);

		// 初始化描述布局
		flDetailDesInfo = (FrameLayout) view.findViewById(R.id.fl_detail_des);
		DetailAppDesHolder appDesHolder = new DetailAppDesHolder();
		flDetailDesInfo.addView(appDesHolder.getRootView());
		appDesHolder.setData(data);

		// 初始化下载布局
		flDetailDownload = (FrameLayout) view
				.findViewById(R.id.fl_detail_downlod);
		DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
		flDetailDownload.addView(downloadHolder.getRootView());
		downloadHolder.setData(data);

		return view;
	}
	/**
	 * 初始化actionbar
	 */
	private void initActionBar() {
		// 获取actionbar对象
		ActionBar actionBar = getSupportActionBar();
		// 左上角显示logo
		// 左上角显示返回图标, 和侧边栏绑定后显示侧边栏图标
		actionBar.setDisplayHomeAsUpEnabled(true);

	}
	// ActionBar上的按钮被点击后的回调方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:// 左上角logo处被点击
			finish();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
