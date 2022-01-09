package com.silab.atptour.service.impl;

import com.silab.atptour.dao.StatisticsDao;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.exceptions.EntityNotFoundException;
import com.silab.atptour.service.StatisticsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lazar
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsDao statisticsDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    @Override
    public Statistics addStatistics(Statistics statistics) {
        logger.debug("Adding new statistics");
        return statisticsDao.save(statistics);
    }
    
    @Override
    public Statistics updateStatistics(Statistics statistics) throws EntityNotFoundException{
        logger.debug("Updating statistics");
        Optional<Statistics> optionalStatistics = statisticsDao.findStatisticsById(statistics.getId());
        if(optionalStatistics.isEmpty()){
            logger.error("Statistics with id {} doesn't exist", statistics.getId());
            throw new EntityNotFoundException("Statistics entity doesn't exist");
        }
        return statisticsDao.save(statistics);
    }
}
