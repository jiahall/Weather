package com.example.weather;

public class WeatherModel {
	Double Heat;
	Long Humidity, Date;
	public WeatherModel(Double heat, Long humidity, Long date) {
		super();
		Heat = heat;
		Humidity = humidity;
		Date = date;
	}
	public Double getHeat() {
		return Heat;
	}
	public void setHeat(Double heat) {
		Heat = heat;
	}
	public Long getHumidity() {
		return Humidity;
	}
	public void setHumidity(Long humidity) {
		Humidity = humidity;
	}
	public Long getDate() {
		return Date;
	}
	public void setDate(Long date) {
		Date = date;
	}
	

}
