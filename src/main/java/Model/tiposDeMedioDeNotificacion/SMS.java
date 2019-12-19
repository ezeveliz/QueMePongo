package Model.tiposDeMedioDeNotificacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import Model.queMePongo.Usuario;


public class SMS implements MedioDeNotificacion {

    public static final String ACCOUNT_SID = "AC4e7dace8356c0d84812f1d86872e0035";
    public static final String AUTH_TOKEN = "071bc6e144ce62cbeed3f2f6be3eb5e4";

    public void notificarUsuario(Usuario usuario, String mensaje) {

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(usuario.getTelefono()), //to
                new com.twilio.type.PhoneNumber("+12183668329"), //from
                mensaje) // message
                .create();

        System.out.println(message.getSid());
    }

}
