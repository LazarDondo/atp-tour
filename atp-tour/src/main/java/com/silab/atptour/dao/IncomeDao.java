package com.silab.atptour.dao;

import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.IncomeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents repository for income data management
 *
 * @author Lazar
 */
@Repository
public interface IncomeDao extends JpaRepository<Income, IncomeId> {

    /**
     * Finds Incomes for the given tournament
     *
     * @param tournament A {@link Tournament} object for which income needs to be found
     *
     * @return A {@link List} with incomes for the given tournament
     */
    public List<Income> findIncomesByTournament(Tournament tournament);
}
