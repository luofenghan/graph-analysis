package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.common.domain.dbo.DataSourceInfoExample;
import com.analysis.graph.common.repository.mapper.DataSourceInfoMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by cwc on 2017/4/22 0022.
 */
@Repository
public class DataSourceRepository {
    @Resource
    private DataSourceInfoMapper dataSourceInfoMapper;

    public Optional<DataSourceInfo> queryDataSourceInfoById(Integer id) {
        return Optional.ofNullable(dataSourceInfoMapper.selectByPrimaryKey(id));
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
        if (dataSourceInfoMapper.insert(dataSourceInfo) != 1) {
            throw new IllegalArgumentException("insertDataSource failed");
        }
        return dataSourceInfo;
    }

    @Transactional
    public DataSourceInfo updateDataSource(DataSourceInfo dataSourceInfo) {
        dataSourceInfo.setUpdatedTime(new DateTime().toDate());
        if (dataSourceInfoMapper.updateByPrimaryKeySelective(dataSourceInfo) != 1) {
            throw new IllegalArgumentException("updateDataSource failed");
        }
        return dataSourceInfo;
    }

    @Transactional
    public void deleteDataSource(Integer id) {
        if (dataSourceInfoMapper.deleteByPrimaryKey(id) != 1) {
            throw new IllegalArgumentException("deleteDataSource failed");
        }
    }
}
