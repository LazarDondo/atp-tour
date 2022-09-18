package com.silab.atptour.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "match_id", referencedColumnName = "id")
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
