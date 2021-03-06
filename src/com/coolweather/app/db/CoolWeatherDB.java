package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class CoolWeatherDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 构造方法私有化
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取CoolWeatherDB的实例
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	/**
	 * 从数据库读取全国所有的省份
	 */
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("_id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while(cursor.moveToNext());
			if(cursor != null){
				cursor.close();
			}
			
		}
		return list;
	}
	
	/**
	 * 将City实例存储到数据库
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceID());
			db.insert("city", null, values);
		}
	}
	
	/**
	 * 从数据库读取某省份的所有城市信息
	 */
	public List<City> loadCities(int provinceID){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id =?", new String[] {String.valueOf(provinceID)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("_id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceID(provinceID);
				list.add(city);
			} while(cursor.moveToNext());
			if(cursor != null){
				cursor.close();
			}
			
		}
		return list;
	}
	
	
	/**
	 * 将County实例存储到数据库
	 */
	public void saveCounty(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("county_id", county.getCityID());
			db.insert("county", null, values);
		}
	}
	
	/**
	 * 从数据库读取某城市的所有县的信息
	 */
	public List<County> loadCounties(int cityID){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id =?", new String[] {String.valueOf(cityID)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("_id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("city_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("city_code")));
				county.setCityID(cityID);
				list.add(county);
			} while(cursor.moveToNext());
			if(cursor != null){
				cursor.close();
			}
			
		}
		return list;
	}
	
	
	
}
