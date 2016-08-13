package com.example.googleplay.view;

import com.example.googleplay.R;
import com.example.googleplay.utils.UIUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 根据当前状态来显示不同页面的自定义控件 -未加载 -加载中 -加载失败 -数据为空 -加载成功
 * 
 * @author yuxuehai
 * 
 */
public abstract class LoadingPage extends FrameLayout {

	private static final int STATE_UNLOAD = 0;// 未加载
	private static final int STATE_LOADING = 1;// 正在加载
	private static final int STATE_LOAD_EMPTY = 2;// 数据为空
	private static final int STATE_LOAD_ERROR = 3;// 加载失败
	private static final int STATE_LOAD_SUCCESS = 4;// 访问成功

	private int mCurrentState = STATE_UNLOAD;// 当前状态
	private View mLodingPage;
	private View mErrorPage;
	private View mEmptyPage;
	private View mSuccessPage;

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public LoadingPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// 加载中的布局
		if (mLodingPage == null) {
			mLodingPage = UIUtils.inflate(R.layout.page_loding);
			addView(mLodingPage);// 将加载中的布局放入帧布局
		}
		// 加载失败的布局
		if (mErrorPage == null) {
			mErrorPage = UIUtils.inflate(R.layout.page_error);
			//点击重试事件
			Button button = (Button) mErrorPage.findViewById(R.id.btn_retry);
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//重新加载数据
					loadData();
				}
			});
			addView(mErrorPage);// 将加载失败的布局放入帧布局
		}

		// 加载空的布局
		if (mEmptyPage == null) {
			mEmptyPage = UIUtils.inflate(R.layout.pager_empty);
			addView(mEmptyPage);// 将空的布局放入帧布局
		}

		showRightPage();
	}

	// 根据当前状态显示布局
	private void showRightPage() {
		// TODO Auto-generated method stub
		if (mLodingPage != null) {

			mLodingPage
					.setVisibility((mCurrentState == STATE_LOADING || mCurrentState == STATE_UNLOAD) ? View.VISIBLE
							: View.GONE);
		}

		if (mEmptyPage != null) {

			mEmptyPage
					.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE
							: View.GONE);
		}

		if (mErrorPage != null) {

			mErrorPage
					.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE
							: View.GONE);
		}
		// 初始化成功界面
		if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
			mSuccessPage = onCreateSuccessView();
			if (mSuccessPage != null) {

				addView(mSuccessPage);
			}
		}
		if (mSuccessPage != null) {
			mSuccessPage
					.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE
							: View.GONE);
		}
	}

	// 开始加载数据
	public void loadData() {
		// 状态归零
		if (mCurrentState == STATE_LOAD_EMPTY
				|| mCurrentState == STATE_LOAD_ERROR
				|| mCurrentState == STATE_LOAD_SUCCESS) {
			mCurrentState = STATE_UNLOAD;
		}
		
		if (mCurrentState != STATE_LOADING) {// 如果当前没有加载，就开始加载数据
			mCurrentState = STATE_LOADING;
			new Thread() {
				public void run() {
					final ResultState resultState = onLoad();
					
					//子线程不能更新UI 必须在住线程
					UIUtils.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (resultState != null) {
								mCurrentState = resultState.getState();// 加载结束后更新网络状态

								// 根据最新的状态显示页面
								showRightPage();

							}
						}
					});
				
				};
			}.start();

		}
	}

	// 加载成功后显示的布局，必须由调用者来实现
	public abstract View onCreateSuccessView();

	// 加载网络数据,返回值为反问成功的值
	public abstract ResultState onLoad();

	/**
	 * 使用枚举表示访问网络的几种状态
	 */
	public enum ResultState {
		STATE_SUCCESS(STATE_LOAD_SUCCESS), // 访问成功
		STATE_EMPTY(STATE_LOAD_EMPTY), // 数据为空
		STATE_ERROR(STATE_LOAD_ERROR);// 访问失败

		private int state;

		private ResultState(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}
	}
}
