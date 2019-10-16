package exercise.Lab4;

public interface Controller_Interface {

    public double getValue() ;
    public void addListenerValue(ValueListener valueL) ;
    public void removeListenerValue(ValueListener valueL);
    public void notifyValueChanged(double value);


    }
