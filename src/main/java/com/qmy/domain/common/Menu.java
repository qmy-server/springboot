package com.qmy.domain.common;

public class Menu {
    private String id;
    private String title;
    private String router;
    private int authoirty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public int getAuthoirty() {
        return authoirty;
    }

    public void setAuthoirty(int authoirty) {
        this.authoirty = authoirty;
    }
}
