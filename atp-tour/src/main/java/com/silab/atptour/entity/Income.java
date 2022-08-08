package com.silab.atptour.entity;

import com.silab.atptour.entity.id.IncomeId;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing player's income from the tournaments
 * 
 * @author Lazar
 */
@Entity
@Table
@IdClass(IncomeId.class)
@Getter @Setter @NoArgsConstructor
public class Income {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "player_id")
    private Player player;
    
    @Version
    private long version;

    private int points;

    public Income(Tournament tournament, Player player, int points) {
        this.tournament = tournament;
        this.player = player;
        this.points = points;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.tournament);
        hash = 97 * hash + Objects.hashCode(this.player);
        hash = 97 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 97 * hash + this.points;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Income other = (Income) obj;
        if (this.version != other.version) {
            return false;
        }
        if (this.points != other.points) {
            return false;
        }
        if (!Objects.equals(this.tournament, other.tournament)) {
            return false;
        }
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        return true;
    }
    
    
}
