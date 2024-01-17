package Menu;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenu {
    private List<MenuItem> foods;
    private List<MenuItem> drinks;
    private List<MenuItem> desserts;

    public RestaurantMenu() {
        foods = new ArrayList<>();
        drinks = new ArrayList<>();
        desserts = new ArrayList<>();
    }

    public void addFood(MenuItem food) {
        foods.add(food);
    }

    public void addDrink(MenuItem drink) {
        drinks.add(drink);
    }

    public void addDessert(MenuItem dessert) {
        desserts.add(dessert);
    }

    public List<MenuItem> getFoods() {
        return foods;
    }

    public List<MenuItem> getDrinks() {
        return drinks;
    }

    public List<MenuItem> getDesserts() {
        return desserts;
    }
}

