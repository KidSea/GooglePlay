package com.example.googleplay.http.protocl;

import java.util.ArrayList;

import org.json.JSONArray;
/**
 * 推荐类别访问网络的请求
 * @author yuxuehai
 *
 */
public class RecommendProtocl extends BaseProtocle<ArrayList<String>> {
	private ArrayList<String> mRecommendList;// 推荐列表集合

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "recommend";
	}

	@Override
	public String getParams() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ArrayList<String> parseData(String result) {
		// TODO Auto-generated method stub
		try {
			JSONArray ja = new JSONArray(result);
			mRecommendList = new ArrayList<String>();
			for (int i = 0; i < ja.length(); i++) {
				String str = ja.getString(i);
				mRecommendList.add(str);
			}

			return mRecommendList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
