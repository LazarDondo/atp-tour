package com.silab.atptour.service;

import com.silab.atptour.entity.Statistics;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
/**
 *
 * @author Lazar
 */
public interface StatisticsService {

    public Statistics addStatistics(Statistics statistics);
    
    public Statistics updateStatistics(Statistics statistics) throws AtpEntityNotFoundException;
}
