package com.example.scottgaydos.weatherapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import net.aksingh.java.api.owm.CurrentWeatherData;
import net.aksingh.java.api.owm.OpenWeatherMap;
import org.json.JSONException;

import java.io.IOException;

public class StartActivity extends Activity
{
	String city = null;

	EditText info_editText = null;
	TextView city_textView = null;
	TextView coord_textView = null;
	TextView weather_textView = null;
	TextView wind_textView = null;

	WeatherData wd = new WeatherData();

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		info_editText = (EditText) findViewById(R.id.info_editText);
		city_textView = (TextView) findViewById(R.id.city_textView);
		coord_textView = (TextView) findViewById(R.id.coord_textView);
		weather_textView = (TextView) findViewById(R.id.weather_textView);
		wind_textView = (TextView) findViewById(R.id.wind_textView);

		info_editText.setInputType(EditorInfo.IME_ACTION_DONE);
		//info_editText.setImeActionLabel("Find", KeyEvent.KEYCODE_ENTER);

		info_editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE)
				{
					if(!((city = info_editText.getText().toString()).equals(null)))
					{
						new SendCity().execute();
					}
				}

				return false;
			}
		});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
		{
			// Launch new intent here to edit metric
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private class SendCity extends AsyncTask
	{
		@Override
		protected Object doInBackground(Object... arg)
		{
			OpenWeatherMap owm = new OpenWeatherMap("");

			try
			{
				CurrentWeatherData cwd = owm.currentWeatherByCityName(city);
				if(cwd.hasResponseCode() && cwd.getResponseCode() == 200)
				{
					if(cwd.hasCityName())
					{
						wd.cityName = cwd.getCityName();
						wd.latitude = (double)Math.round(cwd.getCoordinates_Object().getLatitude() * 100) / 100;
						wd.longitude = (double)Math.round(cwd.getCoordinates_Object().getLongitude() * 100) / 100;
					}

					if(cwd.getMainData_Object().hasMaxTemperature() && cwd.getMainData_Object().hasMinTemperature())
					{
						//maxTemp = cwd.getMainData_Object().getMaxTemperature();
						wd.maxTemp = (double)Math.round(cwd.getMainData_Object().getMaxTemperature() * 100) / 100;
						wd.maxTempMetric = (double)Math.round((wd.maxTemp - 32) * ((double)5/9) * 100) / 100;

						wd.minTemp = (double)Math.round(cwd.getMainData_Object().getMinTemperature() * 100) / 100;
						wd.minTempMetric = (double)Math.round((wd.minTemp - 32) * ((double)5/9) * 100) / 100;

						wd.windSpeed = (double)Math.round(cwd.getWind_Object().getWindSpeed() * 100) / 100;
						wd.windSpeedMetric = (double)Math.round(1.60934 * wd.windSpeed * 100) / 100;

						wd.windDegree = (double)Math.round(cwd.getWind_Object().getWindDegree() * 100) / 100;
						wd.setCardinalDirection();
					}
				}
			}
			catch(JSONException e)
			{

			}
			catch(IOException e)
			{

			}
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					city_textView.setText(wd.cityName);
					coord_textView.setText("Coordinates: " + wd.latitude + ", " + wd.longitude);
					weather_textView.setText("Temperature: " + wd.maxTempMetric);
					//weather_textView.setText("Max Temp: " + wd.maxTempMetric + "C Min Temp: " + wd.minTempMetric + "C");
					wind_textView.setText("Wind: " + wd.windSpeedMetric + "km/h " + wd.cda);
				}
			});


			return null;
		}
	}
}
