package com.example.atish.accelero3;

/**
 * Created by Atish on 15/11/2015.
 */
public interface Communicator {
    public void connect(String MAC);
    public void connectPaired();
    public void disconnect(String MAC);
    public void sendCommand(String MAC,char flag,int value);
    public boolean setBluetooth(boolean enable);
}
