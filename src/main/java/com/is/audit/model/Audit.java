package com.is.audit.model;


import java.util.List;

public abstract class Audit {

    private String event_ts;
    private String username;
    private List<String> user_roles;
    private String action;

    public String getEvent_ts() {
        return event_ts;
    }

    public void setEvent_ts(String event_ts) {
        this.event_ts = event_ts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(List<String> user_roles) {
        this.user_roles = user_roles;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
