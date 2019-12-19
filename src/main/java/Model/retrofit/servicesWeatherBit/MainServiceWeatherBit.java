package Model.retrofit.servicesWeatherBit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.queMePongo.Pronostico;
import Model.queMePongo.Sugerencias;
import Model.retrofit.modelWeatherBit.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static Model.queMePongo.EstadoDeSugerencia.FALLIDO;


public class MainServiceWeatherBit {

	//--Maxima cantidad de dias soportado por el pronostico de la API
	private int maxCantDias = 2;

	public int getMaxCantDias() {
		return maxCantDias;
	}

	public void getWeatherBitData(final int cantDiasFaltantes, final int diferenciaDeHoras/*int lat, int lon*/, Sugerencias sugerencia) {
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.weatherbit.io/v2.0/forecast/")
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();
		
		ConocerClimaWeatherBit conocerClimaWeatherBit = retrofit.create(ConocerClimaWeatherBit.class);
		
		Call<WeatherBitData> llamadoClimasWeatherBit = conocerClimaWeatherBit.getWeatherBitData(/*lat, lon*/);

		llamadoClimasWeatherBit.enqueue(new Callback<WeatherBitData>() {

			public void onResponse(Call<WeatherBitData> call, Response<WeatherBitData> response) {
				if (!response.isSuccessful()){ //no es exitoso el codigo de respuesta
					System.out.println("C�digo de error:" + response.code()); //salta mensaje de error
					sugerencia.setEstado(FALLIDO);
				}else { //nos devuelve el json correspondiente
					WeatherBitData clima = response.body();
					List listaDePronosticos = clima.getData();
					
					Pronostico pronostico = sugerencia.getPronostico();

					listaDePronosticos.forEach(p -> {
						Model.retrofit.modelWeatherBit.List data = (Model.retrofit.modelWeatherBit.List) p;
						String fechaString = data.getTimestamp_local();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						Date fecha;
						try {
							fecha = formatter.parse(fechaString);
							Double temperatura = data.getTemp();
							pronostico.addValues(fecha, temperatura);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							sugerencia.setEstado(FALLIDO);
						}
					});
					
					int elementoFinal = (int) Math.floor(cantDiasFaltantes * 24 + diferenciaDeHoras);

					Model.retrofit.modelWeatherBit.List WBList = (Model.retrofit.modelWeatherBit.List) listaDePronosticos.get(elementoFinal);
					Double temperatura = WBList.getTemp();
					
					sugerencia.setTemperatura(temperatura);
				}
			}

			public void onFailure(Call<WeatherBitData> call, Throwable t) { //cuando falla la conexion con la api
				System.out.println(t.getMessage()); //salta mensaje de error
				sugerencia.setEstado(FALLIDO);
			}
		});
	}
}
