package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Statistics;

/**
 * Represents a service containing all the logic for statistics data management
 * 
 * @author Lazar
 */
public interface StatisticsService {

    /**
     * Saves statistics data in the database
     * 
     * @param statistics A {@link Statistics} object to be saved
     * 
     * @return Saved {@link Statistics}
     */
    public Statistics saveStatistics(Statistics statistics);

    /**
     * Finds statistics in the database based on the match to which it refers
     * 
     * @param match A {@link Match} for which statistics need to be found
     * 
     * @return A {@link Statistics} for the given {@link Match}
     */
    public Statistics findStatistics(Match match);
}
