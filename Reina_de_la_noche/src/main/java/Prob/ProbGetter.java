/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prob;

import base.Globals;
import base.Squad;
import common.ITestConstants;
import common.TestTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jonac
 */
public class ProbGetter {
    private static final int MAXPROB=100;
    private static final int MINPROB=1;
    private static final Random RAND = new Random();
    private static final int MIL=1000;
    private final ArrayList<TestTree> arboles;//todos los arboles
    private final ArrayList<TestTree> chosenTrees=new ArrayList<>();
    private final ArrayList<Squad> squads=new ArrayList<>();
    private final HashMap<Integer,ArrayList<TestTree>> Groups=new HashMap<>();
    private final LinkedHashMap<Integer,Double> PorcentajeGroups=new LinkedHashMap<>();
    private String FileToCSV;
    
    public ProbGetter(ArrayList<TestTree> parboles){
        arboles=parboles;   
    }

    public ArrayList<Squad> getSquads() {
        return squads;
    }

    public ArrayList<TestTree> getChosenTrees() {
        return chosenTrees;
    }
    
    public void asssingScore(TestTree arbol){
        int distanciaX=ITestConstants.TEST_POSICION_HORMIGUERO-arbol.getPosX();//distancia X
        arbol.setScore(arbol.getAmountOfLeaves()/(arbol.getTreeHeight()+distanciaX));
    }
    
    public void sortByScore(){
        Comparator sorter = (Comparator<TestTree>) (TestTree o1, TestTree o2) -> o1.getScore().compareTo(o2.getScore());
        Collections.sort(arboles,sorter.reversed());
        int GroupCounter= MINPROB;
        Groups.put(GroupCounter, new ArrayList<>());
        Float lastScore=arboles.get(MINPROB).getScore();
        int treeID=MINPROB;
        for (TestTree arbole : arboles) {
            if(lastScore-arbole.getScore()>0.1){//si la diferencia llega a ser cierta entonces
                //crea un nuevo grupo
                lastScore=arbole.getScore();
                GroupCounter++;
                Groups.put(GroupCounter, new ArrayList<>());
            }
            arbole.setID(treeID);
            ArrayList<TestTree> temp= Groups.get(GroupCounter);
            temp.add(arbole);
            Groups.put(GroupCounter, temp);
            treeID++;
        }
        
    }
    
    public void calRoute(int secs){
        long miliSecs=(secs*MIL);
        long timeStart = System.currentTimeMillis();
        double remainingTime= (miliSecs*0.2);
        System.out.println("remainintime:"+remainingTime);
        //long timeStart = timetaker;
        
        arboles.forEach((arbole) -> {
            asssingScore(arbole);
        });//asignar los scores
        sortByScore();//ordenar de orden descendente (menor a mayor)
        int memberGroups=Groups.size();
        int memberDivider=(memberGroups/2) +1;
        int MaxMemberGroups=memberGroups;
        double coso = (double)memberDivider/(double)memberGroups;
        double repCap=((double)MAXPROB) *coso;
        int groupCounter=MINPROB;
        PorcentajeGroups.put(groupCounter,repCap);
        while(MaxMemberGroups>2){
            PorcentajeGroups.put(groupCounter,repCap);
            groupCounter++;
            repCap+= ((double)MAXPROB-repCap) *( (double)memberDivider/(double)memberGroups);
            MaxMemberGroups--;
        }
        PorcentajeGroups.put(groupCounter, (Double.valueOf((double)MAXPROB)));//cuando ya llego al maximo porcentaje
        //de representacion
       // System.out.println(System.currentTimeMillis());
        int rand;
        while((System.currentTimeMillis()-timeStart)<remainingTime&&!PorcentajeGroups.isEmpty()){
                //mientras pasa
            //el tiempo va distribuyendo
            rand=RAND.nextInt(100)+1;
            int insertado=insertFromGroup(rand);
            if(-1!=insertado){//eliminar un key donde ya use todos los arboles
                PorcentajeGroups.remove(insertado);
            }
            //remainingTime-=System.currentTimeMillis();
        }
        //System.out.println(System.currentTimeMillis());
        remainingTime-=(System.currentTimeMillis()-timeStart);
        repartirSquads(remainingTime/1000,miliSecs);
        //sort by groups
    }
    
    public void repartirSquads(double remainingTime,long timeLimit){
         //Creating and sending 
        double antsTimeLimit= (double)timeLimit*0.8;
        timeLimit/=1000;
        
        System.out.println("-------------------------------");
        System.out.println("    Time: " + timeLimit);
        System.out.println("    Planning time: " + remainingTime);
        System.out.println("    Harvest time: " +antsTimeLimit/1000 );
        System.out.println("-------------------------------");
        double actualTime = 0;
        int actualObject = 0;
        int totalleafs=0;
        //antsTimeLimit*=1000;
        for(TestTree selectedTree:chosenTrees){
            boolean forceHarvest=false;
            
            double totalDistance = (selectedTree.getLength() + (common.ITestConstants.TEST_POSICION_HORMIGUERO - selectedTree.getPosX())) * 2;
            double timePerAnt = (totalDistance / Globals.VELOCITY);
            int totalAnts;
            
            if(totalDistance < selectedTree.getAmountOfLeaves()){
                totalAnts = (int)totalDistance;
            }
            else{
                totalAnts = selectedTree.getAmountOfLeaves();
            }
            
            double timeSpend = timePerAnt * totalAnts;
            
            
            if(actualTime + timeSpend > antsTimeLimit){
                timeSpend = antsTimeLimit - actualTime;
                totalAnts = (int)timeSpend / (int)timePerAnt;
                forceHarvest = true;
                System.out.println("outtabounds");
            }     
            System.out.println("Actual Tree: " + chosenTrees.get(actualObject).getID());
            System.out.println("Amount of leafs: " + selectedTree.getAmountOfLeaves());
            System.out.println("Total distance: " + totalDistance);
            System.out.println("Amout of Ants: " + totalAnts);
            totalleafs+=totalAnts;

            Squad actualSquad  = new Squad(totalAnts, (int)chosenTrees.get(actualObject).getID(), actualTime, actualTime + timeSpend);
            actualTime += timeSpend;
            System.out.println("Harvest Duration: " + actualTime);
            
            selectedTree.setAmountOfLeaves(selectedTree.getAmountOfLeaves() - totalAnts);
            System.out.println("Amount of leafs after the squad recolection: " + selectedTree.getAmountOfLeaves());
            System.out.println("-------------------------------");
            actualObject++;
            
            
            if(forceHarvest){
                break;
            }
            
            //selectedTrees.add(selectedTree);
        }
        System.out.println("final ammount: "+ totalleafs);
    }
    
    public void printOrden(){
        
    }
    
    public int insertFromGroup(int groupNum){
        int kkey=0;
        for (Map.Entry<Integer, Double> entry : PorcentajeGroups.entrySet()) {
            if(entry.getValue()>=groupNum){
                kkey=entry.getKey();
                break;
            } 
            kkey=entry.getKey();
        }
        if(!Groups.get(kkey).isEmpty()){
            TestTree tt=Groups.get(kkey).remove(0);
            chosenTrees.add(tt);   
            return -1;
        }
        return kkey;
    }
    
    
    
}
