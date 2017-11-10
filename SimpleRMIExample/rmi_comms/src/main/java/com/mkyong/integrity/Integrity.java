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
	public MessageSigThing signMessage(byte[] msg) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		try {
			// generate Signature object
			Signature dsa = Signature.getInstance("SHA256withDSA");		

			// initialize object with private key
			PrivateKey priv = pair.getPrivate();
			dsa.initSign(priv);

			// update & sign the data
			dsa.update(msg);

			byte[] sig = dsa.sign();

			MessageSigThing mst = new MessageSigThing(msg, sig, dsa);

			return mst;

	
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
	public static boolean verifySig(MessageSigThing mst) throws SignatureException, InvalidKeyException {
		// initialize object with public key
		PublicKey pub = pair.getPublic();
		
		try {
			Signature dsa = mst.getSignatureObject();	
			dsa.initVerify(pub);
	
			// update and verify data
			byte[] data = mst.getData();
			byte[] sig = mst.getSig();
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

	public class MessageSigThing{
		private byte[] data;
		private byte[] sig;
		private Signature dsa;

		public MessageSigThing(byte[] data, byte[] sig, Signature dsa) {
			this.data = data;
			this.sig = sig;
			this.dsa = dsa;
		}

		public Signature getSignatureObject() {
			return dsa;
		}

		public byte[] getSig() {
			return sig;
		}

		public byte[] getData() {
			return data;
		}
	}
}

