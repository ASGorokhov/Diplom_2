package model;

import io.qameta.allure.Step;
import java.util.*;

public class OrderGenerator {

    @Step("Generate random order")
    public static Order generateOrder() {
        Order order = new Order();
        List<String> chosenIngredientsIds = new ArrayList<>();
        double numberOfIngredients = 3 + Math.random() * 8;

        for (int i = 0; i < numberOfIngredients; i++) {
            Ingredients ingredient = IngredientsGenerator.getRandomIngredient();
            chosenIngredientsIds.add(ingredient.get_id());
        }

        return order.setIngredients(chosenIngredientsIds);
    }
}