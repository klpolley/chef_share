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
}
