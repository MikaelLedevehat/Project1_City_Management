package com.citymanagement.gameobjects;

import java.util.concurrent.ConcurrentHashMap;

import com.citymanagement.gameobjects.Needs.Need.Command;

public class Needs{

    public class Need{

        @FunctionalInterface
        public interface Command {
            void execute();
        }

        private String _name;
        private float _currentValue = 0;
        private float _maxCap;
        private float _growRate;
        private Command _actionFulfilNeed;
        private Command _actionNeedMaxCapReached;

        public Need(String name, float currentValue,float needMaxCap,float needGrowRate,Command actionFulfilNeed,Command actionNeedMaxCapReached ){
            this._growRate= needGrowRate;
            this._maxCap = needMaxCap;
            this._actionFulfilNeed = actionFulfilNeed;
            this._actionNeedMaxCapReached = actionNeedMaxCapReached;
            this._currentValue = currentValue;
            this._name = name;
        }

        public String getName(){
            return _name;
        }
        public void setName(String n){
            _name = n;
        }
        public float getCurrentValue(){
            return _currentValue;
        }
        public float getMaxCap(){
            return _maxCap;
        }
        public float getGrowRate(){
            return _growRate;
        }
        public Command getActionFulfilNeed(){
            return _actionFulfilNeed;
        }
        public Command getActionNeedMaxCapReached(){
            return _actionNeedMaxCapReached;
        }
        public void setCurrentValue(float v){
            if(v < 0) _currentValue = 0;
            else if(v > _maxCap) _currentValue = _maxCap;
            else _currentValue = v;
        }
        public void setMaxCap(float v){
            if(v < 0) _maxCap = 0;
            else _maxCap = v;
        }
        public void setGrowRate(float v){
            if(v < 0) _growRate = 0;
            _growRate = v;
        }
        public void setActionFulfilNeed(Command c){
            //if(c == null) throw new NullPointerException("The command to fulfil this need can't be null");
            _actionFulfilNeed = c;
        }

        public void setActionNeedMaxCapReached(Command c){
            _actionNeedMaxCapReached = c;
        }
        
        public boolean executeActionToFulfillNeed(){
            if(this.getActionFulfilNeed() == null) return false;
            else{
                this.getActionFulfilNeed().execute();
                return true;
            }
        }
    }

    private final ConcurrentHashMap<String, Need> _needList;

    public Needs(){
        _needList = new ConcurrentHashMap<>();
    }

    public boolean addNeed(String name, float currVal, float maxCap, float growRate, Command actionFulfil, Command actionMaxCap){
        if(_needList.get(name)== null){
            _needList.put(name, new Need(name, currVal, maxCap, growRate, actionFulfil, actionMaxCap));
            return true;
        }else{
            return false;
        }
    }

    public boolean removeNeed(String name){
        Need n = _needList.remove(name);
        if(n == null) return false;
        else return true;
    }

    public Need getNeed(String name){
        Need n = _needList.get(name);
        if(n== null) throw new NullPointerException("This need does't exist : " + name);
        return n;
    }

    public Need getMostPressingNeed(float threshold){
        Need mpn = null;
        for(Need n : _needList.values()){
            if(n.getCurrentValue()< threshold) continue;

            if(mpn == null) mpn = n;
            else if (mpn.getCurrentValue() < n.getCurrentValue()) mpn = n;
        }

        return mpn;
    }

    public Need[] sortNeedsByPriority(){
        Need[] nds = _needList.values().toArray(new Need[_needList.values().size()]);
        float max = Float.MIN_VALUE;
        int index = -1;

        for(int i=0;i<nds.length;i++){
            index = i;
            for (int j=0;j<nds.length-i;j++) {
                if(max < ((Need)nds[i + j]).getCurrentValue()){
                    max = ((Need)nds[i + j]).getCurrentValue();
                    index = i + j;
                }
            } 
            Need tmp = nds[i];
            nds[i] = nds[index];
            nds[index] = tmp;
            index = -1;
            max = Float.MIN_VALUE;
        }
        return nds;
    }

    public void updateAllNeeds(){
        for(Need n : _needList.values()){
           n.setCurrentValue( n.getCurrentValue() + n.getGrowRate());
            checkNeedReachedMaxCap(n);
        }
    }

    private void checkNeedReachedMaxCap(Need n){
        if(n == null) return;
        if(n.getCurrentValue() >= n.getMaxCap()){
            if(n.getActionNeedMaxCapReached() != null) n.getActionNeedMaxCapReached().execute();
        }
    }
}