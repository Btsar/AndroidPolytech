package etu.polytech.gip;

public class MishapModel {

     String location;
     String description;
     String type;
     String severity;
     String time;
     String date;
     String title;
     String building;

    public MishapModel(){

    }

    public MishapModel(String location, String description, String type, String severity, String time, String date, String title, String building ){
        this.location = location;
        this.description = description;
        this.type = type;
        this.severity = severity;
        this.time = time;
        this.date = date;
        this.title = title;
        this.building = building;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSeverity() {
        return severity;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }


}
