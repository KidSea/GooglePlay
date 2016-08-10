package com.example.googleplay.fragment;

import java.util.ArrayList;

import com.example.googleplay.R;
import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.holder.BaseHolder;
import com.example.googleplay.holder.HomeHolder;
import com.example.googleplay.http.protocl.HomeProtocl;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.LoadingPage.ResultState;
import com.example.googleplay.view.MyListView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 主页fragment
 * 
 * @author yuxuehai
 * 
 */
public class HomeFragment extends BaseFragment {

	private ArrayList<AppInfo> data;

	// 加载成功就会回掉这个方法，加载布局，这个方法运行在住线程
	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		// TextView textView = new TextView(UIUtils.getContext());
		// textView.setText(getClass().getSimpleName());
		MyListView view = new MyListView(UIUtils.getContext());



		view.setAdapter(new HomeAdapter(data));
		return view;
	}

	// 运行在子线程，可以直接访问网络
	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub、
		// data = new ArrayList<String>();
		// for(int i=0;i<20;i++){
		// data.add("测试数据："+i);
		// }
		HomeProtocl homeProtocl = new HomeProtocl();
		data = homeProtocl.getData(0);
		return check(data);
	}

	class HomeAdapter extends MyBaseAdapter<AppInfo> {

		public HomeAdapter(ArrayList<AppInfo> data) {
			super(data);
			// TODO Auto-generated constructor stub
		}

		// @Override
		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		// // TODO Auto-generated method stub
		// ViewHolder holder;
		// if (convertView == null) {
		// //1.加载布局
		// convertView = UIUtils.inflate(R.layout.list_item_home);
		//
		// holder = new ViewHolder();
		// //2.加载控件
		// holder.tvContent = (TextView) convertView
		// .findViewById(R.id.tv_content);
		// //3.打一个标记
		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }
		// //4.根据数据刷新界面
		// String content = (String) getItem(position);
		// holder.tvContent.setText(content);
		// return convertView;
		// }

		@Override
		public BaseHolder<AppInfo> getHolder() {
			// TODO Auto-generated method stub

			return new HomeHolder();
		}

		// 此方法在子线程里
		@Override
		public ArrayList<AppInfo> onLoadMore() {
			// TODO Auto-generated method stub
			// ArrayList<String> moreData = new ArrayList<String>();
			// for(int i=0;i<20;i++){
			// moreData.add("测试更多数据" + i);
			// }
			//
			// SystemClock.sleep(2000);
			// 20，40，60
			// 下一页的数据位置正好等于集合大小
			HomeProtocl homeProtocl = new HomeProtocl();
			ArrayList<AppInfo> moredata = homeProtocl.getData(getListSize());
			return moredata;
		}

	}

	static class ViewHolder {

		public TextView tvContent;
	}
}
