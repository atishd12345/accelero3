package com.example.atish.accelero3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoConfigured;
import at.abraxas.amarino.log.Logger;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RoomListFragment.Callbacks {

	private AmarinoConfigured embeddedAmarino;

	private ArduinoReceiver receiver;
	private ServiceIntentConfig intentConfig;
	
	private static String MAC = null;
    private boolean connection = false;
    
private static final String TAG = "accelero";
  
  ////old class to be replaced
  //  public class StatusAmarino extends BroadcastReceiver {
    	
  //      @Override
  //      public void onReceive(Context context, Intent intent) {
  //          final String action = intent.getAction();
  //          Log.v(TAG, "StatusAmarino onReveive connection "+connection );
  //          if(AmarinoIntent.ACTION_CONNECT.equals(action)) {
  //              connection = true;
  //              Log.v(TAG, "StatusAmarino Inside Intent.ACTION_CONNECT connection "+connection );
  //              Toast.makeText(getActivity(), "Bluetooth is connected", Toast.LENGTH_LONG).show();
  //          }
  //              else if(AmarinoIntent.ACTION_CONNECTION_FAILED.equals(action))
  //              Toast.makeText(getActivity(),"Error During connection",Toast.LENGTH_LONG).show();
  //          else{
  //              Log.v(TAG, "StatusAmarino failed on reveive but entered Connection "+connection );
  //          }
  //      }
  //  }
    
  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "onActivityResult REQUEST CODE " +requestCode +" ResultCode" +resultCode);
        switch (requestCode){
            case ASK_ACTIVATION:
                if (resultCode == Activity.RESULT_OK)
                    Toast.makeText(MainActivity.this, "BT active", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(MainActivity.this, "BT inactive", Toast.LENGTH_LONG).show();
                    //finish();
                }
            case ASK_CONNECTION:
                if(resultCode ==Activity.RESULT_OK){

                    MAC = data.getExtras().getString(DeviceList.MAC_ADDRESS);
                    Log.v(TAG, "ASK_CONNECTION MAC "+MAC );
                    Toast.makeText(MainActivity.this,"MAC Address : " +MAC,Toast.LENGTH_LONG).show();

                    //registerReceiver(statusAmarino, new IntentFilter(AmarinoIntent.ACTION_CONNECTED));
                    //Amarino.connect(MainActivity.this, MAC);
                    //Log.v(TAG, "completed Amarino.connect");
                    
                    		receiver = new ArduinoReceiver();
		intentConfig = new ServiceIntentConfig();
		registerReceiver(receiver,
				new IntentFilter(intentConfig.getIntentNameActionConnect()));
		registerReceiver(receiver,
				new IntentFilter(intentConfig.getIntentNameActionReceived()));
		Log.v(TAG, new StringBuilder("bluetooth receiver intent registered")
				.append(MAC).toString());
				
				// create an instance of embeddedAmarino to wrap BT
		// intent broadcasts to service
		embeddedAmarino = new AmarinoConfigured(this.getApplicationContext());
		embeddedAmarino.setIntentConfig(intentConfig);
		embeddedAmarino.connect(MAC);
				
                    connection = true;
                }
                case ASK_DISCONNECTION:
                	if(resultCode ==Activity.RESULT_OK){
                		MAC = data.getExtras().getString(DeviceList.MAC_ADDRESS);
                		 Log.v(TAG, "ASK_DISCONNECTION MAC "+MAC );
                		 
                	embeddedAmarino.disconnect(MAC);
			if (null != receiver) {
				try {
					unregisterReceiver(receiver);
				} catch (Exception e) {
					Log.e(TAG, Log.getStackTraceString(e));
				}
			}
		  connection = false;
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to obtain MAC Address",Toast.LENGTH_LONG).show();
                }

        }
    }
    
    
