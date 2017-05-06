package com.analysis.graph.web.library.domain.vo;

/**
 * Created by cwc on 2017/5/6 0006.
 */
public class DatasetVO {
    private String[] labels;
    private Object[][] data;

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
}
