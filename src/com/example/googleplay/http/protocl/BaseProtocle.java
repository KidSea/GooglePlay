package com.example.googleplay.http.protocl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.R.string;

import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.http.HttpHelper.HttpResult;
import com.example.googleplay.utils.IOUtils;
import com.example.googleplay.utils.StringUtils;
import com.example.googleplay.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * 访问网络的基类
 * 
 * @author yuxuehai
 * @param <T>
 * 
 */
public abstract class BaseProtocle<T> {
	public T getData(int index) {
		// 先判断有没缓存，没有就进入加载数据
		String result = getCache(index);

		if (StringUtils.isEmpty(result)) {// 判断有没缓存或者缓存是否失效
			//请求服务器
			result = getDataFromServer(index);
		}
		//开始解析
		if(result != null){
			T data = parseData(result);
			return data;
		}
		return null;
	}

	// 从网络中获取数据
	// index表示的是从哪个位置开始返回20个数据，用于分页
	private String getDataFromServer(int index) {
		// TODO Auto-generated method stub

		HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey()
				+ "?index=" + index + getParams());

		if (httpResult != null) {
			String result = httpResult.getString();
			//System.out.println(result);
			
			//写缓存
			if(!StringUtils.isEmpty(result)){
				setCache(index, result);
			}
			
			return result;
		}
		return null;
	}

	// 获取网络关键词，子类必须实现
	public abstract String getKey();

	// 获取网络链接参数，子类必须实现
	public abstract String getParams();

	// 缓存方法，以URL为KEY，Json为value
	public void setCache(int index, String json) {
		// 以Url为文件名，以Json为内容，保存本地
		File cacheDir = UIUtils.getContext().getCacheDir();// 本应用的缓存文件
		// 生成缓存文件
		File cacheFile = new File(cacheDir, getKey() + "?index=" + index
				+ getParams());

		FileWriter writer = null;
		try {
			writer = new FileWriter(cacheFile);
			// 缓存截止日期
			long deadline = System.currentTimeMillis() + 30 * 60 * 1000; // 半个小时有效
			writer.write(deadline + "\n");
			writer.write(json);// 写入Json
			writer.flush();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}
	}

	// 读缓存
	public String getCache(int index) {
		// 以Url为文件名，以Json为内容，保存本地
		File cacheDir = UIUtils.getContext().getCacheDir();// 本应用的缓存文件
		// 生成缓存文件
		File cacheFile = new File(cacheDir, getKey() + "?index=" + index
				+ getParams());
		// 判断是否有缓存
		if (cacheFile.exists()) {
			// 判断缓存是否有效
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(cacheFile));
				String deadline = reader.readLine();
				long deadtime = Long.parseLong(deadline);

				if (System.currentTimeMillis() < deadtime) {// 当前时间小于有效期
					// 缓存有效
					StringBuffer buffer = new StringBuffer();
					String line;
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					return buffer.toString();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;

	}
	//解析数据
	public abstract T parseData(String result);
}
