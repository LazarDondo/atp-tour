package com.silab.atptour.model;

import com.silab.atptour.exceptions.AtpException;

/**
 * Model containing all the constants used in the app
 * 
 * @author Lazar
 */
public class AtpModel {
    public static final String GRAND_SLAM= "Grand Slam";

    public static final String GRAND_SLAM_EIGHTS_FINALS = "eights-finals";
    public static final String GRAND_SLAM_QUATER_FINALS = "quater-finals";
    public static final String GRAND_SLAM_SEMI_FINALS = "semi-finals";
    public static final String GRAND_SLAM_FINALS = "finals";
    
    public static final int GRAND_SLAM_EIGHTS_FINALS_POINTS = 200;
    public static final int GRAND_SLAM_QUATER_FINALS_POINTS = 400;
    public static final int GRAND_SLAM_SEMI_FINALS_POINTS = 600;
    public static final int GRAND_SLAM_FINALS_POINTS = 1000;

    /**
     * Determines next round
     *
     * @param currentRound A string representing current round
     *
     * @return next round
     */
    public static String getNextRound(String currentRound) {
        switch (currentRound) {
            case GRAND_SLAM_EIGHTS_FINALS:
                return GRAND_SLAM_QUATER_FINALS;
            case GRAND_SLAM_QUATER_FINALS:
                return GRAND_SLAM_SEMI_FINALS;
            case GRAND_SLAM_SEMI_FINALS:
                return GRAND_SLAM_FINALS;
            default:
                throw new AtpException("Invalid round");
        }
    }
}
