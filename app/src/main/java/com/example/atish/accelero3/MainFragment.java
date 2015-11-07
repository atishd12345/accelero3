package com.example.atish.accelero3;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.abraxas.amarino.Amarino;


public class MainFragment extends Fragment {

    //taken from Main activity
    private static final int ASK_ACTIVATION = 1;
    private static final int ASK_CONNECTION = 2;
    private BluetoothAdapter btadapter = null;
    private static String MAC = null;
    private boolean connection = false;

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

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use getactivity to open another activity from a fragment
                Intent DeviceList = new Intent(getActivity(), DeviceList.class);
                startActivityForResult(DeviceList, ASK_CONNECTION);
            }
        });

        bt_disconnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 if(connection != false){
                    Amarino.disconnect(getActivity(),MAC);
                 }
//                else{
//                     Toast.makeText(getActivity(),"Bluetooth is not acivated ",Toast.LENGTH_SHORT).show();
//                 }
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

       // BluetoothInit();
       // Toast.makeText(getActivity(),"ButtonPressed",Toast.LENGTH_LONG).show();
    setBluetooth(true);
    }

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    public void BluetoothInit(){
        btadapter = BluetoothAdapter.getDefaultAdapter();
        if(!btadapter.isEnabled()){
            Intent active = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(active, ASK_ACTIVATION);

        }
        if(btadapter==null){
            Toast.makeText(getActivity(), "oops! NO BT", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ASK_ACTIVATION:
                if(resultCode == Activity.RESULT_OK)
                    Toast.makeText(getActivity(), "BT active", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getActivity(), "BT inactive", Toast.LENGTH_LONG).show();
                    //finish();
                }
            case ASK_CONNECTION:
                if(resultCode ==Activity.RESULT_OK){
                    MAC = data.getExtras().getString(DeviceList.MAC_ADDRESS);
                    Toast.makeText(getActivity(),"MAC Address : " +MAC,Toast.LENGTH_LONG).show();

                    Amarino.connect(getActivity(),MAC);
                }
                else{
                    Toast.makeText(getActivity(),"Failed to obtain MAC Address",Toast.LENGTH_LONG).show();
                }
            default:
                Toast.makeText(getActivity(),"WTF",Toast.LENGTH_LONG).show();
        }
    }



}
