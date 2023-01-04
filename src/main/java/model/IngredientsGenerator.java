package model;

import client.IngredientsClient;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Random;

public class IngredientsGenerator {

    @Step("Generate random ingredient")
    public static Ingredients getRandomIngredient() {
        IngredientsClient ingredientClient = new IngredientsClient();
        List<Ingredients> ingredients = ingredientClient
                .get()
                .getData();

        Random rand = new Random();

        return ingredients.get(rand.nextInt(ingredients.size()));
    }
}