/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import common.TestGenerator;
import common.TestTree;
import interfaz.Sketch;
import java.util.ArrayList;
import processing.core.PApplet;

/**
 *
 * @author esteb
 */
public class main {
    public static void main(String[] args) {
        TestGenerator generator = new TestGenerator();
        ArrayList<TestTree>[] trees = generator.getTests();
        PlanToFollow plan = new PlanToFollow();
        int actualTest = 1;
        for(ArrayList<TestTree> tree: trees) {
            System.out.println("########################## PRUEBA " + actualTest + " ##################################");
            // EL tiempo se trabaja en seconds
            plan.choosePlan(tree, 15, 300);
            actualTest++;
        }
        
//        new Sketch().setVisible(true);
    }
}
