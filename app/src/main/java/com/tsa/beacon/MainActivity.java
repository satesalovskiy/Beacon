package com.tsa.beacon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);


        BluetoothManager btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = btManager.getAdapter();

        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();


        if(bluetoothAdapter == null){
            Toast.makeText(this, "Bluetooth off", Toast.LENGTH_SHORT).show();
        }

        if(!bluetoothAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, 1);
        }

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }


        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG)) {
// Show an explanation to the user
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }

        } else {
// No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

        bluetoothLeScanner.startScan(leScanCallback);


//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//
//        registerReceiver(broadcastReceiver, filter);
//
//        boolean status = bluetoothAdapter.startDiscovery();
//
//        if(status){
//            Toast.makeText(this, "Looking for devices", Toast.LENGTH_SHORT).show();
//        }
    }
//
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            Toast.makeText(context, "In broadcast", Toast.LENGTH_LONG).show();
//
//            if (BluetoothDevice.ACTION_FOUND.equals(action)){
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                Toast.makeText(context, device.getName(), Toast.LENGTH_LONG).show();
//            }
//        }
//    };

    private ScanCallback leScanCallback = new ScanCallback() {
        public void onScanResult(int callbackType, ScanResult result) {

            if(result.getDevice().getName() != null){
                Toast.makeText(MainActivity.this, result.getDevice().getName(), Toast.LENGTH_SHORT).show();

            if(result.getRssi()> -60) {
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=U6inBmvO8Jc")), "Browser"));

            }
            }


        }
    };
}


