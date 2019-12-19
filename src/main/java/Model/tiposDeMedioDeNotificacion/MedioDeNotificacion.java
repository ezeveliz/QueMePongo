package Model.tiposDeMedioDeNotificacion;

import Model.queMePongo.Usuario;

public interface MedioDeNotificacion {

    public void notificarUsuario(Usuario usuario, String mensaje);

}
