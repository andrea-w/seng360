package com.mkyong.rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.*;
import javax.crypto.*;

public interface RmiInterface extends Remote {
	public String helloTo(String name) throws RemoteException;	
	public String getFeature() throws RemoteException;
	public SecretKey getServerKey() throws RemoteException;
}