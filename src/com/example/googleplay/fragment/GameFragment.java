package com.example.googleplay.fragment;

import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.view.LoadingPage.ResultState;

import android.view.View;
import android.widget.TextView;

/**
 * Game fragment
 * @author yuxuehai
 *
 */
public class GameFragment extends BaseFragment {

	@Override
	public View onCreateSuccessView() {
		// TODO Auto-generated method stub
		TextView view = new TextView(UIUtils.getContext());
		view.setText("GameFragment");
		return view;
	}

	@Override
	public ResultState onLoad() {
		// TODO Auto-generated method stub
		return ResultState.STATE_SUCCESS;
	}

}
