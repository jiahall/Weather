package com.example.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WeatherController {
	WeatherHttpConn weatherHttpConn = new WeatherHttpConn();
	String response = "";

	@GetMapping(value = "/{latitude}/{longitude}")
	public String getTest(@PathVariable("longitude") Double longitude, @PathVariable("latitude") Double latitude) {

		response = weatherHttpConn.getWeather(latitude, longitude);

		return response;

	}

}
