package com.silab.atptour.model;

/**
 * Model containing all pagination constants for controllers
 * 
 * @author Lazar
 */
public class PaginationModel {

    public static final int PLAYER_PAGE = 0;
    public static final int PLAYER_SIZE = 10;
    public static final String PLAYER_SORT_COLUMN = "rank";
    
    public static final int TOURNAMENT_PAGE = 0;
    public static final int TOURNAMENT_SIZE = 10;
    public static final String TOURNAMENT_SORT_COLUMN = "name";
    
    public static final int MATCHES_PAGE = 0;
    public static final int MATCHES_SIZE = 10;
    public static final String MATCHES_SORT_COLUMN = "matchDate";
}
