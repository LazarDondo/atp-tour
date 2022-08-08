package com.silab.atptour.entity;

import com.silab.atptour.entity.id.StatisticsId;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Class representing match statistics
 * 
 * @author Lazar
 */
@Entity
@Table(name = "statistics")
@IdClass(StatisticsId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Id
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumns(value = {
        @JoinColumn(name = "tournament_id", referencedColumnName = "tournament_id"),
        @JoinColumn(name = "first_player_id", referencedColumnName = "first_player_id"),
        @JoinColumn(name = "second_player_id", referencedColumnName = "second_player_id")
    })
    private Match match;
    
    @Version
    private long version;

    private int firstPlayerPoints;

    private int secondPlayerPoints;

    private int firstPlayerAces;

    private int secondPlayerAces;

    private int firstPlayerBreakPoints;

    private int secondPlayerBreakPoints;

    private int firstPlayerFirstServesIn;

    private int secondPlayerFirstServesIn;

    private int firstPlayerSecondServesIn;

    private int secondPlayerSecondServesIn;
}
