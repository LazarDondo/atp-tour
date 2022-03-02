package com.silab.atptour.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing composite primary key for the Income class
 * 
 * @author Lazar
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class IncomeId implements Serializable {

    private long tournament;
    private long player;
}
