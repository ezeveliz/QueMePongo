package Model.DAO;

import Model.hibernate.HibernateSessionFactory;
import Model.queMePongo.Evento;
import Model.queMePongo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class EventoDAO {

    public static Evento getEvento(LocalDateTime horario, int id_usuario) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Evento e WHERE e.id = :id_usuario and e.horario = :horario";
        Query query = session.createQuery(hql);
        query.setParameter("id_usuario", id_usuario);
        query.setParameter("horario", horario);


        List<Evento> list = query.list();

        return list.get(0);
    }

    public static Evento getEvento(int id_evento) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Evento e WHERE e.id = :id_evento";
        Query query = session.createQuery(hql);
        query.setParameter("id_evento", id_evento);


        List<Evento> list = query.list();

        return list.get(0);
    }
    public static List<Evento> getEvento(Usuario usuario) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Evento e WHERE e.usuario.id = :id_usuario";
        Query query = session.createQuery(hql);
        query.setParameter("id_usuario", usuario.getId());


        List<Evento> list = query.list();

        return list;
    }

    public static void modificarEvento(Evento eventoModificado) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        Transaction trx = session.beginTransaction();

        // grabo las modifi caciones
        session.saveOrUpdate(eventoModificado);

        // commiteo
        trx.commit();
        session.close();
    }

    public static int eliminarEvento(Evento eventoAEliminar) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        String qryString = "delete from Evento e where e.id = :id_evento";
        Query query = session.createQuery(qryString);
        query.setParameter("id_evento", eventoAEliminar.getId());

        int count = query.executeUpdate();

        trx.commit();

        session.close();


        return count;
    }

    public static void agregarEvento(Evento evento) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        //Salvo el usuarioNuevo
        session.save(evento);

        // commiteo la transaccion
        trx.commit();

        session.close();
    }
}
