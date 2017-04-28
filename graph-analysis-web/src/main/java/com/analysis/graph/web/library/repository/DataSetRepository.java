package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.DataSet;
import com.analysis.graph.common.domain.dbo.DataSetExample;
import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.common.repository.mapper.DataSetMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by cwc on 2017/4/23 0023.
 */
@Repository
public class DataSetRepository {
    @Resource
    private DataSetMapper dataSetMapper;

    @Resource
    private DataSourceRepository dataSourceRepository;

    @Transactional
    public DataSet insertDataSet(DataSet dataSet) {
        if (StringUtils.isEmpty(dataSet.getDataSetName())) {
            Optional<DataSourceInfo> dataSourceInfoOptional = dataSourceRepository.queryDataSourceInfoById(dataSet.getDataSourceId());
            if (!dataSourceInfoOptional.isPresent()) {
                throw new IllegalArgumentException("dataSource not existed by datasourceId " + dataSet.getDataSourceId());
            }
            DataSourceInfo dataSourceInfo = dataSourceInfoOptional.get();
            dataSet.setDataSetName(String.format("%s_%s", dataSourceInfo.getName(), dataSet.hashCode()));
        }
        if (StringUtils.isEmpty(dataSet.getCategoryName())) {
            dataSet.setCategoryName("未分类");
        }

        DateTime now = new DateTime();
        dataSet.setCreatedTime(now.toDate());
        dataSet.setUpdatedTime(now.toDate());

        dataSetMapper.insert(dataSet);
        return dataSet;
    }

    @Transactional
    public DataSet updateDataSet(DataSet dataSet) {
        dataSet.setUpdatedTime(new DateTime().toDate());
        dataSetMapper.updateByPrimaryKeySelective(dataSet);
        return dataSet;
    }

    @Transactional
    public void deleteDataSet(Long id) {
        dataSetMapper.deleteByPrimaryKey(id);
    }

    public List<DataSet> getDataSetsOfClient(Integer id) {
        DataSetExample example = new DataSetExample();
        example.createCriteria().andClientIdEqualTo(id);

        return dataSetMapper.selectByExample(example);
    }
}
