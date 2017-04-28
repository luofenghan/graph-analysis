package com.analysis.graph.datasource.jdbc;

import com.analysis.graph.datasource.DataProvider;
import com.analysis.graph.datasource.DataSourceSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cwc on 2017/4/28 0028.
 */
@RunWith(JUnit4.class)
public class JdbcDataSourceSystemTest {
    @Test
    public void testDataProvider() {
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=false");
        DataSourceSystem dataSourceSystem = DataSourceSystem.get(uri, 1);

        Map<String, String> query = new HashMap<>();
        query.put("sql", "select * from city");
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(query)) {

            Object[] row;
            while ((row = dataProvider.readLine()) != null) {
                System.out.println(Arrays.toString(row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}