package com.test.log;

public enum  MyEventType {
    DEFAULT("1","default"),
    ADD("2","add"),
    LOGIN("3","login"),
    LOGOUT("4","logout");
    private String event;
    private String name;

    private   MyEventType(String index,String name)
    {
        this.event=index;
        this.name=name;
    }

    public String getEvent() {
        return event;
    }

    public String getName() {
        return name;
    }
}
