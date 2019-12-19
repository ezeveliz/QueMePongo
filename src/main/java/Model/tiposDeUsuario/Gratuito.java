package Model.tiposDeUsuario;

import Model.queMePongo.Guardarropas;
import Model.queMePongo.Prenda;
import Model.queMePongo.Usuario;

public class Gratuito implements TipoUsuario {

	private int limiteDePrendas = 3;
	
	public int agregarPrenda(Prenda prenda, Guardarropas guardarropas) {
		int resultado = 1;

		if(guardarropas.cantidadDePrendas() < limiteDePrendas) {
			guardarropas.agregarPrenda(prenda);
		}
		else{
			resultado = -1;
		}

		return resultado;
	}
	
    public void moverPrenda(Prenda prenda, Guardarropas guardarropasOrigen, Guardarropas guardarropasDestino) {
    	if(guardarropasDestino.cantidadDePrendas() < limiteDePrendas) {
    		guardarropasOrigen.removerPrenda(prenda);
    		guardarropasDestino.agregarPrenda(prenda);

    	}
    }

	}
