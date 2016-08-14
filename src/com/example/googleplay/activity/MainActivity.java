package com.example.googleplay.activity;

import com.example.googleplay.R;
import com.example.googleplay.R.layout;
import com.example.googleplay.fragment.BaseFragment;
import com.example.googleplay.fragment.FragmentFactory;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.PagerTab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * baseactivity继承fragmentactivity
 * 当项目和appcompat关联在一起时，就必须要在清单文件中设置Theme.Appcompat的主题，否则奔溃
 * 
 * @author yuxuehai
 * 
 */
public class MainActivity extends BaseActivity {

	private PagerTab mPagerTab;
	private ViewPager mViewPager;
	private MyAdapter mAdapter;
	private DrawerLayout mDrawerLayout;// 侧边栏布局
	private ActionBarDrawerToggle mToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
		mViewPager = (ViewPager) findViewById(R.id.vp_pager);

		mAdapter = new MyAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);

		mPagerTab.setViewPager(mViewPager);// 将指针绑定Viewpager
		
		mPagerTab.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				BaseFragment fragment = FragmentFactory.createFragment(arg0);
				//开始加载数据
				fragment.loadData();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		initActionBar();

	}
	/**
	 * FragmentPagerAdapter是PagerAdapter的子类，如果viewpager的页面是fragment的话，就使用子类
	 * @author yuxuehai
	 *
	 */
	class MyAdapter extends FragmentPagerAdapter {

		private String[] mTabNames;

		public MyAdapter(FragmentManager fm) {
			super(fm);
			mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页签字符标题的数组
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return mTabNames[position];
		}

		// 返回当前位置fragment对象
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			BaseFragment fragment = FragmentFactory.createFragment(arg0);

			return fragment;
		}

		// 获得fragment对象个数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTabNames.length;
		}

	}
	/**
	 * 初始化actionbar
	 */
	private void initActionBar() {
		// 获取actionbar对象
		ActionBar actionBar = getSupportActionBar();
		// 左上角显示logo
		actionBar.setHomeButtonEnabled(true);
		// 左上角显示返回图标, 和侧边栏绑定后显示侧边栏图标
		actionBar.setDisplayHomeAsUpEnabled(true);

		// 初始化侧边栏开关
		mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_am, R.string.drawer_open,
				R.string.drawer_close);// 参2:DrawerLayout对象, 参3:侧边栏开关图标,
										// 参4:打开侧边栏文本描述;参5:关闭侧边栏文本描述
		// 调用当前同步方法，才可以将侧拉菜单和按钮的点击操作绑定起来
		mToggle.syncState();
	}
	// ActionBar上的按钮被点击后的回调方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:// 左上角logo处被点击
			mToggle.onOptionsItemSelected(item);//侧边栏收起或者关闭
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
