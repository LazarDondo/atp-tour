package com.silab.atptour.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing a professional tennis player
 *
 * @author Lazar
 */
@Entity
@Table(name = "player")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "birth_country", referencedColumnName = "id")
    private Country birthCountry;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private int currentPoints;

    private int livePoints;

    @Column(name = "player_rank")
    private int rank;

    @OneToMany(mappedBy = "firstPlayer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "id")
    @JsonIgnore
    private List<Match> matches;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapKeyColumn(name = "id")
    @JsonIgnore
    private List<Income> incomes;

    public Player(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + rank + ")";
    }

}
