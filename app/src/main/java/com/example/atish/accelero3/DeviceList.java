package com.example.atish.accelero3;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.util.Set;

/**
 * Created by Atish on 07/11/2015.
 */
public class DeviceList extends ListActivity{

    private static final String TAG = "accelero";

    private BluetoothAdapter btadapter;
    static String MAC_ADDRESS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate Device list");
        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        btadapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> PairedDevices = btadapter.getBondedDevices();

        if (PairedDevices.size() > 0){
            for(BluetoothDevice device : PairedDevices){
                String name = device.getName();
                String mac = device.getAddress();
                ArrayBluetooth.add(name + "\n" + mac);
            }
        }
        setListAdapter(ArrayBluetooth);
        Log.v(TAG, "onCreate List Adapter set up");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String InfoGeneral = ((TextView) v).getText().toString();
        String Address = InfoGeneral.substring(InfoGeneral.length() - 17);
        Intent ReturnMAC = new Intent();
        ReturnMAC.putExtra(MAC_ADDRESS, Address);
        setResult(RESULT_OK, ReturnMAC);
        Log.v(TAG, "onClick selected MAC " +MAC_ADDRESS +Address +" Infogeneral " +InfoGeneral);
        finish();
    }
}
