package sample.View;

import sample.Parsers.Controller;

public class SearchWindowTableGroup extends MainWindowTable {
    private Controller controller;

    public SearchWindowTableGroup(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void updateCurrentPage() {
        setCurrentPage(controller.updateSearchWindowTable(getPageNumber(), getRecordsOnPageCount()));
    }
}
