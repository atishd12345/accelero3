/**
 * 
 */
package com.example.atish.accelero3;

import at.abraxas.amarino.service.AmarinoService;

/**
 * @author fbeachler
 * 
 */
public class BTService extends AmarinoService {

	public static final String TAG = "BTService";

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.abraxas.amarino.service.AmarinoService#onCreate()
	 */
	@Override
	public void onCreate() {
		super.setIntentConfig(new ServiceIntentConfig());
		//check if it needs to be MainActivity.class or MainFragment.class
		super.setNotifLaunchIntentClass(MainActivity.class);
		super.onCreate();
	}

}
