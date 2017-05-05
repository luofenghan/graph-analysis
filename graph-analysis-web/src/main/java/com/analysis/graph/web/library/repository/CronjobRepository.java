package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Cronjob;
import com.analysis.graph.common.domain.dbo.CronjobExample;
import com.analysis.graph.common.repository.mapper.CronjobMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by cwc on 2017/4/24 0024.
 */
@Repository
public class CronjobRepository {

    @Resource
    private CronjobMapper cronjobMapper;

    @Transactional
    public Cronjob insertCronjob(Cronjob cronJob) {
        DateTime now = new DateTime();
        cronJob.setCreatedTime(now.toDate());
        cronJob.setUpdatedTime(now.toDate());
        cronjobMapper.insert(cronJob);
        return cronJob;
    }

    @Transactional
    public Cronjob updateCronjob(Cronjob cronJob) {
        cronJob.setUpdatedTime(new DateTime().toDate());
        cronjobMapper.updateByPrimaryKeySelective(cronJob);
        return cronJob;
    }

    public List<Cronjob> queryCronjobList(Integer clientId) {
        CronjobExample example = new CronjobExample();
        example.createCriteria().andClientIdEqualTo(clientId);

        return cronjobMapper.selectByExampleWithBLOBs(example);
    }

    @Transactional
    public void deleteCronjob(Long id) {
        cronjobMapper.deleteByPrimaryKey(id);

    }

    public Optional<Cronjob> queryCronjob(Long id) {
        return Optional.ofNullable(cronjobMapper.selectByPrimaryKey(id));
    }
}
