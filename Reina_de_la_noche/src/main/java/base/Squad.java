/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

/**
 *
 * @author jonac
 */
public class Squad {
    private int ammountOfMembers;//cantidad de hormigas
    private int id;//id del squad
    private float firstTime;//tiempo de la primera hormiga
    private float lastTime;//tiempo de la ultima hormiga
    private float takeOffTime;//tiempo en que el squad sale

    public Squad(int ammountOfMembers, int id, float firstTime, float lastTime) {
        this.ammountOfMembers = ammountOfMembers;
        this.id = id;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
    }

    public int getAmmountOfMembers() {
        return ammountOfMembers;
    }

    public int getId() {
        return id;
    }

    public float getFirstTime() {
        return firstTime;
    }

    public float getLastTime() {
        return lastTime;
    }

    public void setAmmountOfMembers(int ammountOfMembers) {
        this.ammountOfMembers = ammountOfMembers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstTime(float firstTime) {
        this.firstTime = firstTime;
    }

    public void setLastTime(float lastTime) {
        this.lastTime = lastTime;
    }

    public void setTakeOffTime(float takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public float getTakeOffTime() {
        return takeOffTime;
    }
    
    
    
}
