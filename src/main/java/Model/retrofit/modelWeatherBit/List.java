package Model.retrofit.modelWeatherBit;

import lombok.Data;

@Data
public class List {
	
	private String wind_cdir;
    private int rh;
    private String pod;
    private String timestamp_utc;
    private Double pres;
    private Double solar_rad;
    private Double ozone;
    private Weather weather;
    private Double wind_gust_spd;
    private String timestamp_local;
    private Double snow_depth;
    private int clouds;
    private long ts;
    private Double wind_spd;
    private Double pop;
    private String wind_cdir_full;
    private Double slp;
    private Double dni;
    private Double dewpt;
    private Double snow;
    private Double uv;
    private Double wind_dir;
    private Double clouds_hi;
    private Double precip;
    private Double vis;
    private Double dhi;
    private Double app_temp;
    private String datetime;
    private Double temp;
    private Double ghi;
    private Double clouds_mid;
    private Double clouds_low;
}
