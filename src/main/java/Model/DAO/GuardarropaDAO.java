package Model.DAO;

import Model.hibernate.HibernateSessionFactory;
import Model.queMePongo.Atuendo;
import Model.queMePongo.Guardarropas;
import Model.queMePongo.Prenda;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class GuardarropaDAO {
    public static Guardarropas getGuardarropas(int id_guardarropa) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Guardarropas g WHERE g.id = :id_guardarropa";
        Query query = session.createQuery(hql);
        query.setParameter("id_guardarropa", id_guardarropa);


        List<Guardarropas> list = query.list();

        return list.get(0);

    }

    public static Atuendo getAtuendo(int id_atuendo) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Atuendo a WHERE a.id = :id_atuendo";
        Query query = session.createQuery(hql);
        query.setParameter("id_atuendo", id_atuendo);


        List<Atuendo> list = query.list();

        return list.get(0);

    }

    public static void guardarGuardarropas(Guardarropas guardarropa) throws URISyntaxException, SQLException {

        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        //Salvo el usuarioNuevo
        session.save(guardarropa);

        // commiteo la transaccion
        trx.commit();

        session.close();

    }

    public static void  modificarGuardarropas(Guardarropas guardarropa) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        //Salvo el guardarropas
        session.save(guardarropa);

        // commiteo la transaccion
        trx.commit();

        session.close();
    }

    public static int eliminarGuardarropas(Guardarropas guardarropa) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        String qryString = "delete from Guardarropas g where g.id = :id_guardarropa";
        Query query = session.createQuery(qryString);
        query.setParameter("id_guardarropa", guardarropa.getId());

        int count = query.executeUpdate();

        trx.commit();

        session.close();


        return count;
    }

    public static Prenda getPrenda(int id_prenda) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Prenda a WHERE a.id = :id_prenda";
        Query query = session.createQuery(hql);
        query.setParameter("id_prenda", id_prenda);


        List<Prenda> list = query.list();

        return list.get(0);

    }

}
