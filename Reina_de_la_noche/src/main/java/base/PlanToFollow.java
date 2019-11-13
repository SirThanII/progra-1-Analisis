/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.util.ArrayList;

/**
 *
 * @author jonac
 */
public class PlanToFollow {
    private final ArrayList<Squad> orden;//orden en el que saldran
    
    public PlanToFollow(){
        orden= new ArrayList<>();
    }
    
    //Aca vienen los 2 algoritmos que tenemos que hacer para que se cree el plan
    //podemos hacer 2 clases subsidiaras algo asi como PlanProb. PlanGreed
    //y que se llame desde aca y se quede con el que le de mejor relacion hojas/tiempo
}
