package com.example.googleplay.activity;

import com.example.googleplay.R;
import com.example.googleplay.R.layout;
import com.example.googleplay.view.PagerTab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


/**
 * baseactivity继承fragmentactivity
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
	    
	    mPagerTab.setViewPager(mViewPager);//将指针绑定Viewpager
		
	}

	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		//返回当前位置fragment对象
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		//获得fragment对象个数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
