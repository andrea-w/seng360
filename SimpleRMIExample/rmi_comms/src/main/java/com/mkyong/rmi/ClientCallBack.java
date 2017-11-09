package com.mkyong.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallBack extends Remote{

    public String helloTo(String name) throws RemoteException;
}
