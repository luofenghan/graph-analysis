package com.analysis.graph.web.api.controller;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by cwc on 2017/5/4 0004.
 */
public class TestUtils {

    public static <T> T getObjectFromModel(MvcResult mvcResult, Class<T> type) {
        ModelAndView view = mvcResult.getModelAndView();
        return (T) view.getModel().get("graph");
    }
}
