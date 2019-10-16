package exercise.sol.model;

import exercise.sol.event.*;


import java.util.ArrayList;

public class Percentage_Model  {
	/**
	 * The Percentage value is internally stored as an integer
	 */
	protected double myValue;

	private ArrayList<ValueListener> listenerValueList;



	/**
	 * Create a Percentage with an initial value
	 * @param initialValue : the initial value
	 * @throws IllegalArgumentException if value is not correct
	 **/
	public Percentage_Model(double initialValue) {
        listenerValueList = new ArrayList<>();
        setValue(initialValue);
	}

	public Percentage_Model() {
		this(0.0F);
	}

	protected boolean valueIsOK(double value) {
		return ( (0.0F <= value) && (value <= 1.0F));
	}

	public void fireValueChanged(){

        for(ValueListener listener : listenerValueList){
			listener.valueChanged(new ValueChangedEvent(this, getValue()));
		}
	}

	public void addValueListener(ValueListener listener){
		listenerValueList.add(listener);
	}

	public void removeValueListener(ValueListener l){
		listenerValueList.remove( l);
	}


	/**
	 * @returns the current value
	 **/
	public double getValue() {
		return myValue / 100f;
	}



    public void setValue(double value) {
        if (valueIsOK(value) ){
            myValue = Math.round(value * 100);
            fireValueChanged();
            //ValueChangedEvent e = new ValueChangedEvent(ValueChangedEvent.VALUE_CHANGED, value);

           // new ValueChangedEvent(); fireEvent(new ValueChangedEvent(ValueChangedEvent.VALUE_CHANGED, value));

        } else {
            throw (new IllegalArgumentException("Bad percentage value: " + value));
        }
    }


}
