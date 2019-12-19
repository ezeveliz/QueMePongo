package Model.retrofit.modelOpenWeather;

import java.util.ArrayList;
import lombok.Data;

@Data
public class List {

    private Integer dt;
    private Main main;
    private java.util.List<Weather> weather = new ArrayList<Weather>();
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Sys_ sys;
    private String dtTxt;

}