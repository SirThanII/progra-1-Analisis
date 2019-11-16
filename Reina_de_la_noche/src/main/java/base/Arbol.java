/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import javax.swing.Spring;

/**
 *
 * @author jonac
 */
public class Arbol {
    private final int xPos;
    private final int depthLevels;
    private final int id;
    private final int trunkSize;
    private final float growthRate;
    private final float leafSize;
    public static int ammountOfTrees=0;
    public float treeValue;
    public float treeHeight;
    public int amountOfLeaves;

    public Arbol(int xPos, int pTrunkSize, float pGrowthRate, float pLeafSize) {
        this.xPos = xPos;
        this.treeHeight = 0;
        this.depthLevels = calculateDepth(pTrunkSize, pGrowthRate, pLeafSize);
        this.trunkSize = pTrunkSize;
        this.growthRate = pGrowthRate;
        this.leafSize = pLeafSize;
        this.amountOfLeaves = (int)Math.pow(2, this.depthLevels);
        id=ammountOfTrees;
        ammountOfTrees++;
    }

    private int calculateDepth(float pTrunkSize, float pGrowthRate, float pLeafSize) {
        int depth = 0;
        while (pTrunkSize >= pLeafSize) {
            depth += 1;
            this.treeHeight += pTrunkSize;
            pTrunkSize = pTrunkSize - (pTrunkSize * pGrowthRate);
        }
        return  depth;
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
    
    public int getAmountOfLeaves() {
        return amountOfLeaves;
    }
    
    public float getHeight() {
        return treeHeight;
    }
    /*
    Creo que una clase arbol animated podria hacer la diferencia
    */

    @Override
    public String toString() {
        return "Arbol{" + "xPos=" + xPos + ", depthLevels=" + depthLevels + ", id=" + id + ", trunkSize=" + trunkSize + ", growthRate=" + growthRate + ", leafSize=" + leafSize + '}';
    }

    
}
