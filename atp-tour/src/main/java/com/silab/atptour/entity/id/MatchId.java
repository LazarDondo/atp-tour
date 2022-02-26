package com.silab.atptour.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Lazar
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class MatchId implements Serializable {

    private long tournament;
    private long firstPlayer;
    private long secondPlayer;
}
