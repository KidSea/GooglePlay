package com.example.googleplay.http.protocl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.googleplay.domain.AppInfo;
import com.example.googleplay.domain.SubjectInfo;

/**
 * 专题的网络请求
 * 
 * @author yuxuehai
 * 
 */
public class SubjectProtocle extends BaseProtocle<ArrayList<SubjectInfo>> {
	private ArrayList<SubjectInfo> mAppList;// 应用列表集合

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "subject";
	}

	@Override
	public String getParams() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ArrayList<SubjectInfo> parseData(String result) {
		// TODO Auto-generated method stub

		try {
			JSONArray ja = new JSONArray(result);

			// 解析应用列表集合
			mAppList = new ArrayList<SubjectInfo>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				SubjectInfo info = new SubjectInfo();
				
				info.des = jo.getString("des");
				info.url = jo.getString("url");
				
				mAppList.add(info);
			}
			return mAppList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
