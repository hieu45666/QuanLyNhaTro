package com.qlnt.Model;

public class NhaTro {
    public int maNhaTro;
    public int soPhong;
    public int soNguoiToiDa;
    public int giaPhong;
    public int maDay;

    public int getMaDay() {
        return maDay;
    }

    public void setMaDay(int maDay) {
        this.maDay = maDay;
    }

    public int getMaNhaTro() {
        return maNhaTro;
    }

    public void setMaNhaTro(int maNhaTro) {
        this.maNhaTro = maNhaTro;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public int getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        this.giaPhong = giaPhong;
    }

    public int getSoNguoiToiDa() {
        return soNguoiToiDa;
    }

    public void setSoNguoiToiDa(int soNguoiToiDa) {
        this.soNguoiToiDa = soNguoiToiDa;
    }

    public NhaTro(){

    }

    public NhaTro(int maNhaTro, int soPhong, int giaPhong, int soNguoiToiDa){
        this.maNhaTro = maNhaTro;
        this.soPhong = soPhong;
        this.giaPhong = giaPhong;
        this.soNguoiToiDa = soNguoiToiDa;
    }

    public NhaTro(int maNhaTro, int soPhong, int giaPhong, int soNguoiToiDa, int maDay){
        this.maNhaTro = maNhaTro;
        this.soPhong = soPhong;
        this.giaPhong = giaPhong;
        this.soNguoiToiDa = soNguoiToiDa;
        this.maDay = maDay;
    }

    public String toText(){
        return "Phòng: " + this.maNhaTro + " có " + this.soPhong + "phòng. Giá: " + this.giaPhong + ". Tối đa: " + this.soNguoiToiDa+ " người.";
    }
}
