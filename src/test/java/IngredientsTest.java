import client.IngredientsClient;
import model.Ingredients;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class IngredientsTest {

    @Test
    public void getIngredientsTest() {
        IngredientsClient ingredientClient = new IngredientsClient();
        List<Ingredients> actualIngredients = ingredientClient.get().getData();
        assertThat("No available ingredients", actualIngredients.size(), is(not(0)));
    }
}