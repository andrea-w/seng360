package com.mkyong.rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mkyong.rmiinterface.RMIInterface;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface{
	private static final long serialVersionUID = 1L;

	protected ServerOperation() throws RemoteException {
		super();
	}

	@Override
	public String helloTo(String name) throws RemoteException{
		System.err.println(name + " is trying to contact!");
		return "Server says hello to " + name;
	}
	
	public static void main(String[] args){
		try {
			Naming.rebind("//localhost/MyServer", new ServerOperation());            
            System.err.println("Server ready");
            
        } catch (Exception e) {
        	System.err.println("Server exception: " + e.toString());
          e.printStackTrace();
        }

        featuresSelection();
        
	}

	public static void featuresSelection() {
		final JPanel panel = new JPanel();
		final JRadioButton buttonC = new JRadioButton("Confidentiality");
		final JRadioButton buttonI = new JRadioButton("Integrity");
		final JRadioButton buttonA = new JRadioButton("Authentication");
		final JLabel label = new JLabel("Select your desired security features:");

		panel.add(label);
		panel.add(buttonC);
		panel.add(buttonI);
		panel.add(buttonA);

		JOptionPane.showMessageDialog(null, panel);

		String txt = "";
		if (buttonC.isSelected()) {
			txt += "Confidentiality ";
		}
		if (buttonI.isSelected()) {
			txt += "Integrity ";	
		}
		if (buttonA.isSelected()) {
			txt += "Authentication ";	
		}

		System.out.println("Server selected features: " + txt);	
	}
}
