package Model.DAO;

import Model.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import Model.queMePongo.Guardarropas;
import Model.queMePongo.Usuario;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class GuardarropaDAO {
    public Guardarropas getGuardarropas(int id_guardarropa) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Guardarropas g WHERE g.id = :id_guardarropa";
        Query query = session.createQuery(hql);
        query.setParameter("id_guardarropa", id_guardarropa);


        List<Guardarropas> list = query.list();

        return list.get(0);
    }

    public void modificarGuardarropas(Guardarropas guardarropa) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        //Salvo el guardarropas
        session.save(guardarropa);

        // commiteo la transaccion
        trx.commit();

        session.close();
    }

    public int eliminarGuardarropas(Guardarropas guardarropa) throws URISyntaxException, SQLException {
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

}
