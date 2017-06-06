package com.yaler.di.shopd.tool;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyUtil {

	private static Properties prop = null;

	public synchronized static void initProperty(String propertyName) throws Exception {
		if (prop == null) {
			prop = new Properties();
			InputStream inputstream = null;
			ClassLoader cl = PropertyUtil.class.getClassLoader();

			if (cl != null) {
				inputstream = cl.getResourceAsStream(propertyName);
			} else {
				inputstream = ClassLoader.getSystemResourceAsStream(propertyName);
			}


			if (inputstream == null) {
				throw new Exception("inputstream " + propertyName + " open null");
			}

			prop.load(new InputStreamReader(inputstream,"UTF-8"));
			inputstream.close();
			inputstream = null;
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param propertyName
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String propertyName, String key) {
		String result = "";
		try {
			initProperty(propertyName);
			result = prop.getProperty(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param propertyName
	 * @param key
	 * @return
	 */
	public static Integer getIntegerValueByKey(String propertyName, String key) {
		try {
			initProperty(propertyName);
			String result = prop.getProperty(key);
			return Integer.valueOf(result);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param propertyName
	 * @param key
	 * @return
	 */
	public static Double getDoubleValueByKey(String propertyName, String key) {
		try {
			initProperty(propertyName);
			String result = prop.getProperty(key);
			return Double.valueOf(result);
		} catch (Exception e) {
			e.printStackTrace();
			return -1.0;
		}
	}
	
	

}