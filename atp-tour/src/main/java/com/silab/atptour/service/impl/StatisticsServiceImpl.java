package com.silab.atptour.service.impl;

import com.silab.atptour.dao.StatisticsDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.service.StatisticsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Represent an implementation of the {@link StatisticsService} interface
 *
 * @author Lazar
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Statistics saveStatistics(Statistics statistics) {
        logger.debug("Saving statistics");
        return statisticsDao.save(statistics);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statistics findStatistics(Match match) {
        Optional<Statistics> foundStatistics = statisticsDao.findStatisticsByMatch(match);
        return foundStatistics.isPresent() ? foundStatistics.get() : null;
    }
}
