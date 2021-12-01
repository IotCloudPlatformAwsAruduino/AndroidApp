package com.example.android_resapi.ui.dto;

public class DataDto {

    private int temperature;
    private int humid;

    public DataDto(int temperature, int humid){
        this.humid = humid;
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumid() {
        return humid;
    }

    public void setHumid(int humid) {
        this.humid = humid;
    }
}
