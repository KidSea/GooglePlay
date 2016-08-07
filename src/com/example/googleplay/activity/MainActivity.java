package com.example.googleplay.activity;

import com.example.googleplay.R;
import com.example.googleplay.R.layout;
import com.example.googleplay.fragment.BaseFragment;
import com.example.googleplay.fragment.FragmentFactory;
import com.example.googleplay.view.PagerTab;
import com.example.utils.UIUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
}
