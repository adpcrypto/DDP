package com.adp.communicationdisplay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {
    public MutableLiveData<String> data = new MutableLiveData<>();
    public MutableLiveData<Float> engineRPM = new MutableLiveData<>();
    public MutableLiveData<Float> motorRPM = new MutableLiveData<>();
    public MutableLiveData<Float> SOC = new MutableLiveData<>();
    public MutableLiveData<Float> milliVolt = new MutableLiveData<>();
    public MutableLiveData<Float> batteryCurrent = new MutableLiveData<>();
    public MutableLiveData<Float> engineTorque = new MutableLiveData<>();
    public MutableLiveData<Float> motorTorque = new MutableLiveData<>();
    public MutableLiveData<Float> kmpl = new MutableLiveData<>();
    public MutableLiveData<Float> throttle = new MutableLiveData<>();
    public MutableLiveData<Float> distance = new MutableLiveData<>();
    public MutableLiveData<Float> speed = new MutableLiveData<>();
    public MutableLiveData<Float> engineThrottle = new MutableLiveData<>();
    public MutableLiveData<Float> fuel_consump = new MutableLiveData<>();

    public LiveData<Float> getSOC() {
        return SOC;
    }

    public void setSOC(Float SOC) {
        this.SOC.postValue(SOC);
    }

    public LiveData<Float> getEngineThrottle() {
        return engineThrottle;
    }
    public void setEngineThrottle(Float dist) {
        this.engineThrottle.postValue(dist);
    }

    public LiveData<Float> getFuelConsump() {
        return fuel_consump;
    }
    public void setFuelConsump(Float dist) {
        this.fuel_consump.postValue(dist);
    }
    public LiveData<Float> getSpeed() {
        return speed;
    }
    public void setSpeed(Float dist) {
        this.speed.postValue(dist);
    }

    public LiveData<Float> getDistance() {
        return distance;
    }
    public void setDistance(Float dist) {
        this.distance.postValue(dist);
    }


    public MutableLiveData<Float> getMilliVolt() {return milliVolt;}
    public void setMilliVolt(Float milliVolt1) {milliVolt.postValue(milliVolt1);}

    public MutableLiveData<Float> getThrottle() {return throttle;}
    public void setThrottle(Float throttle1) {throttle.postValue(throttle1);}


    public MutableLiveData<Float> getBatteryCurrent() {return batteryCurrent;}
    public void setBatteryCurrent(Float batteryCurrent1) {batteryCurrent.postValue(batteryCurrent1);}


    public MutableLiveData<Float> getEngineTorque() {return engineTorque;}
    public void setEngineTorque(Float engineTorque1) {engineTorque.postValue(engineTorque1);}


    public MutableLiveData<Float> getMotorTorque() {return motorTorque;}
    public void setMotorTorque(Float motorTorque1) {motorTorque.postValue(motorTorque1);}


    public MutableLiveData<Float> getKmpl() {return kmpl;}
    public void setKmpl(Float kmpl1) {kmpl.postValue(kmpl1);}


    public LiveData<Float> getEngineRPM() {return engineRPM;}
    public void setEngineRPM(Float RPM1) {engineRPM.postValue(RPM1);}


    public LiveData<Float> getMotorRPM() {return motorRPM;}
    public void setMotorRPM(Float RPM1) {motorRPM.postValue(RPM1);}


    public LiveData<String> getData() {return data;}
    public void setData(String data1) {data.postValue(data1);}//Asynchronous than setvValue}



}
