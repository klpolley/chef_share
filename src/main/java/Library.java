import java.util.ArrayList;
import java.util.List;

public class Library {
    public List<Food> libraryList;
    public int length;

    public Library(){
        libraryList = new ArrayList<Food>();
        length = 0;
    }

    public int getLength(){

        return length;
    }

    public void addFood(Food foodIn){

    }

    public String getFoodName(int index){
        return "hi";
    }
}
