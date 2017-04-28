package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.CronJob;
import com.analysis.graph.common.domain.dbo.CronJobExample;
import com.analysis.graph.common.repository.mapper.CronJobMapper;
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
public class CronJobRepository {

    @Resource
    private CronJobMapper cronJobMapper;

    @Transactional
    public CronJob insertCronJob(CronJob cronJob) {
        DateTime now = new DateTime();
        cronJob.setCreatedTime(now.toDate());
        cronJob.setUpdatedTime(now.toDate());
        cronJobMapper.insert(cronJob);
        return cronJob;
    }

    @Transactional
    public CronJob updateCronJob(CronJob cronJob) {
        cronJob.setUpdatedTime(new DateTime().toDate());
        cronJobMapper.updateByPrimaryKeySelective(cronJob);
        return cronJob;
    }

    public List<CronJob> queryCronJobList(Integer clientId) {
        CronJobExample example = new CronJobExample();
        example.createCriteria().andClientIdEqualTo(clientId);

        return cronJobMapper.selectByExampleWithBLOBs(example);
    }

    @Transactional
    public void deleteCronJob(Long id) {
        cronJobMapper.deleteByPrimaryKey(id);

    }

    public Optional<CronJob> queryCronJob(Long id) {
        return Optional.ofNullable(cronJobMapper.selectByPrimaryKey(id));
    }
}
