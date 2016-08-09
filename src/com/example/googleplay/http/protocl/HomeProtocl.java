package com.example.googleplay.http.protocl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.googleplay.domain.AppInfo;

public class HomeProtocl extends BaseProtocle<ArrayList<AppInfo>> {

	private ArrayList<AppInfo> mAppList;// 应用列表集合
	private ArrayList<String> mPicList;// 广告图片url集合

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "home";
	}

	@Override
	public String getParams() {
		// TODO Auto-generated method stub
		return "";// 没有参数，就传空串
	}

	@Override
	public ArrayList<AppInfo> parseData(String result) {
		// TODO Auto-generated method stub
		// Gson ,JsonObject
		// 使用这个JsonObject，如果遇到{},就是JsonObject;如果遇到[],就是JsonArry
		try {
			JSONObject jo = new JSONObject(result);

			// 解析应用列表集合
			JSONArray ja = jo.getJSONArray("list");
			mAppList = new ArrayList<AppInfo>();
			for (int i = 0; i < ja.length(); i++) {
				AppInfo info = new AppInfo();

				JSONObject jo1 = (JSONObject) ja.get(i);
				info.des = jo1.getString("des");
				info.downloadUrl = jo1.getString("downloadUrl");
				info.iconUrl = jo1.getString("iconUrl");
				info.id = jo1.getString("id");
				info.name = jo1.getString("name");
				info.packageName = jo1.getString("packageName");
				info.size = jo1.getLong("size");
				info.stars = jo1.getDouble("stars");

				mAppList.add(info);
			}
			// 解析头条广告图片信息
			mPicList = new ArrayList<String>();
			JSONArray ja1 = jo.getJSONArray("picture");
			for (int i = 0; i < ja1.length(); i++) {
				mPicList.add(ja1.getString(i));
			}

			return mAppList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> getPicList() {
		return mPicList;
	}
}
