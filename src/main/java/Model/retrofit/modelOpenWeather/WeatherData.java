package Model.retrofit.modelOpenWeather;

import java.util.ArrayList;
import lombok.Data;

@Data
public class WeatherData {

    private City city;
    private String cod;
    private String message;
    private Integer cnt;
    private java.util.List<List> list = new ArrayList<List>();

}