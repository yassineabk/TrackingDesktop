package Server;

import java.util.List;

import javax.ejb.Local;

import models.Smartphone;
import models.User;
@Local
public interface SmartphoneLocal {
	boolean create(Smartphone smartphone,int idUser);
	boolean update(Smartphone smartphone, int idUser); 
	boolean delete(Smartphone smartphone);
	Smartphone findById(int id); 
	List<Smartphone> findAll(User user); 

}
