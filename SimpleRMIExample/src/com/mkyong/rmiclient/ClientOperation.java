package com.mkyong.rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

import java.security.*;
import javax.crypto.*;
import java.security.*;

import com.mkyong.rmiinterface.RMIInterface;

public class ClientOperation {
	private static RMIInterface look_up;

	private static KeyPair pair;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
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


		//confidentiality();
		
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

		System.out.println("Client selected features: " + txt);

		
		try {
			String test = look_up.getFeature();	
			if (!test.equals(txt)) {
			JOptionPane.showMessageDialog(null, "Failed to connect. Conflicting security features.");
			System.exit(0);
		}
		else {
			while(true) {
				if (txt.contains("Confidentiality")) {
					confidentiality();	
				}
			}
		}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		//JOptionPane.showMessageDialog(null, test);


		
		

	}

	public static void generateKeyPair() {

		try {
			// create key-pair generator object
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
	
			// initialize the generator
			// specify keys of keysize 2048
			keyGen.initialize(2048);
	
			// generate the key pair
			pair = keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			System.err.print(e);
		}

	}

	public static void confidentiality() throws MalformedURLException, RemoteException, NotBoundException {
		String txt = JOptionPane.showInputDialog("Type your message");
		
		String response = look_up.helloTo(txt);

		// KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		// keyGen.init(128);
		// SecretKey secretKey = keyGen.generateKey();

		// JOptionPane.showMessageDialog(null, secretKey);
		
	}


}
