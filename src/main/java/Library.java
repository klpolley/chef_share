import java.util.*;

public class Library {
    HashMap<String,Food> libraryList;
    public int length;

    public Library(){
        libraryList =new HashMap<String,Food>();
        length = 0;
    }

    public int getLength(){

        return length;
    }

    public void addFood(Food foodIn){
        String name = foodIn.getName();

        libraryList.put(name, foodIn);
        length++;
    }

    public boolean isFoodPresent(String foodName){
        if (libraryList.containsKey(foodName)) {
            return true;
        }else{
            return false;
        }
    }

    public Food getFood(String foodName){
        if(isFoodPresent(foodName)){
            return libraryList.get(foodName);
        }
        else return null;
    }

    public ArrayList<Food> search(String nameSnippet){
        Iterator it = libraryList.entrySet().iterator();
        ArrayList<Food> results = new ArrayList<Food>();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            if(((String)entry.getKey()).contains(nameSnippet)){
                results.add((Food)entry.getValue());
            }
        }
        return results;
    }

    public String listAllFood(){
        Iterator it = libraryList.entrySet().iterator();
        String results = "";
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            results += (String)pair.getKey() + "\n";
        }
        return results;
    }

    //JSON functions:

    public void setLibraryList(HashMap<String, Food> foodMap) {
        this.libraryList = foodMap;
    }

    public void setLength(int length){this.length = length;}
}
