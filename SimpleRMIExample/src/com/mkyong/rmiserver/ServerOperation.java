package com.mkyong.rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.io.*;

import java.security.*;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.*;
import com.mkyong.rmi.ClientCallBack;
import com.mkyong.rmiinterface.RmiInterface;
import com.mkyong.rmiclient.ClientOperation;


public class ServerOperation extends UnicastRemoteObject implements RmiInterface {
	private static final long serialVersionUID = 1L;	
	private static RmiInterface look_up;
	private static SecretKey serverSecretKey;
	private static String feature;
	private static ClientCallBack client;
	public ServerOperation() throws RemoteException {
		super();
	}
	
	public String helloTo(String name) throws RemoteException{
		System.err.println(name + "Trying to connect");
		return "Server says hello to " + name;
	}

	public void registered(ClientCallBack client) {
		this.client = client;
	}
	public String getFeature() throws RemoteException {
		return feature;
	}

	public SecretKey getServerKey() {
		return serverSecretKey;
	}
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception {
		try {

			Naming.rebind("//localhost/MyServer", new ServerOperation());
            System.err.println("Server ready");
			featuresSelection();
        } catch (Exception e) {
        	System.err.println("Server exception: " + e.toString());
          e.printStackTrace();
        }


        
	}

	public static void featuresSelection() throws MalformedURLException, RemoteException, NotBoundException, Exception {
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
			confidentiality();
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

	public static void confidentiality() throws MalformedURLException, RemoteException, NotBoundException, Exception {
		look_up = (RmiInterface) Naming.lookup("//localhost/MyServer");
		generateKeyPair();
		// String txt = "test";
		// String response = look_up.helloTo(txt);
		
		while(true) {
			String txt = JOptionPane.showInputDialog("Type your message");

			ServerOperation.client.helloTo("server sends back" + txt);
			String response = look_up.helloTo(txt);

			SecretKey serverKey = look_up.getServerKey();

			JOptionPane.showMessageDialog(null, response);
		}
		// SecretKey key = getSecretEncryptionKey();
		// serverSecretKey = key;
		// System.out.println(serverSecretKey);

	}
	
	public static void generateKeyPair() throws IOException, NoSuchAlgorithmException{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair kp = kpg.genKeyPair();
	
		byte[] publicKey = kp.getPublic().getEncoded();
		byte[] privateKey = kp.getPrivate().getEncoded();
	
		FileOutputStream fos = new FileOutputStream("public.key");
		fos.write(publicKey);
		fos.close();
		fos = new FileOutputStream("private.key");
		fos.write(privateKey);
		fos.close();
	}
}
