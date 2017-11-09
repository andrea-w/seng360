package com.mkyong.rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.security.*;
import javax.crypto.*;
import java.security.*;
import com.mkyong.rmiinterface.RMIInterface;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface{
	private static final long serialVersionUID = 1L;	
	private static RMIInterface look_up;
	
	private static String feature;

	protected ServerOperation() throws RemoteException {
		super();
	}

	@Override
	public String helloTo(String name) throws RemoteException{
		System.err.println(name + " is trying to contact!");
		return "Server says hello to " + name;
	}
	@Override
	public String getFeature() {
		return feature;
	}
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		try {
			Naming.rebind("//localhost/MyServer", new ServerOperation());            
            System.err.println("Server ready");
            
        } catch (Exception e) {
        	System.err.println("Server exception: " + e.toString());
          e.printStackTrace();
        }

        featuresSelection();
        
	}

	public static void featuresSelection() throws MalformedURLException, RemoteException, NotBoundException {
		final JPanel panel = new JPanel();
		final JRadioButton buttonC = new JRadioButton("Confidentiality");
		final JRadioButton buttonI = new JRadioButton("Integrity");
		final JRadioButton buttonA = new JRadioButton("Authentication");
		final JLabel label = new JLabel("Select your desired security features:");

		panel.add(label);
		panel.add(buttonC);
		panel.add(buttonI);
		panel.add(buttonA);

		//feature = "Confidentiality";
		confidentiality();
		JOptionPane.showMessageDialog(null, panel);

		String txt = "";
		if (buttonC.isSelected()) {
			txt += "Confidentiality";
			// confidentiality();
		}
		if (buttonI.isSelected()) {
			txt += "Integrity";	
		}
		if (buttonA.isSelected()) {
			txt += "Authentication";	
		}

		feature = txt;
		System.out.println("Server selected features: " + txt);	
	}

	public static void confidentiality() throws MalformedURLException, RemoteException, NotBoundException {
		look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
		
	}
}
