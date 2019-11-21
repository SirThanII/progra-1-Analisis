/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

/**
 *
 * @author Tony1
 */
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Sketch extends JFrame {

    public Sketch() {
        super("Fractal Tree");
        setBounds(100, 100, 1400, 1000);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
        if(depth==1){
            g.setColor(Color.green);
        }
        if (depth == 0){
            g.setColor(Color.ORANGE);
            return;
        }
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth );
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth );
        g.drawLine(x1, y1, x2, y2);
        drawTree(g, x2, y2, angle - 15, depth - 1);
        drawTree(g, x2, y2, angle + 15, depth - 1);
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.ORANGE);
        drawTree(g, getWidth() / 2, getHeight(), -90, 18);
    }   
}