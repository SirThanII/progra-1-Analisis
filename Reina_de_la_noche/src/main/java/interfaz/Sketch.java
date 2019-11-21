package interfaz;

/**
 *
 * @author Tony1
 */
import base.Globals;
import base.Squad;
import common.TestTree;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import common.ITestConstants;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Sketch extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private static BufferedImage img;
    private static Graphics2D img2D;
    
    private static ArrayList<TestTree> Tree;
    private static ArrayList<Squad> squadsOrder;
    
    private static boolean animationOn;
    
    public Sketch(ArrayList<TestTree> pTree, ArrayList<Squad> pSquadsOrder) {
        Tree = pTree;
        squadsOrder = pSquadsOrder;
        animationOn = false;
    }

    private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
        if(depth==1){
            g.setColor(Color.green);
        }
        if (depth == 0){
            g.setColor(Color.BLACK);
            return;
        }
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 6);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 6);
        g.drawLine(x1, y1, x2, y2);
        drawTree(g, x2, y2, angle - 15, depth - 1);
        drawTree(g, x2, y2, angle + 15, depth - 1);
    }

    public TestTree searchTreePos(int pId) {
        for (TestTree t: Tree) {
            if (t.getID() == pId) {
                return t;
            }
        }
        return null;
    }
    
    public void animation() {
        int actualTime = 0;
        long elapseTimeStart = System.currentTimeMillis();
        long elapseTimeEnd;
        int seconds = 0;
        while (squadsOrder.size() > 0) {
            Squad actualSquad = squadsOrder.get(0);
            TestTree actualTree = searchTreePos(actualSquad.getId());
            int treePos = actualTree.getPosX();
            // Se calcula el tiempo en que la primera hormiga llega a la base del arbol
            int firstTime = actualTime + (ITestConstants.TEST_POSICION_HORMIGUERO - treePos) / Globals.VELOCITY;
            actualTime += firstTime;
            // Se calcula el tiempo en que la ultima hormiga baja del arbol
            int secondTIme = actualTime + (int)((actualTree.getTreeHeight()*2 + actualSquad.getAmmountOfMembers()) / Globals.VELOCITY);
            actualTime += secondTIme;
            // Se calcula el tiempo el tiempod de llegada al hormiguero de la primera hormiga
            int thirdTime = actualTime + (int)((ITestConstants.TEST_POSICION_HORMIGUERO - treePos) / Globals.VELOCITY);
            actualTime += thirdTime;
            System.out.println(firstTime + " " + secondTIme + " " + thirdTime);
            while (true) {
                if (seconds == firstTime) {
                    System.out.println("Primer update");
                    //actualSquad.setFirstPosX(new int[]{treePos-1, getHeight()-40});
                    //actualSquad.setLastPosX(new int[]{(int)((treePos-1) * 1.20), getHeight()-40});
                    animationOn = true;
                    validate();
                    repaint();
                    animationOn = false;
                    seconds += 1;
                }
                
                else if (seconds == secondTIme) {
                    System.out.println("second update");
                   // actualSquad.setLastPosX(new int[]{(treePos), getHeight()-40});
                   // actualSquad.setFirstPosX(new int[]{(int)(treePos * 1.20), getHeight()-40}); // line size
                    animationOn = true;
                    validate();
                    repaint();
                    animationOn = false;
                    seconds += 1;
                }
                
                else if (seconds == thirdTime) {
                    animationOn = true;
                    validate();
                    repaint();
                    animationOn = false;
                    break;
                }
                elapseTimeEnd = System.currentTimeMillis();
                if (elapseTimeEnd - elapseTimeStart >= 1000) {
                    seconds += 1;
                    elapseTimeStart = System.currentTimeMillis();
                }
            }
            squadsOrder.remove(0);
        }
        
        
        
        /*
        int timeInMillis = seconds * 1000;
        // Variables para medir el tiempo desde el inicio de la animacion
        long startTime = System.currentTimeMillis();
        long actualTime = System.currentTimeMillis();
        int currentSecond = 0;
        
        // Variables para medir el tiempo para refrescar la animacion
        long timeElapsedStart = System.currentTimeMillis();
        long timeElapsed = System.currentTimeMillis();
        
        while ( actualTime - startTime < timeInMillis ) {
            
            for (Squad eachSquad: squadsOrder) {
                if (eachSquad.getFirstTime() >= currentSecond) {
                    
                }
                
            }
            
            // Se verifica si han pasado los tres segundos para el repaint
            if (actualTime - timeElapsedStart >= Globals.REFRESH_TIME) {
                this.repaint();
            }
            // Se aumentan los segundos que se lleva en ejecucion
            if( actualTime - timeElapsed >= 1000) {
                currentSecond += 1;
            }
            timeElapsed = System.currentTimeMillis();
            timeElapsedStart = System.currentTimeMillis();
        }
        */
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int x[] = {ITestConstants.TEST_POSICION_HORMIGUERO, ITestConstants.TEST_POSICION_HORMIGUERO+50, ITestConstants.TEST_POSICION_HORMIGUERO+100, ITestConstants.TEST_POSICION_HORMIGUERO+150};
        int y[] = {getHeight()-40, getHeight()-100, getHeight()-100, getHeight()-40};
        g.drawPolygon(x, y, 4);
        
        g.drawLine(0, getHeight()-50, ITestConstants.TEST_POSICION_HORMIGUERO, getHeight()-50);
        g.drawLine(0, getHeight()-30, ITestConstants.TEST_POSICION_HORMIGUERO, getHeight()-30);
        
        for (TestTree t: Tree) {
            drawTree(g, t.getPosX(), getHeight()-50, -90, t.getLevels());
        }
        
        if(squadsOrder.isEmpty()){
            System.out.println("No Perro");
        }
        
        if(!squadsOrder.isEmpty() && !animationOn){
            System.out.println("Perro");
            Squad s = squadsOrder.get(0);
            //int[] firstPos = s.getFirstPosX();
            //int[] lastPos = s.getLastPosX();
            g.setColor(Color.RED);
            //g.drawLine(firstPos[0], firstPos[1], lastPos[0], lastPos[1]);
        }
    }
}