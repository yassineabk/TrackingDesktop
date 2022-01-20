package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import Server.SmartphoneLocal;
import Server.SmartphoneRemote;
import models.Smartphone;
import models.User;
@Stateless
public class SmartphoneSession implements SmartphoneLocal,SmartphoneRemote{

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean create(Smartphone smartphone, int idUser) {
		User user = em.find(User.class, idUser);
		if (user == null) return false;
		else {
			smartphone.setUser(user);
			em.persist(smartphone);
			return true;
		}
	}

	@Override
	public boolean update(Smartphone smartphone, int idUser) {
		User user = em.find(User.class, idUser);
		if (user == null) return false;
		else {
			smartphone.setUser(user);
			em.merge(smartphone);
			return true;
		}
	}


	@Override
	public boolean delete(Smartphone smartphone) {
		em.remove( smartphone);
		return true;
	}

	@Override
	public Smartphone findById(int id) {
		return em.find(Smartphone.class, id);
	}

	@Override
	public List<Smartphone> findAll(User user) {
		Query query = em.createQuery("from Smartphone s where s.user=:user");
		query.setParameter("user", user);
		return query.getResultList();
	}

	

}
