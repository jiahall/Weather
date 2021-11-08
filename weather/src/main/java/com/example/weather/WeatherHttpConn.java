package com.example.weather;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WeatherHttpConn {

	String response = "";

	public String getWeather(Double latitude, Double longitude) {
		
		String response = "";

		try {

			URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude
					+ "&exclude=hourly,minutely,%20alerts&appid=91a5e3d94708c57e8248a454d817a493");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responsecode = conn.getResponseCode();

			if (responsecode != 200) {

				response = "error, please check longitude/latitude and try again";
				return response;

			} else {

				String inline = "";
				Scanner scanner = new Scanner(url.openStream());

				while (scanner.hasNext()) {
					inline += scanner.nextLine();
				}

				scanner.close();

				JSONParser parse = new JSONParser();
				JSONObject baseJSON = (JSONObject) parse.parse(inline);

				JSONArray dailyArray = (JSONArray) baseJSON.get("daily");
				
				Double tempHeat = 0.0;
				Long tempHumidity = 0L;
				JSONObject dailyObject;
				JSONObject temperatureObject;
				WeatherModel hottestDay = new WeatherModel(0.0, 0L, 0L);

				for (int i = 0; i < dailyArray.size(); i++) {

					dailyObject = (JSONObject) dailyArray.get(i);
					temperatureObject = (JSONObject) dailyObject.get("temp");
					tempHumidity = (Long) dailyObject.get("humidity");
					tempHeat = (Double) temperatureObject.get("max");

					if (tempHeat > hottestDay.getHeat()) {
						hottestDay.setDate((Long) dailyObject.get("dt"));
						hottestDay.setHumidity(tempHumidity);
						hottestDay.setHeat(tempHeat);
					} else if (tempHeat == hottestDay.getHeat()) {
						if (tempHumidity > hottestDay.getHumidity()) {
							hottestDay.setDate((Long) dailyObject.get("dt"));
							hottestDay.setHumidity(tempHumidity);

						}
					}

				}
				Date date = new Date(hottestDay.getDate() * 1000);
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String epochDateString = formatter.format(date);
				response = "the hottest day will be: " + epochDateString + " with the max temperature being: "
						+ hottestDay.getHeat();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
