package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Statistics;
/**
 *
 * @author Lazar
 */
public interface StatisticsService {

    public Statistics saveStatistics(Statistics statistics);
    
    public Statistics findStatistics(Match match);
}
