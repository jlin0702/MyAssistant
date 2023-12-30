package com.example.springbootfullstack.model;

public class NavLink {
    private String label;
    private String url;

    public NavLink(String label, String url) {
        this.label = label;
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }
}
