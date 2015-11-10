package com.example.atish.accelero3;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;


public class MainFragment extends Fragment {

    private static final String TAG = "accelero";

    //taken from Main activity
    private static final int ASK_ACTIVATION = 1;
    private static final int ASK_CONNECTION = 2;
    private BluetoothAdapter btadapter = null;
    private static String MAC = null;
    private boolean connection = false;

    private StatusAmarino statusAmarino = new StatusAmarino();

    private static EditText test_EditText;
    private static TextView test_TextView;


    @Override
    public void onStop() {
        super.onStop();
        if(connection != false){
            Amarino.disconnect(getActivity(),MAC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main,container,false);
        test_TextView = (TextView) view.findViewById(R.id.test_TextView);
        test_EditText = (EditText) view.findViewById(R.id.test_EditText);
        final Button bt_connect = (Button) view.findViewById(R.id.bt_connect);
        final Button bt_disconnect = (Button) view.findViewById(R.id.bt_disconnect);
        final Button fragment_button = (Button) view.findViewById(R.id.test_fragment_button);

        final Button btn_ON_1 = (Button) view.findViewById(R.id.btn_on_1);
        final Button btn_OFF_1 = (Button) view.findViewById(R.id.btn_off_1);
        final Button btn_ON_2 = (Button) view.findViewById(R.id.btn_on_2);
        final Button btn_OFF_2 = (Button) view.findViewById(R.id.btn_off_2);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use getactivity to open another activity from a fragment
                Intent DeviceList = new Intent(getActivity(), DeviceList.class);
                startActivityForResult(DeviceList, ASK_CONNECTION);
            }
        });



        bt_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != false) {
                    getActivity().unregisterReceiver(statusAmarino);
                    Amarino.disconnect(getActivity(), MAC);
                     Log.v(TAG, "bt_disconnect onclick After Amarino disconnect");
                    connection = false;
                }
            }
             //setBluetooth(false);
        });

       btn_ON_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(connection != false){
                    Amarino.sendDataToArduino(getActivity(), MAC, 'A', 1);
                }
                else{
                      Log.v(TAG, "BTN ON 1 connection "+connection );
                    Toast.makeText(getActivity(), "onclick DISCONNECTED BTN ON 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_OFF_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != false) {
                    Amarino.sendDataToArduino(getActivity(), MAC, 'A', 0);
                } else {
                    Log.v(TAG, "BTN OFF 1 connection "+connection );
                    Toast.makeText(getActivity(), "onclick DISCONNECTED BTN OFF 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_ON_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(connection != false){
                    Amarino.sendDataToArduino(getActivity(), MAC, 'B', 1);
                }
                else{
                      Log.v(TAG, "BTN ON 2 connection "+connection );
                    Toast.makeText(getActivity(), "onclick DISCONNECTED BTN ON 2", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_OFF_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != false) {
                    Amarino.sendDataToArduino(getActivity(), MAC, 'B', 0);
                } else {
                    Toast.makeText(getActivity(), "onclick DISCONNECTED BTN Off 2", Toast.LENGTH_SHORT).show();
                }
            }
        });


        fragment_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Fragment_buttonClicked(v);
                    }
                }
        );
            return view;
          }

    public void Fragment_buttonClicked (View view){

      //  BluetoothInit();
     //   enableBT(view);
       // Toast.makeText(getActivity(),"ButtonPressed",Toast.LENGTH_LONG).show();
        setBluetooth(true);
        Log.v(TAG, "Fragment button after setting on bluetooth");
    }

    public static boolean setBluetooth(boolean enable) {
         Log.v(TAG, "setBluetooth" );
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            Log.v(TAG, "Enable" +enable );
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
                Log.v(TAG, "Enable" +enable );
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    public void BluetoothInit(){
                Log.v(TAG, "BluetoothInit" );
        btadapter = BluetoothAdapter.getDefaultAdapter();
        if(!btadapter.isEnabled()){
         //   Intent active = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ASK_ACTIVATION);

        }
        if(btadapter== null){
            Toast.makeText(getActivity(), "oops! NO BT", Toast.LENGTH_SHORT).show();
        }
    }
    public void enableBT(View view){
        Log.v(TAG, "enableBT" );
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()){
            Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0), that the system passes back to you in your onActivityResult()
            // implementation as the requestCode parameter.
            int REQUEST_ENABLE_BT = 1;
            startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "onActivityResult REQUEST CODE " +requestCode +" ResultCode" +resultCode);
        switch (requestCode){
            case ASK_ACTIVATION:
                if (resultCode == Activity.RESULT_OK)
                    Toast.makeText(getActivity(), "BT active", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getActivity(), "BT inactive", Toast.LENGTH_LONG).show();
                    //finish();
                }
            case ASK_CONNECTION:
                if(resultCode ==Activity.RESULT_OK){

                    MAC = data.getExtras().getString(DeviceList.MAC_ADDRESS);
                    Log.v(TAG, "ASK_CONNECTION MAC "+MAC );
                    Toast.makeText(getActivity(),"MAC Address : " +MAC,Toast.LENGTH_LONG).show();

                    getActivity().registerReceiver(statusAmarino, new IntentFilter(AmarinoIntent.ACTION_CONNECTED ));
                    Amarino.connect(getActivity(),MAC);
                }
                else{
                    Toast.makeText(getActivity(),"Failed to obtain MAC Address",Toast.LENGTH_LONG).show();
                }

        }
    }

    public class StatusAmarino extends BroadcastReceiver {
        Log.v(TAG, "StatusAmarino Broadcastreveiver connection "+connection );
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.v(TAG, "StatusAmarino onReveive connection "+connection );
            if(AmarinoIntent.ACTION_CONNECT.equals(action)) {
                connection = true;
                Log.v(TAG, "StatusAmarino Inside Intent.ACTION_CONNECT connection "+connection );
                Toast.makeText(getActivity(), "Bluetooth is connected", Toast.LENGTH_LONG).show();
            }
                else if(AmarinoIntent.ACTION_CONNECTION_FAILED.equals(action))
                Toast.makeText(getActivity(),"Error During connection",Toast.LENGTH_LONG).show();
        }

    }


}
