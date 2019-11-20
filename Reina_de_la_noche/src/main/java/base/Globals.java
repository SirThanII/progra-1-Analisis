/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;
import java.util.ArrayList;

/**
 *
 * @author jonac
 */
public class Globals {
    public static final int MINCOUNT=0;
    public static final int ONE=1;
    public static final int PAIR=2;
    public static final int VELOCITY=5; //VELOCITY PER ANTS (M/S)
    public static final int AMOUNTOFANTS=60; //AMOUNT OF ANTS PER ITERACTION
    public static final int AMOUNTOFSQUADS=4; //AMOUNT OF ANTS PER SQUAD
    
    public static ArrayList<Arbol> trees = new ArrayList<Arbol>();
    public static final int AMOUNT_OF_TREES = 100;
    public static final int FIRST_X_POS = 100;
    public static final int LAST_X_POS = 1000;
    
    public static final double PLANNING_PERCENTAJE = 0.20;
    public static final int MS_CONVERTION = 1000;
    
    public static final int ANGLE = 25;
    //WE ARE GOING TO WORK EVERYTHING BY 1 UNIT
    
}
