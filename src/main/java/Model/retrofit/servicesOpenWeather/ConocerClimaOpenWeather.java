package Model.retrofit.servicesOpenWeather;

import java.util.List;

import Model.retrofit.modelOpenWeather.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConocerClimaOpenWeather {
//	@GET("forecast?id=3433955&APPID=887ba3007c8e8b172d3ec5ca069ed8a7&units=metric")

	@GET("forecast?id=3433955&APPID=87ba3007c8e8b172d3ec5ca069ed8a7&units=metric")
	Call<WeatherData> getWeatherData(/*@Path("latitud") int lat, @Path("longitud") int lon*/);

}
