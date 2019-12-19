package Model.retrofit.modelOpenWeather;

import lombok.Data;

@Data
public class Main {

    private Double temp;
    private Double tempMin;
    private Double tempMax;
    private Double pressure;
    private Double seaLevel;
    private Double grndLevel;
    private Integer humidity;
    private Integer tempKf;

}