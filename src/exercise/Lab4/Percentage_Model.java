package exercise.Lab4;

import java.util.ArrayList;

public class Percentage_Model  {
	/**
	 * The Percentage value is internally stored as an integer
	 */
	protected double myValue;

	//List of listener 
	private ArrayList<ValueListener> listenerValueList;

	/**
	 * Create a Percentage with an initial value
	 * @param initialValue : the initial value
	 * @throws IllegalArgumentException if value is not correct
	 **/
	public Percentage_Model(double initialValue) {
        listenerValueList = new ArrayList<>();
//        setValue(initialValue);
		myValue = Math.round(initialValue);
	}

	public Percentage_Model() {
		this(0.0F);
	}

	//Is the value between 0 and 1?
	protected boolean valueIsOK(double value) {
		return ( (0.0F <= value) && (value <= 1.0F));
	}

	//Notify all the listener that the value has been changed
	private void fireValueChanged(Object source){
        for(ValueListener listener: listenerValueList){
        	//TODO do something with the listener
					listener.valueChanged(new ValueChangedEvent(source,getValue()));
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


	//Modify the value
    public void setValue(Object source,double value) {
		value/=100.0f;
        if (valueIsOK(value) ){
            myValue = Math.round(value * 100);
            fireValueChanged(source);
        } else {
            throw (new IllegalArgumentException("Bad percentage value: " + value));
        }
    }


}
