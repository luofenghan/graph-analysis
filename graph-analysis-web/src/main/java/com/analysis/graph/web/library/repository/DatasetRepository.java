package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.constant.GlobalConstant;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.DatasetExample;
import com.analysis.graph.common.repository.mapper.DatasetMapper;
import com.google.common.base.Preconditions;
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

    private void sanitize(Dataset dataSet) {
        if (StringUtils.isEmpty(dataSet.getCategory())) {
            dataSet.setCategory(GlobalConstant.UN_CATEGORY);
        }
    }

    @Transactional
    public Dataset saveDataset(Dataset dataSet) {
        sanitize(dataSet);
        DateTime now = new DateTime();
        dataSet.setCreatedTime(now.toDate());
        dataSet.setUpdatedTime(now.toDate());
        dataSetMapper.insert(dataSet);
        return dataSet;
    }

    @Transactional
    public Dataset updateDataset(Dataset dataSet) {
        sanitize(dataSet);
        dataSet.setUpdatedTime(new DateTime().toDate());
        dataSetMapper.updateByPrimaryKeySelective(dataSet);
        return dataSet;
    }

    @Transactional
    public void removeDataset(Long id) {
        dataSetMapper.deleteByPrimaryKey(id);
    }

    public List<Dataset> listDatasetForClient(Integer clientId) {
        DatasetExample example = new DatasetExample();
        example.createCriteria().andClientIdEqualTo(clientId);

        return dataSetMapper.selectByExample(example);
    }

    public Dataset getDataset(Long dataSetId) {
        Dataset dataSet = dataSetMapper.selectByPrimaryKey(dataSetId);
        Preconditions.checkArgument(dataSet != null, "can not find dataset by datasetId：" + dataSetId);
        return dataSet;
    }
}
