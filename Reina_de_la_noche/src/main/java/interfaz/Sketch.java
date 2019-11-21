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
import processing.core.PApplet;

public class Sketch extends PApplet {

    float theta;   

    @Override
    public void settings() {
        size(640, 360);
    }

    @Override
    public void draw() {
        background(0);
        frameRate(30);
        stroke(255);
        // Let's pick an angle 0 to 90 degrees based on the mouse position
        float a = (320 / (float) width) * 90f;
        // Convert it to radians
        theta = radians(a);
        // Start the tree from the bottom of the screen
        translate(width/2,height);
        // Draw a line 120 pixels
        line(0,0,0,-120);
        // Move to the end of that line
        translate(0,-120);
        // Start the recursive branching!
        branch(120);
    }

    void branch(float h) {
        // Each branch will be 2/3rds the size of the previous one
        h *= 0.66;

        // All recursive functions must have an exit condition!!!!
        // Here, ours is when the length of the branch is 2 pixels or less
        if (h > 2) {
          pushMatrix();    // Save the current state of transformation (i.e. where are we now)
          rotate(theta);   // Rotate by theta
          line(0, 0, 0, -h);  // Draw the branch
          translate(0, -h); // Move to the end of the branch
          branch(h);       // Ok, now call myself to draw two new branches!!
          popMatrix();     // Whenever we get back here, we "pop" in order to restore the previous matrix state

          // Repeat the same thing, only branch off to the "left" this time!
          pushMatrix();
          rotate(-theta);
          line(0, 0, 0, -h);
          translate(0, -h);
          branch(h);
          popMatrix();
        }
    }
}
