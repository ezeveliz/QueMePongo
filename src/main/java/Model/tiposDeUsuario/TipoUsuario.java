package Model.tiposDeUsuario;

import Model.queMePongo.Guardarropas;
import Model.queMePongo.Prenda;

public interface TipoUsuario {

	int agregarPrenda(Prenda prenda, Guardarropas guardarropas);
	//TODO: estos tres metodos deberian retornar algo para confirmar
	void moverPrenda(Prenda prenda, Guardarropas guardarropasOrigen, Guardarropas guardarropasDestino);
}
