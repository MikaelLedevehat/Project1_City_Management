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

    public Human sendAdvances(ArrayList<Human> lf){
        for(Human f : lf){
            if(((HumanFemale)f).receiveAdvance(this)){
                setCurrentPartner(f);
                return f;
            }
        }
        return null;
    }
    

    @Override
    protected boolean reproduce() {
        getNeeds().getNeed("reproductiveUrge").setCurrentValue(0f);
        setCurrentPartner(null);
        return true;
    }

    @Override
    protected void findMate() {
        ArrayList<Human> sortedHumanFemales = new ArrayList<>();
        addAllFemalesIntoList(sortedHumanFemales);

        sortListTowardNearest(sortedHumanFemales);

        Human f = sendAdvances(sortedHumanFemales);

        if(f != null){
            setGoal(()->reproduce(),100);
            setCurrentPartner(f);
            setDestination(f);
            
        }
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
