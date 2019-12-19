package Model.retrofit.modelOpenWeather;

import lombok.Data;

@Data
public class City {

    private Integer id;
    private String name;
    private Coord coord;
    private String country;
    private Integer population;
    private Sys sys;

}
