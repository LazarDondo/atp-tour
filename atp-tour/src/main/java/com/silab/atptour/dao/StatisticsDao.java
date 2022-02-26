package com.silab.atptour.dao;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.entity.id.StatisticsId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface StatisticsDao extends JpaRepository<Statistics, StatisticsId> {

    public Optional<Statistics> findStatisticsByMatch(Match match);
}
