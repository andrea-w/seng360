package com.mkyong.integrity;

import java.security.*;
import javax.crypto.*;
import javax.swing.JOptionPane;
import java.security.Signature;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.lang.SecurityException;

public class Integrity  {

	private static KeyPair pair;

	public Integrity(KeyPair keypair) {
		pair = keypair;
	}

	/*
	@params:
		msg: byte[] of message to be signed
	*/
	public static byte[] signMessage(byte[] msg) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		try {
			// generate Signature object
			Signature dsa = Signature.getInstance("SHA256withDSA");		

			// initialize object with private key
			PrivateKey priv = pair.getPrivate();
			dsa.initSign(priv);

			// update & sign the data
			dsa.update(msg);

			byte[] sig = dsa.sign();

			return sig;

	
		} catch(NoSuchAlgorithmException e) {
			System.out.println(e);
		} catch(InvalidKeyException e) {
			System.out.println(e);
		} catch(SignatureException e) {
			System.out.println(e);
		}

		return null;
		
	}

	/*
	@params:
		data: byte array containing message
		sig: byte array containing message signature
		dsa: Signature object
	*/
	public static boolean verifySig(byte[] data, byte[] sig, Signature dsa) throws SignatureException, InvalidKeyException {
			// initialize object with public key
			PublicKey pub = pair.getPublic();
			
			try {
			dsa.initVerify(pub);
	
			// update and verify data
			dsa.update(data);
			boolean verifies = dsa.verify(sig);
			System.out.println("Signature verifies: " + verifies);
	
			if (!verifies) {
				JOptionPane.showMessageDialog(null, "Warning!\nMessage has been corrupted.");	
			}
			
			return verifies;
		} catch(SignatureException e) {
			System.out.println(e);
		} catch (InvalidKeyException e) {
			System.out.println(e);
		}

		return false;
	}
}

