package enacica.alex.thetimelineoftimelines.model;

import java.util.ArrayList;

public class MainRecycler {
    String title;
    int year;
    ArrayList<InsideRecycler> eventsArrayList;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<InsideRecycler> getEventsArrayList() {
        return eventsArrayList;
    }

    public void setEventsArrayList(ArrayList<InsideRecycler> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
    }

    public static class setTitle extends MainRecycler {
        public setTitle(String someString) {
            super();
            title = someString;
        }
    }
}
