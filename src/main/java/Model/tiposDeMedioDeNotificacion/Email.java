package Model.tiposDeMedioDeNotificacion;

import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import Model.queMePongo.Usuario;

public class Email implements MedioDeNotificacion {

    static String getQMPEmail() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("QMPEMAIL") != null) {
            return System.getenv("QMPEMAIL");
        }
        return "queMePongo.notificationService@gmail.com"; //return default email if isn't set
    }

    static String getSMTPServer() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("SMTP_SERVER") != null) {
            return System.getenv("SMTP_SERVER");
        }
        return "smtp.gmail.com"; //return default smtp server if isn't set
    }

    static int getSMTPPort() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("SMTP_PORT") != null) {
            return Integer.parseInt(System.getenv("SMTP_PORT"));
        }
        return 587; //return default smtp port if isn't set
    }


    public void notificarUsuario(Usuario usuario, String mensaje)  {

        org.simplejavamail.email.Email email = EmailBuilder.startingBlank()
                .from("Que Me Pongo", getQMPEmail())
                .to(usuario.getNombre() + usuario.getApellido(), usuario.getEmail())
                //.cc("")
                .withSubject("Que Me Pongo - Notificación")
                .withPlainText(mensaje)
                .buildEmail();

        MailerBuilder
                .withSMTPServer(getSMTPServer(), getSMTPPort(), getQMPEmail(), "DDSgrupo5")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }
}
