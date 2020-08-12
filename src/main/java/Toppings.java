import java.util.ArrayList;

public class Toppings
{
    private int frequency;
    private ArrayList<String> toppingsArrayList;

    public int getFrequency()
    {
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public ArrayList<String> getToppingsArrayList()
    {
        return toppingsArrayList;
    }

    public void setToppingsArrayList(ArrayList<String> toppingsArrayList)
    {
        this.toppingsArrayList = toppingsArrayList;
    }
}
