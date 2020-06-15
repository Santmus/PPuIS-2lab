package sample.SeachAndDeleteWindowView;

import sample.Parsers.Controller;
import sample.View.MainWindowTable;

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
