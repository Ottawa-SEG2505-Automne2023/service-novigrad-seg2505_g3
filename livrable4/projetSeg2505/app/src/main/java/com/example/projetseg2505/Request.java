package com.example.projetseg2505;

import java.util.*;

public class Request {

    private String id;
    private String associatedLocation;
    private String associatedUsername;
    private String type;
    private List<Requirement<String>> requiredInfos;


    public Request () {
    }

    public Request(String associatedLocation, String associatedUsername, String type, List<Requirement<String>> requiredInfos) {
        this.associatedLocation = associatedLocation;
        this.requiredInfos = requiredInfos;
        this.type = type;
        this.associatedUsername = associatedUsername;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssociatedLocation() {
        return associatedLocation;
    }

    public void setAssociatedLocation(String associatedLocation) {
        this.associatedLocation = associatedLocation;
    }

    public List<Requirement<String>> getRequiredInfos() {
        return requiredInfos;
    }

    public void setRequiredInfos(List<Requirement<String>> requiredInfos) {
        this.requiredInfos = requiredInfos;
    }
}
