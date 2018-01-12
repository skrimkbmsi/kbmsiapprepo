package com.kbmsi.kbmsiapps;

/**
 * Created by Rinov on 3/20/2017.
 */

public class EventInfo {
    private String namaEvent;
    private String info;
    private String tanggal;

    public EventInfo(String namaEvent, String info, String tanggal){
        this.namaEvent = namaEvent;
        this.info = info;
        this.tanggal = tanggal;
    }
    public String getJudul() {
        return namaEvent;
    }

    public String getInfo() {
        return info;
    }

    public String getTanggal() {
        return tanggal;
    }
}

