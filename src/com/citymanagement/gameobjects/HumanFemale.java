/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement.gameobjects;

import java.awt.Color;
import com.customgraphicinterface.utilities.Vector2;

public class HumanFemale extends Human {

    public final static Color DEFAULT_COLOR = Color.pink;
    public final static int DEFAULT_GESTATION_TIME = 200;
    public final static Color DEFAULT_GESTATION_COLOR = Color.red;

    private boolean _isPregnant = false;

    public HumanFemale(IPopulation<Human> pop, Vector2 pos, Color color) {
        super(pop, SexType.FEMALE, pos, color);
    }

    private void birth(){
        int r = (int)(Math.random() * 2);
		population.addToPopulation(HumanFactory.build().make(population));
        this.getMesh(0).setFillColor(DEFAULT_COLOR);
        this.getNeeds().removeNeed("birth");
        _isPregnant = false;
    }

    private void gestate(){
        this.getMesh(0).setFillColor(DEFAULT_GESTATION_COLOR);
        this.getNeeds().addNeed("birth", 0, DEFAULT_GESTATION_TIME, 1, null, ()->birth());
        _isPregnant = true;
    }

    public boolean receiveAdvance(HumanMale h){
        if(getCurrentPartner() != null) return false;
        if(_isPregnant) return false;

        //boolean r = Math.sqrt(h.getAttractivness() / 100f) > Math.random() + 0.5f;
        boolean r = true;
        if(r){
            setGoal(()->reproduce(),100);
            setCurrentPartner(h);
            setDestination(h);
        }
        return r;
    }

    @Override
    protected boolean reproduce() {
        getNeeds().getNeed("reproductiveUrge").setCurrentValue(0);
        setCurrentPartner(null);
        gestate();
        return true;
    }

    @Override
    protected void findMate() {
        //return null;
    }
    
}
