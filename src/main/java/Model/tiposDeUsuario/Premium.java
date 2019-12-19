package Model.tiposDeUsuario;

import Model.queMePongo.Guardarropas;
import Model.queMePongo.Prenda;
import Model.queMePongo.Usuario;

public class Premium implements TipoUsuario {
	
	public int agregarPrenda(Prenda prenda, Guardarropas guardarropas) {
			guardarropas.agregarPrenda(prenda);
			return 1;
	}
	
    public void moverPrenda(Prenda prenda, Guardarropas guardarropasOrigen, Guardarropas guardarropasDestino) {
    	guardarropasOrigen.removerPrenda(prenda);
    	guardarropasDestino.agregarPrenda(prenda);
    }



}
