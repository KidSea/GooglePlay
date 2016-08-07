package com.example.googleplay.fragment;

import java.util.HashMap;

/**
 * 这是一个生产fragment的工产类
 * 
 * @author yuxuehai
 * 
 */
public class FragmentFactory {

	private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();
	
	
	public static BaseFragment createFragment(int position) {
		BaseFragment fragment = mFragmentMap.get(position);
		//先从集合中取，如果没有才创建对象，可以提高性能
		if (fragment == null) {

			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new RecommendFragment();
				break;
			case 5:
				fragment = new CategoryFragment();
				break;
			case 6:
				fragment = new HotFragment();
				break;
			default:
				break;
			}

			mFragmentMap.put(position, fragment);
		}

		return fragment;

	}
}
