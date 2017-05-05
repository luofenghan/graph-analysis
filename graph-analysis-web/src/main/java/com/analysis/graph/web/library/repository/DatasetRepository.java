package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.DatasetExample;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.common.repository.mapper.DatasetMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/4/23 0023.
 */
@Repository
@SuppressWarnings("all")
public class DatasetRepository {
    @Resource
    private DatasourceRepository dataSourceRepository;

    @Resource
    private DatasetMapper dataSetMapper;

    @Transactional
    public Dataset insertDataset(Dataset dataSet) {
        if (StringUtils.isEmpty(dataSet.getName())) {
            Datasource dataSourceInfo = dataSourceRepository.queryDatasourceById(dataSet.getDatasourceId());
            dataSet.setName(String.format("%s_%s", dataSourceInfo.getName(), dataSet.hashCode()));
        }
        if (StringUtils.isEmpty(dataSet.getCategory())) {
            dataSet.setCategory("未分类");
        }

        DateTime now = new DateTime();
        dataSet.setCreatedTime(now.toDate());
        dataSet.setUpdatedTime(now.toDate());

        dataSetMapper.insert(dataSet);
        return dataSet;
    }

    @Transactional
    public Dataset updateDataset(Dataset dataSet) {
        dataSet.setUpdatedTime(new DateTime().toDate());
        dataSetMapper.updateByPrimaryKeySelective(dataSet);
        return dataSet;
    }

    @Transactional
    public void deleteDataset(Long id) {
        dataSetMapper.deleteByPrimaryKey(id);
    }

    public List<Dataset> queryClientDataset(Integer clientId) {
        DatasetExample example = new DatasetExample();
        example.createCriteria().andClientIdEqualTo(clientId);

        return dataSetMapper.selectByExample(example);
    }

    public Dataset queryDataset(Long dataSetId) {
        Dataset dataSet = dataSetMapper.selectByPrimaryKey(dataSetId);
        if (dataSet == null) {
            throw new IllegalArgumentException("can not find client's dataset by datasetId：" + dataSetId);
        }
        return dataSet;
    }
}
