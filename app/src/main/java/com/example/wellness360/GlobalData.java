package com.example.wellness360;
import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData instance;
    private List<String> clickedButtonDataList;

    private GlobalData() {
        clickedButtonDataList = new ArrayList<>();
    }

    public static synchronized GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public List<String> getClickedButtonDataList() {
        return clickedButtonDataList;    }

    public void addClickedButtonData(String data) {
        clickedButtonDataList.add(data);
}

}
