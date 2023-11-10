package kz.project.navigation.ui.events;

import java.util.List;

public class EventsItem {
    List<String> images;
    String title;
    String desc;
    boolean isRead;
    private int viewCounts;

    public EventsItem(List<String> images, String title, String desc, boolean isRead, int viewCounts) {
        this.images = images;
        this.title = title;
        this.desc = desc;
        this.isRead = isRead;
        this.viewCounts = viewCounts;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(int viewCounts) {
        this.viewCounts = viewCounts;
    }
}
