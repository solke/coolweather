package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {
	/**
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvince = response.split(",");
			if(allProvince != null && allProvince.length > 0){
				for(String p : allProvince){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceID){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length > 0){
				for(String p : allCities){
					String[] array = p.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceID(provinceID);
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
		
	}
	
	
	/**
	 * 解析和处理服务器返回的县级数据
	 */
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityID){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties != null && allCounties.length > 0){
				for(String p : allCounties){
					String[] array = p.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityID(cityID);
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
		
	}
}
