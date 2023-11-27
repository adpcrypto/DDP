package com.adp.communicationdisplay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.anastr.speedviewlib.PointerSpeedometer;

public class EngineFragment extends Fragment {


    DataViewModel dataViewModel;
    PointerSpeedometer engineRPMmeter,motorRPMmeter,enginetorquemeter,motorTorquemeter,
        fuelmeter,batteryPowerMeter,engineThrottleMeter,SOCMeter,currentmeter,voltmeter;
    public EngineFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.engine_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        engineRPMmeter = view.findViewById(R.id.engine_RPM);
        motorRPMmeter = view.findViewById(R.id.motor_RPM);
        enginetorquemeter = view.findViewById(R.id.engine_TO);
        motorTorquemeter = view.findViewById(R.id.motor_TO);
        fuelmeter = view.findViewById(R.id.engine_fuel_consump);
        batteryPowerMeter = view.findViewById(R.id.battery_power);
        engineThrottleMeter = view.findViewById(R.id.engine_throttle);
        SOCMeter = view.findViewById(R.id.battery_SOC);
        currentmeter = view.findViewById(R.id.battery_Current);
        voltmeter = view.findViewById(R.id.battery_voltage);



        dataViewModel = new ViewModelProvider(getActivity()).get(DataViewModel.class);

        dataViewModel.milliVolt.setValue(0.0F);
        dataViewModel.batteryCurrent.setValue(0.0F);

        dataViewModel.getEngineRPM().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {engineRPMmeter.speedTo(aFloat,100);}
        });
        dataViewModel.getMotorRPM().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {motorRPMmeter.speedTo(aFloat,100);}
        });
        dataViewModel.getMilliVolt().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {voltmeter.speedTo(aFloat/1000,100);}
        });
        dataViewModel.getBatteryCurrent().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {currentmeter.speedTo(aFloat,100);}
        });
        dataViewModel.getEngineTorque().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {enginetorquemeter.speedTo(aFloat,100);}
        });
        dataViewModel.getMotorTorque().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {motorTorquemeter.speedTo(aFloat,100);}
        });
        dataViewModel.getFuelConsump().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {fuelmeter.speedTo(aFloat,100);}
        });
        dataViewModel.getMilliVolt().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {batteryPowerMeter.speedTo(aFloat*dataViewModel.getBatteryCurrent().getValue()/1000,100);}
        });
        dataViewModel.getBatteryCurrent().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {batteryPowerMeter.speedTo(aFloat*dataViewModel.getMilliVolt().getValue()/1000,100);}
        });
        dataViewModel.getEngineThrottle().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {engineThrottleMeter.speedTo(aFloat,100);}
        });
        dataViewModel.getSOC().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {SOCMeter.speedTo(aFloat,100);}
        });

    }
}
