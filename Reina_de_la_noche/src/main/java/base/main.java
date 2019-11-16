/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

/**
 *
 * @author esteb
 */
public class main {
    public static void main(String[] args) {
        PlanToFollow plan = new PlanToFollow();
        plan.treeGenerator();
        plan.GreedyPlan(Globals.trees, plan.getAnthillDistance(), 60);
    }
}
