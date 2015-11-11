/**
 * 
 */
package com.example.atish.accelero3;

import at.abraxas.amarino.intent.AmarinoServiceIntentConfig;

/**
 * @author fbeachler
 * 
 */
public class ServiceIntentConfig implements AmarinoServiceIntentConfig {
	//Changed all the namespaces by adding accelero3
	public static final String DEFAULT_ACTION_CONNECT = "accelero3.bt.intent.action.CONNECT";
	public static final String DEFAULT_ACTION_DISCONNECT = "accelero3.bt.intent.action.DISCONNECT";
	public static final String DEFAULT_ACTION_SEND = "accelero3.bt.intent.action.SEND";
	public static final String DEFAULT_ACTION_RECEIVED = "accelero3.bt.intent.action.RECEIVED";
	public static final String DEFAULT_ACTION_CONNECTED = "accelero3.bt.intent.action.CONNECTED";
	public static final String DEFAULT_ACTION_DISCONNECTED = "accelero3.bt.intent.action.DISCONNECTED";
	public static final String DEFAULT_ACTION_CONNECTION_FAILED = "accelero3.bt.intent.action.CONNECTION_FAILED";
	public static final String DEFAULT_ACTION_PAIRING_REQUESTED = "accelero3.bt.intent.action.PAIRING_REQUESTED";
	public static final String DEFAULT_ACTION_GET_CONNECTED_DEVICES = "accelero3.bt.intent.action.ACTION_GET_CONNECTED_DEVICES";
	public static final String DEFAULT_ACTION_CONNECTED_DEVICES = "accelero3.bt.intent.action.ACTION_CONNECTED_DEVICES";
	public static final String DEFAULT_ACTION_ENABLE = "accelero3.bt.intent.action.ENABLE";
	public static final String DEFAULT_ACTION_DISABLE = "accelero3.bt.intent.action.DISABLE";
	public static final String DEFAULT_ACTION_DISABLE_ALL = "accelero3.bt.intent.action.DISABLE_ALL";
	public static final String DEFAULT_ACTION_EDIT_PLUGIN = "accelero3.bt.intent.action.EDIT_PLUGIN";

	@Override
	public String getIntentNameActionSend() {
		return DEFAULT_ACTION_SEND;
	}

	@Override
	public String getIntentNameActionReceived() {
		return DEFAULT_ACTION_RECEIVED;
	}

	@Override
	public String getIntentNameActionPairingRequested() {
		return DEFAULT_ACTION_PAIRING_REQUESTED;
	}

	@Override
	public String getIntentNameActionGetConnectedDevices() {
		return DEFAULT_ACTION_CONNECTED_DEVICES;
	}

	@Override
	public String getIntentNameActionEnable() {
		return DEFAULT_ACTION_ENABLE;
	}

	@Override
	public String getIntentNameActionEditPlugin() {
		return DEFAULT_ACTION_EDIT_PLUGIN;
	}

	@Override
	public String getIntentNameActionDisconnected() {
		return DEFAULT_ACTION_DISCONNECTED;
	}

	@Override
	public String getIntentNameActionDisconnect() {
		return DEFAULT_ACTION_DISCONNECT;
	}

	@Override
	public String getIntentNameActionDisableAll() {
		return DEFAULT_ACTION_DISABLE_ALL;
	}

	@Override
	public String getIntentNameActionDisable() {
		return DEFAULT_ACTION_DISABLE;
	}

	@Override
	public String getIntentNameActionConnectionFailed() {
		return DEFAULT_ACTION_CONNECTION_FAILED;
	}

	@Override
	public String getIntentNameActionConnectedDevices() {
		return DEFAULT_ACTION_CONNECTED_DEVICES;
	}

	@Override
	public String getIntentNameActionConnected() {
		return DEFAULT_ACTION_CONNECTED;
	}

	@Override
	public String getIntentNameActionConnect() {
		return DEFAULT_ACTION_CONNECT;
	}
}
