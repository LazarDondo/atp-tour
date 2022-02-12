package com.silab.atptour.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.silab.atptour.entity.id.MatchId;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "matches")
@IdClass(MatchId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "first_player_id")
    private Player firstPlayer;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "second_player_id")
    private Player secondPlayer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate matchDate;

    @Column(length = 20)
    private String round;

    @Column(length = 5)
    private String result;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "winner")
    private Player winner;

    public Match(Tournament tournament, Player firstPlayer, Player secondPlayer, LocalDate matchDate, String round) {
        this.tournament = tournament;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchDate = matchDate;
        this.round = round;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.tournament);
        hash = 73 * hash + Objects.hashCode(this.firstPlayer);
        hash = 73 * hash + Objects.hashCode(this.secondPlayer);
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
        final Match other = (Match) obj;
        if (!Objects.equals(this.tournament, other.tournament)) {
            return false;
        }
        if (!Objects.equals(this.firstPlayer, other.firstPlayer)) {
            return false;
        }
        if (!Objects.equals(this.secondPlayer, other.secondPlayer)) {
            return false;
        }
        return true;
    }

}
