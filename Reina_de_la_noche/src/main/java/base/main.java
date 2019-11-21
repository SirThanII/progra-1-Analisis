package base;

import common.TestGenerator;
import common.TestTree;
import interfaz.Sketch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import processing.core.PApplet;

/**
 *
 * @author esteb
 */
public class main {
    public static void main(String[] args) {
        TestGenerator generator = new TestGenerator();
        ArrayList<TestTree>[] trees = generator.getTests();
        int actualTest = 1;
        
        System.out.println("########################## PRUEBA " + actualTest + " ##################################");
        PlanToFollow plan = new PlanToFollow();
        // EL tiempo se trabaja en seconds
        plan.GreedyPlan(trees[1], 15, 300);
        actualTest++;
        ArrayList<TestTree> selecTree = plan.getSelectedTrees();
        ArrayList<Squad> squads = plan.getOrden();
        final JFrame frame = new JFrame("L Systems - Tree Fractal");
        Sketch s = new Sketch(selecTree, squads);
        frame.setContentPane(s);
        //frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.setVisible(true);
        s.animation();
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.dispose();

        
        
        
        //new Sketch().setVisible(true);
    }
}