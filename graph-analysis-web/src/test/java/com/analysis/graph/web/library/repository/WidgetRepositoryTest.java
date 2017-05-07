package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Widget;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.datasource.aggregation.Field;
import com.analysis.graph.datasource.aggregation.Filter;
import com.analysis.graph.datasource.aggregation.Metric;
import com.analysis.graph.web.library.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
@Transactional
@TestComponent
public class WidgetRepositoryTest {
    @Resource
    private WidgetRepository graphRepository;

    public Widget graph() {
        Widget widget = new Widget();
        widget.setId(null);
        widget.setName("graph");
        widget.setCategory("图表");
        widget.setClientId(1);
        widget.setDatasetId(1L);
        widget.setGraphType("table");

        widget.setOptionalField(null);

        widget.setRowField(JsonUtils.toJsonString(getRowFields()));
        widget.setColumnField(JsonUtils.toJsonString(getColumnFields()));
        widget.setOptionalField(JsonUtils.toJsonString(getOptionalFields()));
        widget.setMetricField(JsonUtils.toJsonString(getMetricFields()));
        return widget;
    }

    private List<Metric> getMetricFields() {
        Metric profit = new Metric();
        profit.setColumn("profit");
        profit.setFunction(Metric.Function.SUM);
        return Arrays.asList(profit);
    }

    private List<Field> getOptionalFields() {
        Field productId = new Field();
        productId.setName("product_id");
        productId.setAlias("产品ID");
        productId.setValues(Arrays.asList("OFF-LA-4658", "FUR-FU-6238", "FUR-BO-4845", "OFF-BI-3720", "OFF-AR-5905"));

        Field productName = new Field();
        productName.setName("product_name");
        productName.setAlias("产品名称");
        productName.setValues(Arrays.asList("Hon File Folder Labels, Adjustable,",
                "Tenex Clock, Durable,",
                "Ikea 3-Shelf Cabinet, Mobile,",
                "Cardinal Binder, Clear,",
                "Sanford Canvas, Water Color,",
                "GlobeWeis Mailers, with clear poly window,",
                "GlobeWeis Mailers, with clear poly window,",
                "Konica Card Printer, Red,",
                "Elite Box Cutter, Easy Grip,",
                "Enermax Router, Erganomic,",
                "Wilson Jones Hole Reinforcements, Durable,",
                "Accos Staples, 12 Pack,",
                "Hon Steel Folding Chair, Black,",
                "Kraft Mailers, Security-Tint,",
                "Stanley Canvas, Easy-Erase,",
                "Advantus Clamps, Metal,",
                "Jiffy Peel and Seal, with clear poly window,",
                "Deflect-O Frame, Duo Pack"));

        return Arrays.asList(productId, productName);
    }

    private List<Field> getRowFields() {
        Field category = new Field();
        category.setName("category");
        category.setAlias("类别");
        category.setValues(Arrays.asList("技术", "办公用品", "家具"));
        Filter categoryFilter = new Filter();
        categoryFilter.setType(Filter.Type.NOT_EQUAL);
        categoryFilter.setValues(Arrays.asList("办公用品", "家具"));
        category.setFilter(categoryFilter);
        category.setSort(Field.Sort.ASC);

        Field marketSegment = new Field();
        marketSegment.setName("market_segment");
        marketSegment.setAlias("细分市场");
        marketSegment.setValues(Arrays.asList("公司", "家庭办公室", "消费者"));

        return Arrays.asList(category, marketSegment);
    }

    private List<Field> getColumnFields() {
        Field market = new Field();
        market.setName("market");
        market.setAlias("市场");
        market.setValues(Arrays.asList("非洲", "拉丁美洲", "美国", "欧洲", "亚太地区"));

        Field region = new Field();
        region.setName("region");
        region.setAlias("地区");

        region.setValues(Arrays.asList("北非", "北欧", "大洋洲", "东非"));

        return Arrays.asList(market, region);
    }

    @Test
    @Rollback
    public void insertGraph() throws Exception {
        Widget graph = graph();
        Widget savedGraph = graphRepository.saveWidget(graph);

        Assert.assertNotNull(savedGraph.getId());

    }

    @Test
    @Rollback
    public void updateGraph() throws Exception {
        Widget graph = graph();
        Widget savedGraph = graphRepository.saveWidget(graph);
        Assert.assertNotNull(savedGraph.getId());

        ;
        List<Field> rowFieldsList = JsonUtils.toJavaObject(savedGraph.getRowField(), new TypeReference<List<Field>>() {
        });
        Field marketSegment = rowFieldsList.remove(1);
        Filter marketFilter = new Filter();
        marketFilter.setType(Filter.Type.NOT_EQUAL);
        marketFilter.setValues(marketSegment.getValues().subList(0, marketSegment.getValues().size() / 2));
        marketSegment.setFilter(marketFilter);
        rowFieldsList.add(1, marketSegment);

        savedGraph.setRowField(JsonUtils.toJsonString(rowFieldsList));

        graphRepository.updateWidget(savedGraph);

        Widget queriedGraph = graphRepository.getWidget(savedGraph.getId());

        Assert.assertEquals(JsonUtils.toJsonString(rowFieldsList), queriedGraph.getRowField());
        Assert.assertNotNull(queriedGraph.getColumnField());
    }

    @Test
    @Rollback
    public void listGraph() throws Exception {
        Widget graph = graph();
        Widget savedGraph = graphRepository.saveWidget(graph);
        Assert.assertNotNull(savedGraph.getId());
        Assert.assertEquals(1, graphRepository.listWidgetForClient(savedGraph.getClientId()).size());
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void deleteGraph() throws Exception {
        Widget graph = graph();
        Widget savedGraph = graphRepository.saveWidget(graph);
        Assert.assertNotNull(savedGraph.getId());

        graphRepository.removeWidget(savedGraph.getId());

        graphRepository.getWidget(savedGraph.getId());
    }

}