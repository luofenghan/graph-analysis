package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.common.domain.dbo.DatasourceExample;
import com.analysis.graph.common.repository.mapper.DatasourceMapper;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/4/22 0022.
 */
@Repository
public class DatasourceRepository {
    @Resource
    private DatasourceMapper datasourceMapper;

    public Datasource getDatasource(Integer id) {
        Datasource dataSourceInfo = datasourceMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(dataSourceInfo != null, "can not find dataSourceInfo by id: %d", id);
        return dataSourceInfo;
    }

    public List<Datasource> listDatasourceForClient(Integer id) {
        DatasourceExample example = new DatasourceExample();
        example.createCriteria().andClientIdEqualTo(id);
        return datasourceMapper.selectByExample(example);
    }

    @Transactional
    public Datasource saveDatasource(Datasource dataSourceInfo) {
        DateTime now = new DateTime();
        dataSourceInfo.setCreatedTime(now.toDate());
        dataSourceInfo.setUpdatedTime(now.toDate());
        datasourceMapper.insert(dataSourceInfo);
        return dataSourceInfo;
    }

    @Transactional
    public Datasource updateDatasource(Datasource dataSourceInfo) {
        dataSourceInfo.setUpdatedTime(new DateTime().toDate());
        datasourceMapper.updateByPrimaryKeySelective(dataSourceInfo);
        return dataSourceInfo;
    }

    @Transactional
    public void removeDataSource(Integer id) {
        datasourceMapper.deleteByPrimaryKey(id);
    }
}
