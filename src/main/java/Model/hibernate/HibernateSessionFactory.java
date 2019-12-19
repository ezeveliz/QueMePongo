package Model.hibernate;

import java.io.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    // nombre y ubicacion del archivo de confi guracion
    public static String CONFIG_FILE = "C:\\Users\\majo\\Desktop\\QueMePongo\\src\\main\\java\\Model\\hibernate\\hibernate.cfg.xml";
    private static SessionFactory sessionFactory = null;
    private static Session session = null;

    public static Session getSession()
    {
        if( sessionFactory==null || !session.isOpen() )
        {
            File f = new File(CONFIG_FILE);
            sessionFactory = new Configuration()
                .configure(f)
                .buildSessionFactory();
            session = sessionFactory.openSession();
        }
        return session;
    }
}
