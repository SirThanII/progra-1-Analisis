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
    public static final int VELOCITY=1; //VELOCITY PER ANTS (1 M/S)
    public static final int AMOUNTOFANTS=60; //AMOUNT OF ANTS PER ITERACTION
    public static final int AMOUNTOFSQUADS=4; //AMOUNT OF ANTS PER SQUAD
    
    public static ArrayList<Arbol> trees = new ArrayList<Arbol>();
    public static final int amountOfTrees = 100;
    public static final int firstXPos = 100;
    public static final int lastXPos = 1000;
    
    public static final int angle = 25;
    //WE ARE GOING TO WORK EVERYTHING BY 1 UNIT
    
}
