package com.example.googleplay.http.protocl;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * 排行页访问网络
 * 
 * @author yuxuehai
 * 
 */
public class HotProtocol extends BaseProtocle<ArrayList<String>> {

	private ArrayList<String> mHotList;// 推荐列表集合

	@Override
	public String getKey() {
		return "hot";
	}

	@Override
	public String getParams() {
		return "";
	}


	@Override
	public ArrayList<String> parseData(String result) {
		// TODO Auto-generated method stub
		try {
			JSONArray ja = new JSONArray(result);
			mHotList = new ArrayList<String>();
			for (int i = 0; i < ja.length(); i++) {
				String str = ja.getString(i);
				mHotList.add(str);
			}

			return mHotList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
