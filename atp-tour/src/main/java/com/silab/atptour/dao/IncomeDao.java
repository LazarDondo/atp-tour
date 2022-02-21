package com.silab.atptour.dao;

import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.IncomeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface IncomeDao extends JpaRepository<Income, IncomeId>{
    public List<Income> findIncomesByTournament(Tournament tournament);
}
