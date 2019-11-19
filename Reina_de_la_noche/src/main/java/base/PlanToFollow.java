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
    
    
    public void GreedyPlan(ArrayList<TestTree> trees, int distace, float time) {
        long timeStart = System.currentTimeMillis();
        int maxLeafAmount = 0;
        float lowestHeight = 0;
        int closestTree = 0;
        
        // Tiempo limite de las hormigas
        float planTimeLimit = (float) (time * 0.20);
        float antsTimeLimit = time - planTimeLimit;
        // Tiempo limite del plan en milisegundos
        planTimeLimit *= 1000;
        antsTimeLimit *= 1000; // De uno en uno, todos los arboles
//        double timePerSquad = antsTimeLimit * 0.20 * 1000; //20% por squad del tiempo para recoleccion, solo 4 squads
        
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
//            System.out.println(tree);
        }
        
        //Sort by the score obtain
        trees.sort(Comparator.comparing(TestTree::getScore).reversed());
        
        //Creating and sending squads
        System.out.println("-------------------------------");
        System.out.println("    Time: " + time);
        System.out.println("    Planning time: " + planTimeLimit);
        System.out.println("    Harvest time: " + antsTimeLimit);
        System.out.println("-------------------------------");
        
        float actualTime = 0;
        int actualTree = 1;
        for(TestTree selectedTree: trees){
            long timeDiference = System.currentTimeMillis() - timeStart;
//            System.out.println("Diferencia: " + timeDiference);
            if ((float)timeDiference >= planTimeLimit) {
                System.out.println(" ---- !!! El plan alcanzo su tiempo limite !!! ---- ");
                break;
            }
            float totalDistance = selectedTree.getLength() + (common.ITestConstants.TEST_POSICION_HORMIGUERO - selectedTree.getPosX());
            int totalAnts = (int)antsTimeLimit / ((int)totalDistance / Globals.VELOCITY);
            if(selectedTree.getAmountOfLeaves() < totalAnts){
                totalAnts = selectedTree.getAmountOfLeaves();
            }

            System.out.println("Actual Tree: " + actualTree);
            System.out.println("Amount of leafs: " + selectedTree.getAmountOfLeaves());
            System.out.println("Total distance: " + totalDistance);
            System.out.println("Amout of Ants: " + totalAnts);

            Squad actualSquad  = new Squad(totalAnts, 1, actualTime, actualTime + totalAnts);
            actualTime += totalAnts;
            
            selectedTree.setAmountOfLeaves(selectedTree.getAmountOfLeaves() - totalAnts);
            System.out.println("Amount of leafs after the squad recolection: " + selectedTree.getAmountOfLeaves());
            System.out.println("-------------------------");
            actualTree++;
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

