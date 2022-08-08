package com.silab.atptour.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing a tennis tournament
 * 
 * @author Lazar
 */
@Entity
@Table(name = "tournament")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor 
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Version
    private long version;

    @Column(unique = true, length = 50)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "host_country", referencedColumnName = "id")
    private Country hostCountry;

    private String tournamentType;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapKeyColumn(name = "id")
    @JsonIgnore
    private List<Match> matches;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapKeyColumn(name = "id")
    @JsonIgnore
    private List<Income> incomes;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Player> participants;

    public Tournament(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.startDate);
        hash = 89 * hash + Objects.hashCode(this.completionDate);
        hash = 89 * hash + Objects.hashCode(this.hostCountry);
        hash = 89 * hash + Objects.hashCode(this.tournamentType);
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
        final Tournament other = (Tournament) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.tournamentType, other.tournamentType)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.completionDate, other.completionDate)) {
            return false;
        }
        if (!Objects.equals(this.hostCountry, other.hostCountry)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "name: " + name;
    }

}
