package Model.DAO;


import Model.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import Model.queMePongo.Usuario;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO {

    public Usuario getUsuario(String usuarioIng) throws URISyntaxException, SQLException {

        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Usuario u WHERE u.usuario = :usuarioIng";
        Query query = session.createQuery(hql);
        query.setParameter("usuarioIng", usuarioIng);


        List<Usuario> list = query.list();

        return list.isEmpty()? null: list.get(0);
    }

    public List<Usuario> serchUsuarioNombre(String parametro) throws URISyntaxException, SQLException {

        Session session = HibernateSessionFactory.getSession();
        String hql = "FROM Usuario u WHERE u.usuario like :usuarioIng";
        Query query = session.createQuery(hql);
        query.setParameter("usuarioIng", parametro + '%');

        List<Usuario> list = query.list();

        return list;

    }

    public void registrarUsuario(Usuario usuarioNuevo) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        // inicio una transaccion
        Transaction trx = session.beginTransaction();

        //Salvo el usuarioNuevo
        session.save(usuarioNuevo);

        // commiteo la transaccion
        trx.commit();

        session.close();
    }

    public void modificarUsuario(Usuario usuarioModificado) throws URISyntaxException, SQLException {
        Session session = HibernateSessionFactory.getSession();
        Transaction trx = session.beginTransaction();

        // grabo las modifi caciones
        session.merge(usuarioModificado);

        // commiteo
        trx.commit();
        session.close();
    }
}
