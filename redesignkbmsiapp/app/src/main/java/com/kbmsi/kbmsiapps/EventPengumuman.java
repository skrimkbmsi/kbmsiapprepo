package com.kbmsi.kbmsiapps;

/**
 * Created by Rinov on 3/20/2017.
 */

public class EventPengumuman {
    private String namaEvent, info, tanggal;


    public EventPengumuman(String judul, String info, String tanggal ){
        this.namaEvent = judul;
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

