package com.mkyong.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.crypto.*;

public interface RmiInterface extends Remote {
	public String helloTo(String name) throws RemoteException;	
	public String getFeature() throws RemoteException;
	public SecretKey getServerKey() throws RemoteException;
	public void registered(ClientCallBack client) throws RemoteException;
}