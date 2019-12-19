package Model.retrofit.modelWeatherBit;

import java.util.ArrayList;
import lombok.Data;
import Model.retrofit.modelWeatherBit.List;

@Data
public class WeatherBitData {

    private java.util.List<List> data = new ArrayList<>();
    private String city_name;
    private String lon;
    private String timezone;
    private String lat;
    private String country_code;
    private String state_code;

}