//This takes care of receiving intents
public static class ArduinoReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action == null)
					return;
				ServiceIntentConfig configuredIntents = new ServiceIntentConfig();
				if (configuredIntents.getIntentNameActionConnect().equals(
						action)) {
					Logger.d(TAG, "CONNECT request received");
					 Log.v(TAG, "CONNECT request received ");
				//	connection = true;
					Intent i = new Intent(context, BTService.class);
					i.setAction(configuredIntents.getIntentNameActionConnect());
					i.replaceExtras(intent);
					context.startService(i);
				} else if (configuredIntents.getIntentNameActionReceived()
						.equals(action)) {
					Logger.d(TAG, "DATA_RECEIVED request received");
						 Log.v(TAG, "DATA_RECEIVED request received");
					@SuppressWarnings("unused")
					char[] chData = null;
					@SuppressWarnings("unused")
					String strData = null;

					// the device address from which the data was sent, we don't
					// need it
					// here but to demonstrate how you retrieve it
					@SuppressWarnings("unused")
					final String address = intent
							.getStringExtra(ServiceIntentConfig.EXTRA_DEVICE_ADDRESS);

					// the type of data which is added to the intent
					final int dataType = intent.getIntExtra(
							ServiceIntentConfig.EXTRA_DATA_TYPE, -1);
					Log.v(TAG,
							new StringBuilder()
									.append("data received from Arduino with type: ")
									.append(dataType).toString());
					if (dataType == ServiceIntentConfig.CHAR_ARRAY_EXTRA) {
						chData = intent
								.getCharArrayExtra(ServiceIntentConfig.EXTRA_DATA);
					}
					if (dataType == ServiceIntentConfig.STRING_EXTRA) {
						strData = intent
								.getStringExtra(ServiceIntentConfig.EXTRA_DATA);
					}
				} else if (configuredIntents.getIntentNameActionDisconnect()
						.equals(action)) {
					Logger.d(TAG, "DISCONNECT request received");
					Log.v(TAG, "DISCONNECT request received");
					Intent i = new Intent(context, BTService.class);
					i.setAction(configuredIntents
							.getIntentNameActionDisconnect());
					i.replaceExtras(intent);
					context.startService(i);
				} else if (configuredIntents
						.getIntentNameActionConnectedDevices().equals(action)) {
					Logger.d(TAG, "GET_CONNECTED_DEVICES request received");
						Log.v(TAG, "GET_CONNECTED_DEVICES request received");
					Intent i = new Intent(context, BTService.class);
					i.setAction(configuredIntents
							.getIntentNameActionGetConnectedDevices());
					context.startService(i);
				}
			}
		}
	}

    //moved into main fragment
    //   private static final int ASK_ACTIVATION = 1;
  //  private static final int ASK_CONNECTION = 2;
//    private BluetoothAdapter btadapter = null;
//    private static String MAC = null;

    private boolean mTwoPane;

    //   Used to store the last screen title. For use in {@link restoreActionBar()}.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Log.v(TAG, "MainActivity Phone onCreate mTwoPane" + mTwoPane);
        // start the Main fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RoomListFragment()).commit();

        }


        // masterflow

        if (findViewById(R.id.room_detail_container) != null) {
        
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        Log.v(TAG, "MainActivity Tablet mTwoPane " + mTwoPane);
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((RoomListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container))
                    .setActivateOnItemClick(true);
        }
        
        
        //initialise floating action button
        FloatingActionButton();
        //set up navigation Drawer
        NavigationDrawer();

        //INITIALISE BLUETOOTH
        // moved to main fragment
      //  BluetoothInit();

    }


    //sets up Navigation drawer
    public void NavigationDrawer(){
         Log.v(TAG, "MainActivity NavigationDrawer ");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    //sets up floating action button
     public void FloatingActionButton(){
          Log.v(TAG, "MainActivity FloatingActionButton ");
         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Snackbar.make(view, "Replace with quick BT settings", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
             }
         });
     }

    /**
     * Callback method from {@link RoomListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
         Log.v(TAG, "MainActivity onItemSelected mTwoPane"+mTwoPane);
        if (mTwoPane) {
            Log.v(TAG, "MainActivity onItemSelected Tablet inside ");
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RoomDetailFragment.ARG_ITEM_ID, id);
            RoomDetailFragment fragment = new RoomDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.room_detail_container, fragment)
                    .commit();

        } else {
            Log.v(TAG, "MainActivity onItemSelected Else inside ");
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, RoomDetailActivity.class);
            detailIntent.putExtra(RoomDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

            //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.v(TAG, "MainActivity Action_settings Selected ");
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
         //   Toast.makeText(this, "Settings Pressed", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // methods for implementing navdrawer
    @Override
    public void onBackPressed() {
        Log.v(TAG, "MainActivity Back Pressed ");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        FragmentManager fragmentManager = getFragmentManager();
//        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        RoomListFragment RoomListFrag = new RoomListFragment();
        MainFragment MainFrag = new MainFragment();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
             Log.v(TAG, "MainActivity Home Selected ");
            // Handle the Home action
            String tag= "tag";
            // getFragmentManager().beginTransaction().replace(R.id.container, new RoomListFragment(), "stuff").commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RoomListFrag)
                    .commit();
//            transaction.beginTransaction()
//                    .replace(R.id.container, new RoomListFragment())
//                    .commit();

        } else if (id == R.id.nav_settings) {
             Log.v(TAG, "MainActivity Nav_settings Selected ");
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {
             Log.v(TAG, "MainActivity Help Selected ");

        } else if (id == R.id.nav_about) {
             Log.v(TAG, "MainActivity About Selected ");
             Toast.makeText(getApplicationContext(), "ABOUT",
                      Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_test) {
             Log.v(TAG, "MainActivity Test Selected ");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFrag)
                    .commit();

    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
