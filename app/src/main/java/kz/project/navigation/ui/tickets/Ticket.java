package kz.project.navigation.ui.tickets;

import java.io.Serializable;

public class Ticket implements Serializable {
    String imagePath;
    String name, time, seat;
    String ceremonyType;

    public Ticket(String imagePath, String name, String time, String seat, String ceremonyType) {
        this.imagePath = imagePath;
        this.name = name;
        this.time = time;
        this.seat = seat;
        this.ceremonyType = ceremonyType;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCeremonyType() {
        return ceremonyType;
    }

    public void setCeremonyType(String ceremonyType) {
        this.ceremonyType = ceremonyType;
    }
}
