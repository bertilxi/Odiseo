package app.datos.servicios.implementacion;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.clases.FiltroPropietario;
import app.datos.clases.FiltroVendedor;
import app.datos.entidades.Propietario;
import app.datos.entidades.Vendedor;
import app.datos.servicios.PropietarioService;
import app.datos.servicios.VendedorService;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

@Repository
public class PropietarioServiceImpl implements PropietarioService, VendedorService {

	private SessionFactory sessionFactory;

	@Autowired
	public PropietarioServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void guardarVendedor(Vendedor vendedor) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(vendedor);
		} catch(Exception e){
			throw new SaveUpdateException();
		}

	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void modificarVendedor(Vendedor vendedor) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(vendedor);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Vendedor obtenerVendedor(FiltroVendedor filtro) throws PersistenciaException {
		Vendedor vendedor = null;
		Session session = getSessionFactory().getCurrentSession();
		try{
			vendedor = (Vendedor) session.getNamedQuery("obtenerVendedor").setParameter("tipoDocumento", filtro.getTipoDocumento()).setParameter("documento", filtro.getDocumento()).uniqueResult();
		} catch(NoResultException e){
			return null;
		} catch(Exception e){
			//hacer algo
		}
		return vendedor;
	}

	@Override
	public void guardarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarPropietario(Propietario propietario) throws PersistenciaException {

	}

}