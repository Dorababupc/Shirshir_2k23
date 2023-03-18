package com.example.shishir_2k23.Event;

public class EventModel {
    private String event_name;
   private String imgUrl;
   private String event_type;

    public EventModel() {
    }

    public EventModel(String event_name, String imgUrl,String event_type) {
        this.event_name = event_name;
        //this.registration_count = registration_count;
        this.imgUrl = imgUrl;
        this.event_type = event_type;
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
