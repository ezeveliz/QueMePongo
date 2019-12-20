package Model.tiposDeMedioDeNotificacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import Model.queMePongo.Usuario;


public class SMS implements MedioDeNotificacion {


    static String getAccountSID() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("ACCOUNT_SID") != null) {
            return System.getenv("ACCOUNT_SID");
        }
        return "AC4e7dace8356c0d84812f1d86872e0035"; //return default account_sid if isn't set
    }
//    public static final String ACCOUNT_SID = "AC4e7dace8356c0d84812f1d86872e0035";

    static String getAuthToken() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("AUTH_TOKEN") != null) {
            return System.getenv("AUTH_TOKEN");
        }
        return "071bc6e144ce62cbeed3f2f6be3eb5e4"; //return default authorization token if isn't set
    }
//    public static final String AUTH_TOKEN = "071bc6e144ce62cbeed3f2f6be3eb5e4";


    static String getSmsNumber() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("SMSNUMBER") != null) {
            return System.getenv("SMSNUMBER");
        }
        return "+12183668329"; //return default sms number if isn't set
    }


    public void notificarUsuario(Usuario usuario, String mensaje) {

    Twilio.init(getAccountSID(), getAuthToken());
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(usuario.getTelefono()), //to
                new com.twilio.type.PhoneNumber(getSmsNumber()), //from
                mensaje) // message
                .create();

        System.out.println(message.getSid());
    }

}
