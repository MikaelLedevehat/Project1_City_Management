package com.citymanagement.gameobjects;

import java.util.concurrent.ConcurrentHashMap;

import com.citymanagement.gameobjects.Needs.Need.Command;

/**
 * A class that represent a collection of {@code Need}, and can update them.
 */
public class Needs{

    /**
    * A class that represent a need starting at a value, and that can increase. The need can perform two action: one is {@code executeActionToFulfillNeed}
    * used to help full this particular need. The second will be called when the need will reach it's max value.
    */
    public class Need{

        /**
         * Is used to store an action (lamda) to do when some criterias are met.
         */
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

        /**
         * Reprsent a need that can be updated.
         * @param name Of the need.
         * @param currentValue Of the need.
         * @param needMaxCap Maximum cap for this specific need.
         * @param needGrowRate The amount the currentValue will grow by each update.
         * @param actionFulfilNeed The Command exectute to try fulfilling this need.
         * @param actionNeedMaxCapReached The command executed when the currentValue reach maxCap.
         */
        public Need(String name, float currentValue,float needMaxCap,float needGrowRate,Command actionFulfilNeed,Command actionNeedMaxCapReached ){
            this._growRate= needGrowRate;
            this._maxCap = needMaxCap;
            this._actionFulfilNeed = actionFulfilNeed;
            this._actionNeedMaxCapReached = actionNeedMaxCapReached;
            this._currentValue = currentValue;
            this._name = name;
        }

        /**
         * Get the name of this need.
         * @return The name of this need.
         */
        public String getName(){
            return _name;
        }

        /**
         * Set the name of the need.
         * @param n the new name of the need.
         */
        public void setName(String n){
            _name = n;
        }

        /**
         * Get the current value of this need.
         * @return the current value.
         */
        public float getCurrentValue(){
            return _currentValue;
        }

        /**
         * The maximum value possible for this need.
         * @return the max cap of this particular need.
         */
        public float getMaxCap(){
            return _maxCap;
        }

        /**
         * Get the growth rate (amount by witch this need's currentValue increate every update).
         * @return the growth rate. 
         */
        public float getGrowthRate(){
            return _growRate;
        }

        /**
         * Get the action used to fulfill this need (this action should help reducing, or even reseting this need value, but it is not guarenteed).
         * @return the action used to fulfill this need.
         */
        public Command getActionFulfillNeed(){
            return _actionFulfilNeed;
        }

        /**
         * Get the action used when the value reaches the max value allowed. This action is called automatically.
         * @return the action used when the value reaches the max value allowed.
         */
        public Command getActionNeedMaxCapReached(){
            return _actionNeedMaxCapReached;
        }

        /**
         * Set the current value of this need.
         * @param v the new value of this need.
         */
        public void setCurrentValue(float v){
            if(v < 0) _currentValue = 0;
            else if(v > _maxCap) _currentValue = _maxCap;
            else _currentValue = v;
        }

        /**
         * Set the maximum value allowed for this need (cap).
         * @param v the new maximum cap.
         */
        public void setMaxCap(float v){
            if(v < 0) _maxCap = 0;
            else _maxCap = v;
        }

        /**
         * Set the growth rate for this need (amount by witch this need's currentValue increate every update).
         * @param v the new growth rate.
         */
        public void setGrowthRate(float v){
            if(v < 0) _growRate = 0;
            _growRate = v;
        }

        /**
         * Set the action used to fulfill this need (this action should help reducing, or even reseting this need value, but it is not guarenteed).
         * @param c the new action.
         */
        public void setActionFulfillNeed(Command c){
            _actionFulfilNeed = c;
        }

        /**
         * Set the action used when the value reaches the max value allowed. This action is called automatically.
         * @param c the new action.
         */
        public void setActionNeedMaxCapReached(Command c){
            _actionNeedMaxCapReached = c;
        }
        
        /**
         * If an action is set to fulfill this need, execute it.
         * @return if an action was executed.
         */
        public boolean executeActionToFulfillNeed(){
            if(this.getActionFulfillNeed() == null) return false;
            else{
                this.getActionFulfillNeed().execute();
                return true;
            }
        }
    }

    private final ConcurrentHashMap<String, Need> _needList;

    /**
     * Create an empty collection of {@code Need}
     */
    public Needs(){
        _needList = new ConcurrentHashMap<>();
    }

    /**
     * Add a new need to the collection.
     * @param name the name of the new need. Must be unique.
     * @param currVal the starting value of the new need.
     * @param maxCap the maximum value allowed for the new need.
     * @param growRate the amount the value will increase eache update.
     * @param actionFulfil the action ({@code Command}) to execute to help fulfill the need. Can be null.
     * @param actionMaxCap the action ({@code Command}) automatically executed when the value reaches maxCap. Can be null.
     * @return false if the need already exist (same name), true otherwise.
     */
    public boolean addNeed(String name, float currVal, float maxCap, float growRate, Command actionFulfil, Command actionMaxCap){
        if(_needList.get(name)== null){
            _needList.put(name, new Need(name, currVal, maxCap, growRate, actionFulfil, actionMaxCap));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Remove a need from the collection.
     * @param name the name of the need to remove.
     * @return true if a Need was removed, false otherwise (need not existing).
     */
    public boolean removeNeed(String name){
        Need n = _needList.remove(name);
        if(n == null) return false;
        else return true;
    }

    /**
     * Get a specific need.
     * @param name the name of the need.
     * @return the need if it exist, null otherwise.
     */
    public Need getNeed(String name){
        return _needList.get(name);
    }

    /**
     * Get the need with the most advanced value proportionate to it's maxCap.
     * @param threshold the minimum value that a need should have to be considered.
     * @return the most pressing need, null if all the need are under the treshold.
     */
    //TODO sort using proportionality, not raw value. Use sortNeed by value
    public Need getMostPressingNeed(float threshold){
        Need mpn = null;
        for(Need n : _needList.values()){
            if(n.getCurrentValue()< threshold) continue;

            if(mpn == null) mpn = n;
            else if (mpn.getCurrentValue() < n.getCurrentValue()) mpn = n;
        }

        return mpn;
    }

    /**
     * Get an array of Need, sorted by priorty (current value proportionate to maxCap).
     * @returnan array of Need.
     */
    //TODO sort using proportionality, not raw value
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

    /**
     * Update all the currentValue of all the Need in the collection by their growthRate amount.
     * Calls {@code checkNeedReachedMaxCap()} on each need.
     */
    public void updateAllNeeds(){
        for(Need n : _needList.values()){
           n.setCurrentValue( n.getCurrentValue() + n.getGrowthRate());
            checkNeedReachedMaxCap(n);
        }
    }

    /**
     * Check if a need is at maximum value. If so, execute it's {@code actionNeedMaxCapReached}.
     * @param n the need to check.
     */
    private void checkNeedReachedMaxCap(Need n){
        if(n == null) return;
        if(n.getCurrentValue() >= n.getMaxCap()){
            if(n.getActionNeedMaxCapReached() != null) n.getActionNeedMaxCapReached().execute();
        }
    }
}