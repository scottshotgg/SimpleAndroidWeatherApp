package com.example.scottgaydos.weatherapp;

/**
 * Created by scottgaydos on 11/27/14.
 */
public class WeatherData
{
	String cityName = null;
	String countryName = null;

	double maxTemp = 0;
	double maxTempMetric = 0;
	double minTemp = 0;
	double minTempMetric = 0;

	double latitude = 0;
	double longitude = 0;

	double windDegree = 0;
	int windDegreeInt = 0;
	double windSpeed = 0;
	double windSpeedMetric = 0;
	String cardinalDirection = null;
	String cda = null;

	public WeatherData()
	{

	}

	public void setCardinalDirection()
	{
		windDegreeInt = (int)windDegree;

		if (windDegreeInt == 0)
		{
			cardinalDirection = "East";
			cda = "E";
		}
		else if (windDegreeInt < 90)
		{
			cardinalDirection = "North East";
			cda = "NE";
		}
		else if (windDegreeInt == 90)
		{
			cardinalDirection = "North";
			cda = "N";
		}
		else if (windDegreeInt < 180)
		{
			cardinalDirection = "North West";
			cda = "NW";
		}
		else if (windDegreeInt == 180)
		{
			cardinalDirection = "West";
			cda = "W";
		}
		else if (windDegreeInt < 270)
		{
			cardinalDirection = "South West";
			cda = "SW";
		}
		else if (windDegreeInt == 270)
		{
			cardinalDirection = "South";
			cda = "South";
		}
		else if (windDegreeInt > 270)
		{
			cardinalDirection = "South East";
			cda = "SE";
		}
		// Fix exception stuff here when it can't figure it out
	}
}
