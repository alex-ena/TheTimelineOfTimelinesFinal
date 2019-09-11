package enacica.alex.thetimelineoftimelines.model;

public class InsideRecycler {

    HistoryEvent singleEvent;

    public void setSingleEvent(HistoryEvent singleEvent) {
        this.singleEvent = singleEvent;
    }

    public String getInsideTitle() {
        return insideTitle;
    }

    public void setInsideTitle(String insideTitle) {
        this.insideTitle = insideTitle;
    }

    String insideTitle;

    public HistoryEvent getSingleEvent() {
        return singleEvent;
    }
}
