/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import common.TestGenerator;
import common.TestTree;
import java.util.ArrayList;

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
            plan.GreedyPlan(tree, 15, 60);
            actualTest++;
        }
    }
}
