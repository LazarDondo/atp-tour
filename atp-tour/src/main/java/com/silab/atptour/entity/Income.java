package com.silab.atptour.entity;

import com.silab.atptour.entity.id.IncomeId;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Lazar
 */
@Entity
@Table
@IdClass(IncomeId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Income {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "player_id")
    private Player player;

    private int points;
}
