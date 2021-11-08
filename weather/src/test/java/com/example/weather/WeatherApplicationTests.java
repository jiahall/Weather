package com.example.weather;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class WeatherApplicationTests {
	
	WeatherModel weatherModel; 
	ArrayList<WeatherModel> weatherList = new ArrayList<WeatherModel>();
	
	@BeforeEach
	public void setUp() {
		
		WeatherModel weather1= new WeatherModel(11.1, 1L, 30L);
		WeatherModel weather2= new WeatherModel(12.2, 2L, 30L);
		WeatherModel weather3= new WeatherModel(13.3, 3L, 30L);
		WeatherModel weather4= new WeatherModel(13.3, 4L, 30L);
		WeatherModel weather5= new WeatherModel(15.6, 6L, 30L);
		WeatherModel weather6= new WeatherModel(15.6, 5L, 30L);
		weatherList.add(weather1);
		weatherList.add(weather2);
		weatherList.add(weather3);
		weatherList.add(weather4);
		weatherList.add(weather5);
		weatherList.add(weather6);
		
	}
	
	@Test 
	public void getWeather() {
		String response = "";
		try {
		
		URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=89&lon=-0.13878781625840952&exclude=hourly,minutely,%20alerts&appid=91a5e3d94708c57e8248a454d817a493");
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		
		int responsecode = conn.getResponseCode();
        if (responsecode != 200) {
        
        	response ="error, please check longitude/latitude and try again";
        } else {
		
		String inline = "";
		Scanner scanner = new Scanner(url.openStream());
		
		while(scanner.hasNext()) {
			inline += scanner.nextLine();
		}
		
		scanner.close();
		
		JSONParser parse = new JSONParser();
		JSONObject baseJSON = (JSONObject)parse.parse(inline); 
		
		JSONArray dailyArray = (JSONArray) baseJSON.get("daily");
		assertEquals(8, dailyArray.size());


		}
		
        
		
		
		}catch ( Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getError() {
		String response="";
		try {
			
			URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=50.824955973889&lon=3878781625840952&exclude=hourly,minutely,%20alerts&appid=91a5e3d94708c57e8248a454d817a493");
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responsecode = conn.getResponseCode();
			
	        if (responsecode != 200) {
	        	response ="error, please check longitude/latitude and try again";
	        }
	        	
			assertTrue(response.matches("error, please check longitude/latitude and try again"));

			}catch ( Exception e) {
				e.printStackTrace();
			}
	
		
	}

	@Test
	void getHottestWeather() {
		WeatherModel hottest= new WeatherModel(0.0, 0L, 0L);

		for( int i =0; i < weatherList.size(); i++) {
			if(weatherList.get(i).getHeat() >hottest.getHeat()) {
				hottest = weatherList.get(i);
						
			}else if (weatherList.get(i).getHeat() ==hottest.getHeat()) {
				
			
				if(weatherList.get(i).getHumidity() >hottest.getHumidity()) {
					hottest = weatherList.get(i);
				}
			}  
	}
assertEquals(6L, hottest.getHumidity());
	}
}
