package com.silab.atptour.entity.id;

import com.silab.atptour.entity.id.MatchId;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing composite primary key for Statistics class
 * 
 * @author Lazar
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class StatisticsId implements Serializable {

    private long id;

    private MatchId match;

}
