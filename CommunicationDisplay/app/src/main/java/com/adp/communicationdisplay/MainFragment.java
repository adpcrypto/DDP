package com.adp.communicationdisplay;

import static java.lang.Math.round;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.adp.communicationdisplay.BatteryView;

import com.github.anastr.speedviewlib.AwesomeSpeedometer;
import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.anastr.speedviewlib.SpeedView;

public class MainFragment extends Fragment {

    DataViewModel dataViewModel;
    BatteryView batteryView;

    Button reset;
    PointerSpeedometer engineRPMmeter,engineTorqueMeter,motorTorqueMeter,throttleMeter,speedometer,odometer,milegemeter;
    public MainFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.vehicle_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        engineRPMmeter = view.findViewById(R.id.engineRPM);
        speedometer = view.findViewById(R.id.veh_speed);
        throttleMeter =view.findViewById(R.id.hand_throttle);
        engineTorqueMeter = view.findViewById(R.id.e_to);
        motorTorqueMeter =view.findViewById(R.id.m_to);
        batteryView = view.findViewById(R.id.battery_view);
        milegemeter = view.findViewById(R.id.milege_meter);
        odometer = view.findViewById(R.id.odometer);

        reset = view.findViewById(R.id.reset_button);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.reset(getContext().getApplicationContext());
            }
        });



        if(batteryView==null){
            Toast.makeText(view.getContext(),"NULL",Toast.LENGTH_SHORT).show();
        }else{
            batteryView.setPercent(80);
        }

        dataViewModel = new ViewModelProvider(getActivity()).get(DataViewModel.class);


        dataViewModel.getMotorRPM().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float s) {
                Log.e("CHANGE","DATA");
                Log.e("CHANGE",s.toString());
//                batteryView.setPercent((round(s/60)));
                //motorRPMmeter.speedTo(s,0);
            }
        });

        dataViewModel.getEngineRPM().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                engineRPMmeter.speedTo(aFloat,100);
            }
        });
        dataViewModel.getEngineTorque().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                engineTorqueMeter.speedTo(aFloat,100);
            }
        });
        dataViewModel.getMotorTorque().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                motorTorqueMeter.speedTo(aFloat,100);
            }
        });
        dataViewModel.getThrottle().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                throttleMeter.speedTo(aFloat,100);
            }
        });
        dataViewModel.getSOC().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float s) {
                Log.e("SOC","CHANGE");
                Log.e("CHANGE",s.toString());
                batteryView.setPercent((round(s)));
            }
        });
        dataViewModel.getKmpl().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                milegemeter.speedTo(aFloat,100);
                //milegeText.setText(String.format("%.1f", aFloat)+"km/l");
            }
        });

        dataViewModel.getSpeed().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {speedometer.speedTo(aFloat,100);}
        });

        dataViewModel.getDistance().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                odometer.speedTo(aFloat,100);
               // distanceText.setText(String.format("%.2f", aFloat)+"km");
            }
        });
    }
}
