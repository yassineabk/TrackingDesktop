package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import Server.UserLocal;
import Server.UserRemote;
import models.User;
@Stateless
public class Session implements UserLocal,UserRemote{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean create(User user) {
		em.persist(user);
		return true;
	}

	@Override
	public boolean update(User user) {
		em.merge(user);
		return true;
	}

	@Override
	public boolean delete(User user) {
		em.remove(em.contains(user)?user:em.merge(user));
		return true;
	}
	
	

	@Override
	public User findById(int id) {
		/*Query query = em.createNativeQuery("SELECT * FROM User u WHERE idUser=?",User.class);
		query.setParameter(1, id);
		return (User) query.getSingleResult();*/
		
		return em.find(User.class, id);
	}

	public List<User> findAll(){
		Query query = em.createQuery("from User");
	    return  query.getResultList();
	}

}
