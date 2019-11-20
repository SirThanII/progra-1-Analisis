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
        float planTimeLimit = (float) (time * Globals.PLANNING_PERCENTAJE);
        float antsTimeLimit = time - planTimeLimit;
        
        // Tiempo limite del plan en milisegundos
        planTimeLimit *= Globals.MS_CONVERTION;
        antsTimeLimit *= Globals.MS_CONVERTION;
        double timePerSquad = antsTimeLimit * Globals.PLANNING_PERCENTAJE; //20% por squad del tiempo para recoleccion, solo 4 squads
        
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
        int actualObject = 1;
        for(TestTree selectedTree: trees){
            long timeDiference = System.currentTimeMillis() - timeStart;
            float timeLeft = 0, antsLeft = 0;
            boolean wastes = false, forceHarvest = false;
            if (actualTime >= antsTimeLimit) {
                System.out.println(" ---- !!! El plan alcanzo su tiempo limite !!! ---- ");
                break;
            }            
         
            System.out.println("Diferencia: " + timeDiference);
            if ((float)timeDiference >= planTimeLimit) {
                System.out.println(" ---- !!! El plan alcanzo su tiempo limite (real)!!! ---- ");
                break;
            }
            
            if(actualTime + timePerSquad > antsTimeLimit){
                timePerSquad = antsTimeLimit - actualTime;
                System.out.println("Revisar: " + timePerSquad);
                forceHarvest = true;
            }
            
            float totalDistance = selectedTree.getLength() + (common.ITestConstants.TEST_POSICION_HORMIGUERO - selectedTree.getPosX());
            float timeSpend = (totalDistance / Globals.VELOCITY);
            int totalAnts = (int)timePerSquad / (int)timeSpend;
            
            if(selectedTree.getAmountOfLeaves() < totalAnts){
                antsLeft = totalAnts - selectedTree.getAmountOfLeaves();
                timeLeft = totalAnts * timeSpend - selectedTree.getAmountOfLeaves() * timeSpend;
                totalAnts = selectedTree.getAmountOfLeaves();
                wastes = true;
            }

            System.out.println("Actual Tree: " + actualObject);
            System.out.println("Amount of leafs: " + selectedTree.getAmountOfLeaves());
            System.out.println("Total distance: " + totalDistance);
            System.out.println("Amout of Ants: " + totalAnts);
            
            if(wastes){
                System.out.println("Time left from the asignation: " + timeLeft);
                System.out.println("Ants left from the asignation: " + antsLeft);
            }

            Squad actualSquad  = new Squad(totalAnts, actualObject, actualTime, actualTime + (totalAnts * timeSpend));
            actualTime += totalAnts * timeSpend;
            System.out.println("Harvest Duration: " + actualTime);
            
            selectedTree.setAmountOfLeaves(selectedTree.getAmountOfLeaves() - totalAnts);
            System.out.println("Amount of leafs after the squad recolection: " + selectedTree.getAmountOfLeaves());
            System.out.println("-------------------------------");
            actualObject++;
            if(forceHarvest){
                break;
            }
        }
    }
}

