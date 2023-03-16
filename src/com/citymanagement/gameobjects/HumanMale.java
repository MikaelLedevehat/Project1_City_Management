package com.citymanagement.gameobjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import com.customgraphicinterface.utilities.Vector2;

public class HumanMale extends Human {
    public final static float DEFAULT_ATTARCTIVNESS = 10f;
    public final static Color DEFAULT_COLOR = Color.blue;


    private float _attractivness = 10;

    public float getAttractivness(){
        return _attractivness;
    }

    public HumanMale(HumanPopulation pop, Vector2 pos, Color color, float attractivness) {
        super(pop, SexType.MALE,pos, color);

        this._attractivness = attractivness;
    }

    public Vector2 sendAdvances(ArrayList<Human> lf){
        for(Human f : lf){
            System.out.println("coucou" + f);
            if(((HumanFemale)f).receiveAdvance(this)){
                System.out.println("yop");
                setCurrentPartner(f);
                return f.getTransform().getPos();
            }
        }
        return null;
    }
    

    @Override
    protected boolean reproduce() {
        getNeeds().setReproductiveUrge(0);
        return true;
    }

    @Override
    protected Vector2 findMate() {
        ArrayList<Human> sortedHumanFemales = new ArrayList<>();
		
        addAllFemalesIntoList(sortedHumanFemales);

        sortListTowardNearest(sortedHumanFemales);

        return sendAdvances(sortedHumanFemales);
    }

    private void sortListTowardNearest(ArrayList<Human> sortedHumanFemales) {

        Comparator<Human> c = new Comparator<Human>() {

            @Override
            public int compare(Human o1, Human o2) {
                if(Vector2.dist(getTransform().getPos(), o1.getTransform().getPos()) > Vector2.dist(getTransform().getPos(), o2.getTransform().getPos())){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            
        };
        sortedHumanFemales.sort(c);
    }

    private void addAllFemalesIntoList(ArrayList<Human> sortedHumanFemales) {
        for(Human h : population.getPopulationList()){
			if(h == null) continue;
			if(h.getSex() != getSex()){
				sortedHumanFemales.add(h);
			}
		}
    }
}
