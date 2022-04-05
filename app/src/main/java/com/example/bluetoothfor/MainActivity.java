package com.example.bluetoothfor;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private String[] names;
    private String[] address;

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ArrayList<String> example = new ArrayList<String>();
    ArrayList<String> addressesList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button on = (Button) findViewById(R.id.onButton);
        Button search = (Button)findViewById(R.id.search_button);
//        Button search = (Button) findViewById(R.id.search_button);
        ListView list = (ListView) findViewById(R.id.list);
        // if bluetooth is active when user open the app
        if( bluetoothAdapter.isEnabled() ){
            getDevices();
            showDevices(list);
        }

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBluetooth();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDevices();
                        showDevices(list);
                    }
                }, 3000);

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getDevices();
//                showDevices(list);
//            }
//        });

//
//        if (bluetoothAdapter == null) {
//            // Device doesn't support Bluetooth
//        }else{
//            if (!bluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 0);
//
//                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//                if (pairedDevices.size() > 0) {
//                    // There are paired devices. Get the name and address of each paired device.
//                    for (BluetoothDevice device : pairedDevices) {
//                        String deviceName = device.getName();
//                        String deviceHardwareAddress = device.getAddress(); // MAC address
//                        Log.i("device", deviceName);
//                    }
//                }else {
//                    Log.i("device", "no found");
//                }
//            }else{
//                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//                if (pairedDevices.size() > 0) {
//                    // There are paired devices. Get the name and address of each paired device.
//                    for (BluetoothDevice device : pairedDevices) {
//                        String deviceName = device.getName();
//                        String deviceHardwareAddress = device.getAddress(); // MAC address
//                        Log.i("device", deviceName);
//                    }
//                }else {
//                    Log.i("device", "no found");
//                }
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }
    private void showDevices(ListView list){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, example);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("name", (String) list.getItemAtPosition(i));
            }
        });
    }
    private void requestBluetooth() {
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        startActivity(enableBluetoothIntent);
    }

    private void getDevices() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i("device", deviceName);
                example.add(deviceName);
                addressesList.add(deviceHardwareAddress);
            }
        } else {
            Log.i("device", "404 No devices found");
        }
    }
    private void searchBluetoothDevices(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };
}