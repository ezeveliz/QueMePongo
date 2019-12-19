package Model.retrofit.servicesWeatherBit;

import java.util.List;

import Model.retrofit.modelWeatherBit.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConocerClimaWeatherBit {

	@GET("hourly?city=Buenos%20Aires,AR&key=6c46202dee6e4f4583ea8e62573679ec&hours=48") 
	Call<WeatherBitData> getWeatherBitData(/*@Path("latitud") int lat, @Path("longitud") int lon*/);

}
