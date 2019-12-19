package Model.retrofit.servicesOpenWeather;

import Model.queMePongo.Pronostico;
import Model.queMePongo.Sugerencias;
import Model.retrofit.modelOpenWeather.WeatherData;
import Model.retrofit.servicesWeatherBit.MainServiceWeatherBit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Date;
import java.util.List;

import static Model.queMePongo.EstadoDeSugerencia.FALLIDO;

public class MainServiceOpenWeather {

	//--Maxima cantidad de dias soportado por el pronostico de la API
	private int maxCantDias = 5;

	public int getMaxCantDias() {
		return maxCantDias;
	}

	public void getWeatherData(final int cantDiasFaltantes, final int diferenciaDeHoras/*int lat, int lon*/, final Sugerencias sugerencia) {
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://api.openweathermap.org/data/2.5/")
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();
		
		ConocerClimaOpenWeather conocerClimaOpenWeather = retrofit.create(ConocerClimaOpenWeather.class);
		
		Call<WeatherData> llamadoClimasOpenWeather = conocerClimaOpenWeather.getWeatherData(/*lat, lon*/);

		llamadoClimasOpenWeather.enqueue(new Callback<WeatherData>() {

			public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {

				if (!response.isSuccessful()){ //no es exitoso el codigo de respuesta
					MainServiceWeatherBit mainServiceWeatherBit = new MainServiceWeatherBit(); //llama a la otra api (weatherbit)
					if(cantDiasFaltantes <= mainServiceWeatherBit.getMaxCantDias()) {
						mainServiceWeatherBit.getWeatherBitData(cantDiasFaltantes, diferenciaDeHoras, sugerencia);
					} else {
						sugerencia.setEstado(FALLIDO);
					}
				}else { //nos devuelve el json correspondiente
					WeatherData clima = response.body();
					List listaDePronosticos = clima.getList();

					Pronostico pronostico = sugerencia.getPronostico();

					listaDePronosticos.forEach(p -> {
						Model.retrofit.modelOpenWeather.List prntc = (Model.retrofit.modelOpenWeather.List) p;
						Date fecha = new java.util.Date((long)prntc.getDt()*1000);
						Double temperatura = prntc.getMain().getTemp();
						pronostico.addValues(fecha, temperatura);
					});

					int elementoFinal = (int) Math.floor(cantDiasFaltantes * 8 + diferenciaDeHoras / 3);

					Model.retrofit.modelOpenWeather.List OWList = (Model.retrofit.modelOpenWeather.List) listaDePronosticos.get(elementoFinal);
					OWList.getDt();
					Model.retrofit.modelOpenWeather.Main main = OWList.getMain();
					Double temperatura = main.getTemp();

					sugerencia.setTemperatura(temperatura);
					sugerencia.setTemperaturaEvento(temperatura);
				}
			}

			public void onFailure(Call<WeatherData> call, Throwable t) { //cuando falla la conexion con la api

				MainServiceWeatherBit mainServiceWeatherBit = new MainServiceWeatherBit();
				if(cantDiasFaltantes <= mainServiceWeatherBit.getMaxCantDias()) {
					mainServiceWeatherBit.getWeatherBitData(cantDiasFaltantes, diferenciaDeHoras, sugerencia);
				} else {
					sugerencia.setEstado(FALLIDO);
				}
			}
		});
	}
		
}
