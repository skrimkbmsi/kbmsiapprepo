package com.kbmsi.kbmsiapps;

/**
 * Created by Rinov on 3/20/2017.
 */

public class Event {
    private String namaEvent;
    private String ID_KONTEN;
    private String namaPembuat;
    private String[] tanggal;

    public Event(String namaEvent, String ID_KONTEN, String namaPembuat, String[]tanggal ){
        this.namaEvent = namaEvent;
        this.ID_KONTEN = ID_KONTEN;
        this.namaPembuat = namaPembuat;
        this.tanggal = tanggal;
    }
    public String getJudul() {
        return namaEvent;
    }
    public String getID_KONTEN() {
        return ID_KONTEN;
    }
    public String getNamaPembuat() {
        return namaPembuat;
    }
    public String getTanggal() {
        String namaBulan [] = {" ","Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agt", "Sep", "Nov", "Dec"};

        return namaBulan[Integer.parseInt(tanggal[1])]+" "+tanggal[2]+","+" "+tanggal[0];
//        return tanggal[2]+" "+tanggal[0];
    }
}

