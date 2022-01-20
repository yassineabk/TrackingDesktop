package tracking_Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Server.SmartphoneRemote;
import Server.UserRemote;
import models.Smartphone;
import models.User;
import service.Session;

public class Main {
	
	private static SmartphoneRemote lookUpSmartphone() throws NamingException {
		Hashtable<Object, Object> config = new Hashtable<Object, Object>();
			config.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
			config.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			final Context context = new InitialContext(config);
			return (SmartphoneRemote) context.lookup("ejb:/tracking_Swing/SmartphoneSession!Server.SmartphoneRemote");
			
	}
		
	private static UserRemote lookUpUser() throws NamingException {
		Hashtable<Object, Object> config = new Hashtable<Object, Object>();
		config.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		config.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(config);
		return (UserRemote) context.lookup("ejb:/tracking_Swing/Session!Server.UserRemote");
				
	}

	public static void main(String[] args) throws NamingException {
		UserRemote remote2 = lookUpUser();
		SmartphoneRemote remote = lookUpSmartphone(); 
		User u1 = new User("Azilal","Abdelilah","abdelilah@gmail.com",new Date(), "123");
		Smartphone p1 = new Smartphone("apple","008888000");
		/*remote2.create(u1);	
		remote.create(p1, 1);
		*/
		/*for(User u : remote2.findAll().get(0)) {
			System.out.println(u.toString()
		}
		 
		
		for(Smartphone u : remote.findAll()) {
			System.out.println(u.toString());
		}*/
		//System.out.println(remote2.findById(1));
		List<User> list= new ArrayList<User>(); 
		Iterator it = remote2.findAll().iterator(); 
		while(it.hasNext()){ 
		     Object[] line = (Object[]) it.next(); 
		     User eq = new User(); 
		     eq.setId((int) line[0]);
		     eq.setNom(line[3].toString()); 
		     eq.setPrenom(line[4].toString()); 
		     eq.setEmail(line[2].toString()); 
		     eq.setTelephone(line[5].toString());
		     eq.setDateNaissance((Date) line[1]);
		     //And set all the Equip fields here 
		     //And last thing add it to the list 
		 
		     list.add(eq); 
		}
		for(User u : list) {
			System.out.println(u.toString());
		}
		//System.out.println(remote2.findAll().get(0).toString());
		
		Scanner in = new Scanner(System.in);
		in.next();
			
	}


}
