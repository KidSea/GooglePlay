package com.example.googleplay.fragment;

import java.util.ArrayList;
import java.util.Random;

import com.example.googleplay.http.protocl.RecommendProtocl;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.LoadingPage.ResultState;
import com.example.googleplay.view.fly.ShakeListener;
import com.example.googleplay.view.fly.ShakeListener.OnShakeListener;
import com.example.googleplay.view.fly.StellarMap;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 推荐 fragment
 * 
 * @author yuxuehai
 * 
 */
public class RecommendFragment extends BaseFragment {

	private ArrayList<String> data;

	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		final StellarMap stellar = new StellarMap(UIUtils.getContext());

		stellar.setAdapter(new RecommendAdapter());
		// 设置随机方式,将控件划分为9行6列，在格子里随机出现
		stellar.setRegularity(6, 9);
		// 设置内编剧
		stellar.setInnerPadding(10, 10, 10, 10);

		// 设置默认值内面,第一组
		stellar.setGroup(0, true);

		ShakeListener shake = new ShakeListener(UIUtils.getContext());
		shake.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				stellar.zoomIn();// 調到下一個頁數
			}
		});

		return stellar;
	}

	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		RecommendProtocl protocl = new RecommendProtocl();
		data = protocl.getData(0);

		return check(data);
	}

	class RecommendAdapter implements StellarMap.Adapter {

		// 返回组的数量
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		// 返回某组的个数
		@Override
		public int getCount(int group) {
			// TODO Auto-generated method stub
			int count = data.size() / getGroupCount();// 用总数除以组个数就是每组应该展示的孩子的个数
			if (group == getGroupCount() - 1) {// 由于上一行代码不一定整除, 最后一组,将余数补上
				count += data.size() % getGroupCount();
			}

			return count;
		}

		// 初始化布局
		@Override
		public View getView(int group, int position, View convertView) {
			// TODO Auto-generated method stub
			if (group > 0) {// 如果发现不是第一组,需要更新position, 要加上之前几页的总数,才是当前组的准确位置
				position = position + getCount(group - 1) * group;
			}
			final String keyword = data.get(position);
			TextView view = new TextView(UIUtils.getContext());
			view.setText(data.get(position));

			// 设置随机文字大小
			Random random = new Random();
			int size = 16 + random.nextInt(10);// 产生16-25的随机数
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);// 以sp为单位设置文字大小

			// 设置随机文字颜色
			int r = 30 + random.nextInt(210);// 产生30-239的随机颜色, 绕过0-29,
												// 240-255的值,避免颜色过暗或者过亮
			int g = 30 + random.nextInt(210);
			int b = 30 + random.nextInt(210);
			view.setTextColor(Color.rgb(r, g, b));

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(UIUtils.getContext(), keyword,
							Toast.LENGTH_SHORT).show();
				}
			});

			return view;
		}

		// 返回下组ID
		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			// TODO Auto-generated method stub
			if (!isZoomIn) {
				// 下一组
				if (group < getGroupCount() - 1) {
					return ++group;
				} else {
					return 0;// 如果没有下一页了,就跳到第一组
				}
			} else {
				// 上一组
				if (group > 0) {
					return --group;
				} else {
					return getGroupCount() - 1;// 如果没有上一页了,就跳到最后一组
				}
			}

		}

	}
}
