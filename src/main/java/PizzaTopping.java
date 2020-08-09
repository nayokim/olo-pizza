import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PizzaTopping {
    private JsonArray rootJsonArray;
    private ArrayList<Toppings> emptyArrayList= new ArrayList<Toppings>();
    private int freq;
    private String oloURL = "https://www.olo.com/pizzas.json";

    public static void main(String[] args) throws IOException {
        String oloURL = "https://www.olo.com/pizzas.json";

        PizzaTopping pizza = new PizzaTopping();
        pizza.findFrequency(oloURL);
    }

    private void request(String url) throws IOException {
        try {
            URL oloUrl = new URL(url);
            URLConnection requestSend = oloUrl.openConnection();
            JSONParser(requestSend);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void JSONParser(URLConnection requestSend) throws IOException {
        JsonParser jp = new JsonParser(); //from gson
        try
        {
            JsonElement response = jp.parse(new InputStreamReader((InputStream) requestSend.getContent())); //Convert the input stream to a json element
            rootJsonArray = (JsonArray)jp.parse(String.valueOf(response));
            System.out.println(rootJsonArray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
}

    private ArrayList<String> createArrayList(JsonArray rootJsonArray) {
        ArrayList<String> aList = new ArrayList<String>();
        if (rootJsonArray != null)
        {
            for (int i=0;i<rootJsonArray.size();i++)
            {
                aList.add(rootJsonArray.get(i).toString());
            }
        }
        return aList;
    }

    public void findFrequency(String url) throws IOException {
        request(url);
        for (int i = 0; i < rootJsonArray.size(); i++ ) {
            JsonObject baseJsonArray = (JsonObject) rootJsonArray.get(i);
            JsonArray toppingsJsonArray = (JsonArray) baseJsonArray.get("toppings");

            ArrayList<String> jsonArrayList = createArrayList(toppingsJsonArray);
            Collections.sort(jsonArrayList);

            boolean exists = false;
            for (Toppings toppings : emptyArrayList) {
                if (toppings.getToppingsArrayList().equals(jsonArrayList)) {
                    freq = toppings.getFrequency();
                    freq++;
                    toppings.setFrequency(freq);

                    exists = true;
                    break;
                }
            }

            if (!exists){
                Toppings toppings = new Toppings();
                toppings.setFrequency(1);
                toppings.setToppingsArrayList(jsonArrayList);
                emptyArrayList.add(toppings);
            }
        }
        emptyArrayList.sort(Comparator.comparing(Toppings::getFrequency).reversed());

        System.out.println("Top 20 Pizza Toppings");
        for(int i=0; i<20; i++)
        {
            Toppings toppings = emptyArrayList.get(i);
            System.out.println("# " + (i+1) + ": " + toppings.getToppingsArrayList() + ", count: " +  toppings.getFrequency());
        }
    }

}
