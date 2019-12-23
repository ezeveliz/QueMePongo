package Utils;

import Model.queMePongo.Prenda;

import java.util.List;

public class FilaPrendasDTO {
    private Prenda primero = null;
    private Prenda segundo = null;
    private Boolean tieneSegundo = false;
    private Prenda tercero = null;
    private Boolean tieneTercero = false;
    private Prenda cuarto = null;
    private Boolean tieneCuarto = false;

    public Prenda getPrimero() {
        return primero;
    }

    public Prenda getSegundo() {
        return segundo;
    }

    public Prenda getTercero() {
        return tercero;
    }

    public Prenda getCuarto() {
        return cuarto;
    }

    public Boolean getTieneSegundo() {
        return tieneSegundo;
    }

    public Boolean getTieneTercero() {
        return tieneTercero;
    }

    public Boolean getTieneCuarto() {
        return tieneCuarto;
    }

    public FilaPrendasDTO(List<Prenda> fila) {
        primero = fila.get(0);
        if (fila.size() > 1) {
            segundo = fila.get(1);
            tieneSegundo = true;
            if (fila.size() > 2) {
                tercero = fila.get(2);
                tieneTercero = true;
                if (fila.size() > 3) {
                    cuarto = fila.get(3);
                    tieneCuarto = true;
                }
            }
        }
    }
}
