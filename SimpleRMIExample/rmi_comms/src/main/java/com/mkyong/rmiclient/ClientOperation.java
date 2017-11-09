package com.mkyong.rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.security.*;
import javax.crypto.*;

import com.mkyong.aes.AES;
import com.mkyong.rmi.ClientCallBack;
import com.mkyong.rmi.RmiInterface;
import com.mkyong.rmiserver.ServerOperation;

import java.security.spec.*;
import java.nio.file.*;

public class ClientOperation implements ClientCallBack{
	private static RmiInterface look_up;

	private static KeyPair pair;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, NoSuchAlgorithmException, Exception {
		new ClientOperation();


	}
	public ClientOperation() throws Exception {
		UnicastRemoteObject.exportObject(this, 0);
		look_up = (RmiInterface) Naming.lookup("//localhost/MyServer");
		look_up.registered((ClientOperation)this);
		featuresSelection();
	}

	
	public String helloTo(String name) {
		System.err.println(name + "Trying to connect");
		return "Client says hello to " + name;
	}

	public static void featuresSelection() throws MalformedURLException, RemoteException, NotBoundException, NoSuchAlgorithmException, Exception {
		final JPanel panel = new JPanel();
		final JRadioButton buttonC = new JRadioButton("Confidentiality");
		final JRadioButton buttonI = new JRadioButton("Integrity");
		final JRadioButton buttonA = new JRadioButton("Authentication");
		final JLabel label = new JLabel("Select your desired security features:");

		panel.add(label);
		panel.add(buttonC);
		panel.add(buttonI);
		panel.add(buttonA);


		// confidentiality();
		
		JOptionPane.showMessageDialog(null, panel);

		String txt = "";
		if (buttonC.isSelected()) {
			txt += "Confidentiality";
			confidentiality();
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

	public static void confidentiality() throws MalformedURLException, RemoteException, NotBoundException, NoSuchAlgorithmException, Exception{
		SecretKey clientAesKey = getSecretEncryptionKey();

		 while(true) {

			 String txt = JOptionPane.showInputDialog("Client Type your message");
			 String[] clients = Naming.list("MyServer");
			 for(String client : clients) {
				 RmiInterface remote = (RmiInterface) Naming.lookup(client);
				 String response = remote.helloTo(txt);
				 SecretKey serverKey = remote.getServerKey();
				 JOptionPane.showMessageDialog(null, response);
			 }
//			 String txt = JOptionPane.showInputDialog("Type your message");
//
//			 String response = look_up.helloTo(txt);
//			 SecretKey serverKey = look_up.getServerKey();
//			 JOptionPane.showMessageDialog(null, response);

		//ENCRYPT AES KEY WITH PUBLIC KEY
		final String secretKey = "ssshhhhhhhhhhh!!!!";
		
		
		//  String originalString = "howtodoinjava.com";
		//  String encryptedString = AES.encrypt(originalString, secretKey) ;
		//  String decryptedString = AES.decrypt(encryptedString, secretKey) ;
			
		// System.out.println(originalString);
		// System.out.println(encryptedString);
		// System.out.println(decryptedString);
		
		// Path fileLocation = Paths.get("public.key");
		// Path fileLocation2 = Paths.get("private.key");
		// byte[] publicKeyBytes = Files.readAllBytes(fileLocation);
		// byte[] privateKeyBytes = Files.readAllBytes(fileLocation2);
		
		// PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
		// PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
		
		// System.out.println("PUBLIC KEY==>:" + publicKey);
		// System.out.println("PRIVATE KEY==>: " + privateKey);
		 }
	}

	public static SecretKey getSecretEncryptionKey() throws Exception{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128); // The AES key size in number of bits
		SecretKey secKey = generator.generateKey();
		return secKey;
	}
}
