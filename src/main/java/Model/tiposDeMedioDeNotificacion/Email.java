package Model.tiposDeMedioDeNotificacion;

import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import Model.queMePongo.Usuario;

public class Email implements MedioDeNotificacion {

    public void notificarUsuario(Usuario usuario, String mensaje)  {

        org.simplejavamail.email.Email email = EmailBuilder.startingBlank()
                .from("Que Me Pongo", "queMePongo.notificationService@gmail.com")
                .to(usuario.getNombre() + usuario.getApellido(), usuario.getEmail())
                //.cc("")
                .withSubject("Que Me Pongo - Notificación")
                .withPlainText(mensaje)
                .buildEmail();

        MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "queMePongo.notificationService@gmail.com", "DDSgrupo5")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }
}
