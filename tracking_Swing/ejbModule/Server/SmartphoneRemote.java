package Server;

import java.util.List;

import javax.ejb.Remote;

import models.Smartphone;
import models.User;

@Remote
public interface SmartphoneRemote {
	boolean create(Smartphone smartphone,int idUser);
	boolean update(Smartphone smartphone, int idUser); 
	boolean delete(Smartphone smartphone);
	Smartphone findById(int id); 
	List<Smartphone> findAll(User user); 

}
