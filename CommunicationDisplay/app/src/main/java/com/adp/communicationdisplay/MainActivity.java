package com.adp.communicationdisplay;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button btnStart;
    UsbDevice device;
    UsbManager usbManager;
    static UsbSerialDevice serialDevice;
    TextView textView;
    DataViewModel dataViewModel;
    BottomNavigationView bottomNavigationView;
    MainFragment mainFragment = new MainFragment();
    EngineFragment engineFragment = new EngineFragment();

    private static final  String USB_PERMISSION= "com.adp.communicationdisplay.USB_PERMISSION";
    UsbDeviceConnection usbDeviceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        dataViewModel =new ViewModelProvider(this).get(DataViewModel.class);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.s1);

        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        Log.e("START","PROGRAM STARTED");


        //IntentFilter filter = new IntentFilter(USB_PERMISSION);
        registerReceiver(broadcastReceiver,
                new IntentFilter(USB_PERMISSION));

        registerReceiver(broadcastReceiver, new IntentFilter(
                UsbManager.ACTION_USB_DEVICE_ATTACHED));
        registerReceiver(broadcastReceiver, new IntentFilter(
                UsbManager.ACTION_USB_DEVICE_DETACHED));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    private void startClick() {
        if(usbManager==null){
            Log.e("MANAGER","NOT INIT");
        }else{
            Log.e("MANAGER","NOT NULL");
        }
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                //0x2341
                if (deviceVID == 0x16C0)
                {
                    if(usbManager.hasPermission(device)){
                        Log.e("ALREADY","HAVE PERM");
                    }else {
                        Log.e("DEVICE", "PERMISSION REQUESTED");
                        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0,
                                new Intent(USB_PERMISSION), PendingIntent.FLAG_MUTABLE);
                        usbManager.requestPermission(device, pi);
                    }
                    keep = false;
                } else {
                    Toast.makeText(this,"NOT ARDUINO",Toast.LENGTH_SHORT).show();
                    Log.e("DEVICE","NOT ARDUINO");
                    usbDeviceConnection = null;
                    device = null;
                }
                if (!keep)
                    break;
            }
        }else{
            Log.e("DEVICE","NO USB DEVICES"+usbDevices.toString());
            UsbAccessory[] usbAccessory = usbManager.getAccessoryList();
            if(usbAccessory!=null) {
                Log.e("ACCESSORY", usbAccessory.toString());
            }else{
                Log.e("ACCESSORY", "NO ACCESSORY DEVICES" );
            }
        }
    }

    private void stopClick() {
        if(serialDevice!=null) {
            serialDevice.close();
            serialDevice =null;
        }
    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("RECEIVED","RECEIVES");
            if (USB_PERMISSION.equals(intent.getAction())) {

                boolean granted =
                        intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,false);
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        Log.e("BUNDLE", key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                    }
                }else{
                    Log.e("BUNDLE","NULL");
                }
                if (granted) {
                    Log.e("SERIAL", "PERM GRANTED");
                    usbDeviceConnection = usbManager.openDevice(device);
                    serialDevice = UsbSerialDevice.createUsbSerialDevice(device, usbDeviceConnection);
                    if (serialDevice != null) {
                        if (serialDevice.open()) { //Set Serial Connection Parameters.
                            //setUiEnabled(true); //Enable Buttons in UI
                            serialDevice.setBaudRate(9600);
                            serialDevice.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialDevice.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialDevice.setParity(UsbSerialInterface.PARITY_NONE);
                            serialDevice.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialDevice.read(mCallback); //
                            Log.e("CONNECTION","Serial Connection Opened!\n");

                        } else {
                            Log.e("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.e("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.e("SERIAL", "PERM NOT GRANTED");
                    usbDeviceConnection = usbManager.openDevice(device);
                    serialDevice = UsbSerialDevice.createUsbSerialDevice(device, usbDeviceConnection);
                    if (serialDevice != null) {
                        if (serialDevice.open()) { //Set Serial Connection Parameters.
                            //setUiEnabled(true); //Enable Buttons in UI
                            serialDevice.setBaudRate(9600);
                            serialDevice.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialDevice.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialDevice.setParity(UsbSerialInterface.PARITY_NONE);
                            serialDevice.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialDevice.read(mCallback); //
                            Log.e("CONNECTION","Serial Connection Opened!\n");

                        } else {
                            Log.e("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.e("SERIAL", "PORT IS NULL");
                    }
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                Toast.makeText(MainActivity.this,"Device Attached",Toast.LENGTH_SHORT).show();
                Log.e("DEVICE", "ATTACHED");
                startClick();
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                Toast.makeText(MainActivity.this,"Device Detached",Toast.LENGTH_SHORT).show();
                Log.e("DEVICE", "DETACHED");
                stopClick();
            }
        };
    };

    public static void reset(){
        if(serialDevice==null){
            //Toast.makeText(context, "NO DEVICE",Toast.LENGTH_SHORT);
            Log.e("A","A");
        }else {
            Log.e("X","X");
            serialDevice.write("X".getBytes());
        }
    }
    String data = null;
    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            Log.e("DATA", "RECEIVED");
            try {
                String a = new String(arg0, "UTF-8");
                Log.e("DATA=", a);
                if(a.equals("")){
                    return;
                }
                if (data == null) {
                    data = a;
                } else {
                    data = data + a;
                }
                if(data.contains("$")){
                    data = data.replace("$","");
                    String b[] = data.split("#");
                    for (String q:b){
                        if(q.contains("SOC")){
                            q = q.replace("SOC","");
                            Float SOC_val = Float.parseFloat(q);
                            dataViewModel.setSOC(SOC_val*100);
                        }
                        if(q.contains("MV")){
                            q = q.replace("MV","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setMilliVolt(mill_val);
                        }
                        if(q.contains("IB")){
                            q = q.replace("IB","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setBatteryCurrent(mill_val);
                        }
                        if(q.contains("ERPM")){
                            q = q.replace("ERPM","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setEngineRPM(mill_val);
                        }
                        if(q.contains("MRPM")){
                            q = q.replace("MRPM","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setMotorRPM(mill_val);
                        }
                        if(q.contains("ETO")){
                            q = q.replace("ETO","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setEngineTorque(mill_val);
                        }
                        if(q.contains("MTO")){
                            q = q.replace("MTO","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setMotorTorque(mill_val);
                        }
                        if(q.contains("MIL")){
                            q = q.replace("MIL","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setKmpl(mill_val);
                        }
                        if(q.contains("THR")){
                            q = q.replace("THR","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setEngineThrottle(mill_val);
                        }
                        if(q.contains("HTR")){
                            q = q.replace("HTR","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setThrottle(mill_val);
                        }
                        if(q.contains("VS")){
                            q = q.replace("VS","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setSpeed(mill_val);
                        }
                        if(q.contains("TRD")){
                            q = q.replace("TRD","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setDistance(mill_val);
                        }
                        if(q.contains("ISF")){
                            q = q.replace("ISF","");
                            Float mill_val = Float.parseFloat(q);
                            dataViewModel.setFuelConsump(mill_val);
                        }
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.s1) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, mainFragment) //Fragement f= new FirstFrag()
                        .commit();
                return true;
            } else if (itemId == R.id.s2) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, engineFragment)
                        .commit();
                return true;
            }
//            } else if (itemId == R.id.s3) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frameLayout, electricFragment)
//                        .commit();
//                return true;
//            }
            return false;
        }

}