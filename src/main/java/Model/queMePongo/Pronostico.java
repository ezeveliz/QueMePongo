package Model.queMePongo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pronostico {
    private static Pronostico instancia;
    private Pronostico(){}
    private Map<Date, Double> valores = new HashMap<>();

    public static final Double TEMP_NO_ENCONTRADA = 200.0;

    public static Pronostico obtenerInstancia(){
        if(instancia == null) {
            instancia = new Pronostico();
        }
        return instancia;
    }

    //--Verifico que exista algun pronostico
    private boolean existePronostico() {
        if(instancia == null ) {
            instancia = new Pronostico();
            return false;
        } else return valores.size() != 0;
    }

    //--Limpio los valores preexistentes de los pronosticos
    //-- TODO Borrar?
    public void limpiarValores() {
        this.valores.clear();
    }

    //--Agrego valores al hashmap
    public void addValues(Date fecha, Double temp) {
        valores.put(fecha, temp);
    }

    //Si retorna 200, no existe el pronostico para esa fecha
    public Double obtenerPronosticoPara(Date fechaAProbar) {
        if(existePronostico()) {
            Date fechaProxima = null;
            long diffInMillies = 0;
            int iter = 0;
            for(Date fechaDePronostico : valores.keySet()){
                if( iter == 0 ) {
                    diffInMillies = this.diferenciaDeTiempoEnMillis(fechaAProbar, fechaDePronostico);
                    fechaProxima = fechaDePronostico;
                    iter = 1;
                } else {
                    long diferencia = this.diferenciaDeTiempoEnMillis(fechaAProbar, fechaDePronostico);
                    if(diferencia < diffInMillies) {
                        diffInMillies = diferencia;
                        fechaProxima = fechaDePronostico;
                    }
                }
            }

            long diffInHours = diffInMillies / (60*60*100);

            if(diffInHours < 4) {
                return valores.get(fechaProxima);
            }
        }
        //this.limpiarValores();
        return TEMP_NO_ENCONTRADA;
    }

    private long diferenciaDeTiempoEnMillis(Date fechaAProbar, Date fechaDePronostico) {
        return Math.abs(fechaAProbar.getTime() - fechaDePronostico.getTime());
    }
}
