package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.common.domain.dbo.DataSourceInfoExample;
import com.analysis.graph.common.repository.mapper.DataSourceInfoMapper;
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
public class DataSourceRepository {
    @Resource
    private DataSourceInfoMapper dataSourceInfoMapper;

    public DataSourceInfo queryDataSourceInfoById(Integer id) {
        DataSourceInfo dataSourceInfo = dataSourceInfoMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(dataSourceInfo != null, "can not find dataSourceInfo by id: %d", id);
        return dataSourceInfo;
    }

    public List<DataSourceInfo> queryDataSourceListByClientId(Integer id) {
        DataSourceInfoExample example = new DataSourceInfoExample();
        example.createCriteria().andClientIdEqualTo(id);
        return dataSourceInfoMapper.selectByExample(example);
    }

    @Transactional
    public DataSourceInfo insertDataSource(DataSourceInfo dataSourceInfo) {
        DateTime now = new DateTime();
        dataSourceInfo.setCreatedTime(now.toDate());
        dataSourceInfo.setUpdatedTime(now.toDate());
        dataSourceInfoMapper.insert(dataSourceInfo);
        return dataSourceInfo;
    }

    @Transactional
    public DataSourceInfo updateDataSource(DataSourceInfo dataSourceInfo) {
        dataSourceInfo.setUpdatedTime(new DateTime().toDate());
        dataSourceInfoMapper.updateByPrimaryKeySelective(dataSourceInfo);
        return dataSourceInfo;
    }

    @Transactional
    public void deleteDataSource(Integer id) {
        dataSourceInfoMapper.deleteByPrimaryKey(id);
    }
}
