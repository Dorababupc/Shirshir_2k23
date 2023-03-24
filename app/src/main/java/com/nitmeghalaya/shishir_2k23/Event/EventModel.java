package com.nitmeghalaya.shishir_2k23.Event;

public class EventModel {
    private String event_name;
   private String imgUrl;
   private String event_type;
   private String pdfUrl;
    public EventModel() {
    }

    public EventModel(String event_name, String imgUrl,String event_type,String pdfUrl) {
        this.event_name = event_name;
        //this.registration_count = registration_count;
        this.imgUrl = imgUrl;
        this.event_type = event_type;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getEvent_name() {
        return event_name;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }
}
