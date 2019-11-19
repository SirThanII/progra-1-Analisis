/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import common.TestTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author jonac
 */
public class PlanToFollow {
    private final ArrayList<Squad> orden;//orden en el que saldran
    private final ArrayList<Arbol> selectedTrees;
    
    public PlanToFollow(){
        orden = new ArrayList<>();
        selectedTrees = new ArrayList<>();
    }
    
    //Aca vienen los 2 algoritmos que tenemos que hacer para que se cree el plan
    //podemos hacer 2 clases subsidiaras algo asi como PlanProb. PlanGreed
    //y que se llame desde aca y se quede con el que le de mejor relacion hojas/tiempo
    /*
    public void treeGenerator() {
        Random randomGenerator = new Random();
        int smallestXPos = Globals.LAST_X_POS;
        for (int i=0; i< Globals.AMOUNT_OF_TREES ;i++) {
            int xPos = ThreadLocalRandom.current().nextInt(Globals.FIRST_X_POS, Globals.LAST_X_POS+1);
            if (xPos < smallestXPos) {
                smallestXPos = xPos;
            }
            int trunkSize = ThreadLocalRandom.current().nextInt(10, 30);
            float leafSize = 2;
            int randomInt = randomGenerator.nextInt(9)+1;
            float growthRate = (float)(randomInt)/10;
            Arbol tree = new Arbol(xPos, trunkSize, growthRate, leafSize);
            if (tree.getAmountOfLeaves() > maxLeafAmount) {
                maxLeafAmount = tree.getAmountOfLeaves();
            }
            if (lowestHeight == 0 || tree.getHeight() < lowestHeight) {
                lowestHeight = tree.getHeight();
            }
            Globals.trees.add(tree);
            System.out.println(tree);
        }
        
        // Hormiguero
        int anthillPos = ThreadLocalRandom.current().nextInt(0, Globals.FIRST_X_POS);
        PlanToFollow.anthillDistance = smallestXPos - anthillPos;
    }
    */
    
    
    public void GreedyPlan(ArrayList<TestTree> trees, int distace, int time) {
        int maxLeafAmount = 0;
        float lowestHeight = 0;
        int closestTree = 0;
            
        for (TestTree tree: trees) {
            
            if (lowestHeight == 0 || tree.getTreeHeight() < lowestHeight) {
                lowestHeight = tree.getTreeHeight();
            }
            if (tree.getAmountOfLeaves() > maxLeafAmount) {
                maxLeafAmount = tree.getAmountOfLeaves();
            }
            if (tree.getPosX() > closestTree) {
                closestTree = tree.getPosX();
            } 
        }
        
        for (TestTree tree: trees) {
            int treeDistance = common.ITestConstants.TEST_POSICION_HORMIGUERO - tree.getPosX();
            float treeHeight = tree.getTreeHeight();
            int amountOfLeaves = tree.getAmountOfLeaves();

            float treeValue = (((float)(amountOfLeaves) / (float)(maxLeafAmount)) * 33) +
                    (((float)(closestTree) / (float)(treeDistance)) * 33) +
                    ((lowestHeight / treeHeight) * 33);
            
            tree.setScore(treeValue);
            System.out.println(tree);
        }
        
        //Sort by the score obtain
        trees.sort(Comparator.comparing(TestTree::getScore).reversed());
        
        //Creating and sending squads
        
        //for(TestTree selectedTree: trees){
        for(int amountOfTrees = 0; amountOfTrees < 10; amountOfTrees++){
            TestTree prueba = trees.get(amountOfTrees);
        
            float totalDistance = prueba.getLength() + (common.ITestConstants.TEST_POSICION_HORMIGUERO - prueba.getPosX());
            int totalAnts = Globals.AMOUNT_OF_TIME / (int)totalDistance;
            System.out.println("Actual Tree: " + amountOfTrees);
            System.out.println("Amount of leafs: " + prueba.getAmountOfLeaves());
            System.out.println("Total distance: " + totalDistance);
            System.out.println("Amout of Ants: " + totalAnts);
            Squad theFirst = new Squad(totalAnts, 1, 1, totalAnts + 1);

            prueba.setAmountOfLeaves(prueba.getAmountOfLeaves() - totalAnts);
            System.out.println("Amount of leafs after the squad recolection: " + prueba.getAmountOfLeaves());
            System.out.println("-------------------------");
        }
        
        // Se calcula la posicion del hormiguero
        /*
        int closestTree = 0;
        for (Arbol actualTree: trees) {
            if (closestTree == 0 || actualTree.getxPos() < closestTree) {
                closestTree = actualTree.getxPos();
            }
        }
        // La resta da la posicion exacta del hormiguero
        int antHillPos = closestTree - distace;
        
        // Primero se le da una calificacion a cada arbol
        for (Arbol actualTree: trees) {
            float treeHeight = actualTree.getHeight();
            float treeDistance = actualTree.getxPos() - antHillPos;
            int amountOfLeaves = actualTree.getAmountOfLeaves();
            float firstPercentage = (treeHeight/lowestHeight) * 33;
            float secondPercetage = (closestTree/treeDistance) * 33;
            float thirdPercentage = (lowestHeight/treeHeight) * 33;
            float treeValue = ((treeHeight/lowestHeight) * 33) + ((closestTree/treeDistance) * 33) + ((lowestHeight/treeHeight) * 33);
        }
        */
    }
}

