package com.citymanagement.gameobjects;

import java.util.HashMap;

import com.citymanagement.gameobjects.Needs.Need.Command;

public class Needs{

    public class Need{

        @FunctionalInterface
        public interface Command {
            void execute();
        }

        private float _currentValue = 0;
        private float _maxCap;
        private float _growRate;
        private Command _command;

        public Need(float currentValue,float needMaxCap,float needGrowRate,Command c){
            this._growRate= needGrowRate;
            this._maxCap = needMaxCap;
            this._command = c;
            this._currentValue = currentValue;
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
        public Command getCommand(){
            return _command;
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
        public void setCommand(Command c){
            if(c == null) throw new NullPointerException("The command can't be null");
            _command = c;
        }
    }

    private final HashMap<String, Need> _needList = new HashMap<>();

    public Needs(){
    }

    public boolean addNeed(String name, float currVal, float maxCap, float growRate, Command c){
        if(_needList.get(name)== null){
            _needList.put(name, new Need(currVal, maxCap, growRate, c));
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
        return _needList.get(name);
    }

    public void updateAllNeeds(){
        for(Need n : _needList.values()){
            n.setCurrentValue(n.getCurrentValue() + n.getGrowRate());
        }
    }
}