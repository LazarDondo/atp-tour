package com.silab.atptour.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing match between two players on the tournament
 *
 * @author Lazar
 */
@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "first_player_id")
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "second_player_id")
    private Player secondPlayer;

    @Version
    private long version;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate matchDate;

    @Column(length = 20)
    private String round;

    @Column(length = 5)
    private String result;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "winner")
    private Player winner;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "next_match", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Match nextMatch;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Match> results;

    public Match(Tournament tournament, LocalDate matchDate, String round) {
        this.tournament = tournament;
        this.matchDate = matchDate;
        this.round = round;
    }

    public Match(Tournament tournament, Player firstPlayer, LocalDate matchDate, String round) {
        this.tournament = tournament;
        this.firstPlayer = firstPlayer;
        this.matchDate = matchDate;
        this.round = round;
    }

    public Match(Tournament tournament, Player firstPlayer, Player secondPlayer, LocalDate matchDate, String round) {
        this.tournament = tournament;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchDate = matchDate;
        this.round = round;
    }

    public Match(Tournament tournament, Player firstPlayer, Player secondPlayer, LocalDate matchDate, String round, Match nextMatch) {
        this.tournament = tournament;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchDate = matchDate;
        this.round = round;
        this.nextMatch = nextMatch;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.tournament);
        hash = 17 * hash + Objects.hashCode(this.firstPlayer);
        hash = 17 * hash + Objects.hashCode(this.secondPlayer);
        hash = 17 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 17 * hash + Objects.hashCode(this.matchDate);
        hash = 17 * hash + Objects.hashCode(this.round);
        hash = 17 * hash + Objects.hashCode(this.result);
        hash = 17 * hash + Objects.hashCode(this.winner);
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
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.round, other.round)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.tournament, other.tournament)) {
            return false;
        }
        if (!Objects.equals(this.firstPlayer, other.firstPlayer)) {
            return false;
        }
        if (!Objects.equals(this.secondPlayer, other.secondPlayer)) {
            return false;
        }
        if (!Objects.equals(this.matchDate, other.matchDate)) {
            return false;
        }
        if (!Objects.equals(this.winner, other.winner)) {
            return false;
        }
        return true;
    }

}
