package com.silab.atptour.dao;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.entity.id.StatisticsId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents repository for statistics data management
 * 
 * @author Lazar
 */
@Repository
public interface StatisticsDao extends JpaRepository<Statistics, StatisticsId> {

    /**
     * Finds statistics by match
     * 
     * @param match A {@link Match} for which statistics need to be found
     * 
     * @return An {Optional} statistics
     * 
     */
    public Optional<Statistics> findStatisticsByMatch(Match match);
}
