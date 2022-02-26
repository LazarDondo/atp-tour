package com.silab.atptour.exceptions;

/**
 * Checked exception to be thrown when the entity already exists
 * 
 * @author Lazar
 */
public class AtpEntityExistsException extends Exception {

    public AtpEntityExistsException(String message) {
        super(message);
    }
}
