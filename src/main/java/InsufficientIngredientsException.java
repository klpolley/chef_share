import java.util.Collection;

public class InsufficientIngredientsException extends Throwable {

    private Collection<Ingredient> insufficientIngredients;

    public InsufficientIngredientsException(Collection<Ingredient> insufficientIngredients) {
        this.insufficientIngredients = insufficientIngredients;
    }

    public Collection<Ingredient> getInsufficientIngredients(){
        return insufficientIngredients;
    }
}
