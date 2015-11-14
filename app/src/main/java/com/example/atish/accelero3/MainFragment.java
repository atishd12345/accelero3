package com.example.atish.accelero3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainFragment extends Fragment {
    Communicator communicator;

    private static TextView test_TextView;

    @Override
    public void onStop() {
        super.onStop();
        communicator.disconnect(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main,container,false);

        test_TextView = (TextView) view.findViewById(R.id.test_TextView);

        final Button bt_connect = (Button) view.findViewById(R.id.bt_connect);
        final Button bt_disconnect = (Button) view.findViewById(R.id.bt_disconnect);
        final Button fragment_button = (Button) view.findViewById(R.id.test_fragment_button);

        final Button btn_ON_1 = (Button) view.findViewById(R.id.btn_on_1);
        final Button btn_OFF_1 = (Button) view.findViewById(R.id.btn_off_1);
        final Button btn_ON_2 = (Button) view.findViewById(R.id.btn_on_2);
        final Button btn_OFF_2 = (Button) view.findViewById(R.id.btn_off_2);

        communicator = (Communicator) getActivity();

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             communicator.connectPaired();
            }
        });

        bt_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            communicator.disconnect(null);
            }
        });

       btn_ON_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              communicator.sendCommand(null,'A',1);
            }
        });

        btn_OFF_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {communicator.sendCommand(null,'A',0);
            }
        });

        btn_ON_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {communicator.sendCommand(null,'B',1);
            }
        });

        btn_OFF_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {communicator.sendCommand(null,'B',0);
            }
        });

        fragment_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {communicator.setBluetooth(true);}});

            return view;
    }




    // public void BluetoothInit(){
    //             Log.v(TAG, "BluetoothInit" );
    //     btadapter = BluetoothAdapter.getDefaultAdapter();
    //     if(!btadapter.isEnabled()){
    //      //   Intent active = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    //         startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ASK_ACTIVATION);

    //     }
    //     if(btadapter== null){
    //         Toast.makeText(getActivity(), "oops! NO BT", Toast.LENGTH_SHORT).show();
    //     }
    // }
    // public void enableBT(View view){
    //     Log.v(TAG, "enableBT" );
    //     BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //     if (!mBluetoothAdapter.isEnabled()){
    //         Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    //         // The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0), that the system passes back to you in your onActivityResult()
    //         // implementation as the requestCode parameter.
    //         int REQUEST_ENABLE_BT = 1;
    //         startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
    //     }
    // }


	


}
