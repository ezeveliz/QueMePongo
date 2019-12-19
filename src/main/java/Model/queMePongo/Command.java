package Model.queMePongo;

import java.util.HashMap;
import java.util.List;

public interface Command {
    // este seria el metodo execute en realidad, solo que este nombre me parece mas apropiado
    HashMap<Guardarropas, List<Atuendo>> obtenerSugerencias(List<Guardarropas> guardarropas, PreferenciasDTO preferencias);
}
