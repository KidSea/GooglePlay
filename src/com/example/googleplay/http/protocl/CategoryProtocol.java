package com.example.googleplay.http.protocl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.googleplay.domain.CategoryInfo;


/**
 * 分类页访问网络
 * 
 * @author yuxuehai
 * 
 */
public class CategoryProtocol extends BaseProtocle<ArrayList<CategoryInfo>> {

	@Override
	public String getKey() {
		return "category";
	}

	@Override
	public String getParams() {
		return "";
	}



	@Override
	public ArrayList<CategoryInfo> parseData(String result) {
		try {
			JSONArray ja = new JSONArray(result);

			ArrayList<CategoryInfo> list = new ArrayList<CategoryInfo>();
			for (int i = 0; i < ja.length(); i++) {
				CategoryInfo titleInfo = new CategoryInfo();
				JSONObject jo = (JSONObject) ja.get(i);

				// 添加标题对象
				if (jo.has("title")) {
					String title = jo.getString("title");
					titleInfo.isTitle = true;
					titleInfo.title = title;
					list.add(titleInfo);
				}
				
				if (jo.has("infos")) {
					JSONArray array = jo.getJSONArray("infos");
					for (int j = 0; j < array.length(); j++) {
						CategoryInfo info = new CategoryInfo();
						JSONObject obj = array.getJSONObject(j);
						info.name1 = obj.getString("name1");
						info.name2 = obj.getString("name2");
						info.name3 = obj.getString("name3");
						info.url1 = obj.getString("url1");
						info.url2 = obj.getString("url2");
						info.url3 = obj.getString("url3");
						info.isTitle = false;
						list.add(info);
					}
				}
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}
