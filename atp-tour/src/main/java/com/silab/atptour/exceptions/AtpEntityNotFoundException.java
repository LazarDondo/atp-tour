package com.silab.atptour.exceptions;

/**
 * Checked exception to be thrown when the entity isn't found
 * 
 * @author Lazar
 */
public class AtpEntityNotFoundException extends Exception {

    public AtpEntityNotFoundException(String message) {
        super(message);
    }

}
