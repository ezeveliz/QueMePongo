package Model.DAO;

import Model.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import Model.queMePongo.Evento;
import Model.queMePongo.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class EventoDAO {

    public Evento getEvento(LocalDateTime horario, int id_usuario){
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Evento e WHERE e.id = :id_usuario and e.horario = :horario";
        Query query = session.createQuery(hql);
        query.setParameter("id_usuario", id_usuario);
        query.setParameter("horario", horario);


        List<Evento> list = query.list();

        return list.get(0);
    }

    public Evento getEvento(int id_evento){
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Evento e WHERE e.id = :id_evento";
        Query query = session.createQuery(hql);
        query.setParameter("id_evento", id_evento);


        List<Evento> list = query.list();

        return list.get(0);
    }
    public List<Evento> getEvento(Usuario usuario){
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Evento e WHERE e.usuario.id = :id_usuario";
        Query query = session.createQuery(hql);
        query.setParameter("id_usuario", usuario.getId());


        List<Evento> list = query.list();

        return list;
    }

    public void modificarEvento(Evento eventoModificado){
        Session session = HibernateSessionFactory.getSession();
        Transaction trx = session.beginTransaction();

        // grabo las modifi caciones
        session.saveOrUpdate(eventoModificado);

        // commiteo
        trx.commit();
        session.close();
    }

    public int eliminarEvento(Evento eventoAEliminar){
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
}
