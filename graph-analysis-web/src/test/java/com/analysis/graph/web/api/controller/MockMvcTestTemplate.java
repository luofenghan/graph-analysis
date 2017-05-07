package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.common.domain.dbo.Widget;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by cwc on 2017/5/5 0005.
 */
public class MockMvcTestTemplate {
    //    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    //    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    //    @Test
    public void saveGraph() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/graph").param("", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("graph"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    private LinkedMultiValueMap<String, String> toParams(Widget graph) {
        Map<String, List<String>> stringListHashMap = new HashMap<>();
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(JSONObject.toJSONString(graph)), Functions.toStringFunction());
        for (Map.Entry<String, String> entry : queryParam.entrySet()) {
            stringListHashMap.put(entry.getKey(), Collections.singletonList(entry.getValue()));
        }
        return new LinkedMultiValueMap<>(stringListHashMap);
    }
}
