package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Server.UserRemote;
import models.User;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;

public class UserFrame {

	private JFrame frame;
	private JTextField txtNom;
	private JTextField txtPrenom;
	private JTextField txtEmail;
	private JTextField txtTelephone;
	private JDateChooser dateChooser;
	
	private DefaultTableModel model;
    private int id = -1;
    Object[] column = {"ID","Nom","Prenom","Email","Telephone","Date de naissance"};
    final Object[] row = new Object[5];
    
    
    private static UserRemote remote;
    private JTable table;
    
    private static UserRemote lookUpUser() throws NamingException {
		Hashtable<Object, Object> config = new Hashtable<Object, Object>();
		config.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		config.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(config);
		return (UserRemote) context.lookup("ejb:/tracking_Swing/Session!Server.UserRemote");
				
	}
    
    
    
    void load() throws RemoteException {
        model.setRowCount(0);
        /*List<User> list= new ArrayList<User>(); 
		Iterator it = remote.findAll().iterator(); 
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
		}*/
        //for(User u : remote.findAll()){
        for(User u : remote.findAll()){	
            model.addRow(new Object[]{
                u.getId(),
                u.getNom(),
                u.getPrenom(),
                u.getEmail(),
                u.getTelephone(),
                u.getDateNaissance()
            });
        }
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws NamingException{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFrame window = new UserFrame();
					window.frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws RemoteException 
	 * @throws NamingException 
	 */
	public UserFrame() throws RemoteException, NamingException {
		initialize();
		remote = lookUpUser();
        load();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 669, 505);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		
		JButton myphones = new JButton("Smartphones");
		myphones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SmartphoneFrame smartphoneFrame;
				try {
					smartphoneFrame = new SmartphoneFrame();
					smartphoneFrame.getFrame().setVisible(true);
					smartphoneFrame.idUser = id;
					smartphoneFrame.load();
					getFrame().dispose();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (NamingException e1) {
					e1.printStackTrace();
				}
			}
		});
		myphones.setBackground(Color.MAGENTA);
		myphones.setFont(new Font("Raleway SemiBold", Font.BOLD, 12));
		myphones.setBounds(470, 322, 124, 23);
		panel.add(myphones);
		
		JButton btnAdd = new JButton("Ajouter");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remote.create(new User(txtNom.getText(),txtPrenom.getText(),txtEmail.getText(),dateChooser.getDate(),txtTelephone.getText()));
				try {
					load();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setFont(new Font("Antonio", Font.BOLD, 13));
		btnAdd.setBounds(470, 36, 119, 23);
		panel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Modifier");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNom.getText().isEmpty() && txtPrenom.getText().isEmpty()&& 
						txtEmail.getText().isEmpty()&&txtTelephone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(panel, "Champs vides !!");
				}else {
					remote.update(new User(Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString()),
							txtNom.getText(),txtPrenom.getText(),txtEmail.getText(),
							dateChooser.getDate(),txtTelephone.getText()));
					try {
						load();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setBackground(Color.CYAN);
		btnUpdate.setFont(new Font("Antonio", Font.BOLD, 13));
		btnUpdate.setBounds(470, 83, 119, 23);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Supprimer");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(remote.findById(id).toString());
				//System.out.println(id);
				remote.delete(remote.findById(id));
				
				try {
					load();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Antonio", Font.BOLD, 13));
		btnDelete.setBackground(Color.RED);
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(470, 126, 119, 23);
		panel.add(btnDelete);
		
		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Bahnschrift", Font.BOLD, 13));
		lblNewLabel.setBounds(22, 36, 89, 14);
		panel.add(lblNewLabel);
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Bahnschrift", Font.BOLD, 13));
		lblEmail.setBounds(22, 86, 89, 14);
		panel.add(lblEmail);
		
		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setForeground(Color.WHITE);
		lblPrenom.setFont(new Font("Bahnschrift", Font.BOLD, 13));
		lblPrenom.setBounds(22, 61, 89, 14);
		panel.add(lblPrenom);
		
		JLabel lblTelephone = new JLabel("Telephone :");
		lblTelephone.setForeground(Color.WHITE);
		lblTelephone.setFont(new Font("Bahnschrift", Font.BOLD, 13));
		lblTelephone.setBounds(22, 111, 89, 14);
		panel.add(lblTelephone);
		
		JLabel lblEmail_1_1 = new JLabel("Date de Naissance :");
		lblEmail_1_1.setForeground(Color.WHITE);
		lblEmail_1_1.setFont(new Font("Bahnschrift", Font.BOLD, 13));
		lblEmail_1_1.setBounds(22, 136, 124, 14);
		panel.add(lblEmail_1_1);
		
		txtNom = new JTextField();
		txtNom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNom.setBounds(154, 32, 198, 20);
		panel.add(txtNom);
		txtNom.setColumns(10);
		
		txtPrenom = new JTextField();
		txtPrenom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPrenom.setColumns(10);
		txtPrenom.setBounds(154, 57, 198, 20);
		panel.add(txtPrenom);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEmail.setColumns(10);
		txtEmail.setBounds(154, 82, 198, 20);
		panel.add(txtEmail);
		
		txtTelephone = new JTextField();
		txtTelephone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTelephone.setColumns(10);
		txtTelephone.setBounds(154, 107, 198, 20);
		panel.add(txtTelephone);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 163, 546, 153);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				id = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 0).toString());
				System.out.println(id);
				txtNom.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
				txtPrenom.setText(model.getValueAt(table.getSelectedRow(), 2).toString());
				txtEmail.setText(model.getValueAt(table.getSelectedRow(), 3).toString());
				txtTelephone.setText(model.getValueAt(table.getSelectedRow(), 4).toString());
				dateChooser.setDate((Date) model.getValueAt(table.getSelectedRow(), 5));
			}
		});
		model = new DefaultTableModel();
		Object[] column = {"ID","Nom","Prenom","Email","Telephone","Date de naissance"};
	    final Object[] row = new Object[0];
	    model.setColumnIdentifiers(column);
	    table.setModel(model);
		scrollPane.setViewportView(table);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(164, 136, 176, 19);
		panel.add(dateChooser);
	}
	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
