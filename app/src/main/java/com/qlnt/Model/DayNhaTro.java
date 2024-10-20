package com.qlnt.Model;

public class DayNhaTro {
    public int maDay;
    public String tenDay;
    public String moTa;

    public int getMaDay() {
        return maDay;
    }

    public void setMaDay(int maDay) {
        this.maDay = maDay;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenDay() {
        return tenDay;
    }

    public void setTenDay(String tenDay) {
        this.tenDay = tenDay;
    }

    public DayNhaTro(){

    }

    public DayNhaTro(int maDay,String tenDay, String moTa){
        this.maDay = maDay;
        this.tenDay = tenDay;
        this.moTa = moTa;
    }
}
