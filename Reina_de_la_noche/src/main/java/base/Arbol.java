/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

/**
 *
 * @author jonac
 */
public class Arbol {
    private final int xPos;
    private final int depthLevels;
    private final int id;
    public static int ammountOfTrees=0;

    public Arbol(int xPos, int depthLevels) {
        this.xPos = xPos;
        this.depthLevels = depthLevels;
        id=ammountOfTrees;
        ammountOfTrees++;
    }

    public int getxPos() {
        return xPos;
    }

    public int getDepthLevels() {
        return depthLevels;
    }
    
    public int getId() {
        return id;
    }
    
    /*
    Creo que una clase arbol animated podria hacer la diferencia
    */

    
}
