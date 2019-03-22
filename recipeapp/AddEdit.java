package uottawa.ca.recipeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 12/5/2016.
 */

public class AddEdit extends AppCompatActivity{

    Recipe addEditRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        String state = getIntent().getStringExtra("selectedItem"); //this gets the passed recipe from the previous activity

        if(!state.equals("")){ //if the passed string is not empty (which indiictaes this is an edit not an add), the file is loaded and the fields populated
            Recipe recipe = read_ex.read(state + ".json", this); //l;oad and store recipe locally
            addEditRecipe = recipe;

            ((TextView) findViewById(R.id.addEditRecipeName)).setText(recipe.get_name());

            ((Spinner) findViewById(R.id.addEditClassSpinner)).setSelection(getClassPosition(recipe.get_class().toString()));;
            ((Spinner) findViewById(R.id.addEditCategorySpinner)).setSelection(getCategoryPosition(recipe.get_category().toString()));
            ((Spinner) findViewById(R.id.addEditTypeSpinner)).setSelection(getTypePosition(recipe.get_type().toString()));

            if(recipe.get_ingredients().size() >= 1) {
                ((TextView) findViewById(R.id.addEditIngredientName1)).setText(recipe.get_ingredients().get(0).get_name());
                ((TextView) findViewById(R.id.addEditIngredientQuantity1)).setText(String.valueOf(recipe.get_ingredients().get(0).get_quantity()));
                ((Spinner) findViewById(R.id.addEditIngredientUnits1)).setSelection(getUnitPosition(recipe.get_ingredients().get(0).get_units().toString()));
            }
            if(recipe.get_ingredients().size() >= 2) {
                ((TextView) findViewById(R.id.addEditIngredientName2)).setText(recipe.get_ingredients().get(1).get_name());
                ((TextView) findViewById(R.id.addEditIngredientQuantity2)).setText(String.valueOf(recipe.get_ingredients().get(1).get_quantity()));
                ((Spinner) findViewById(R.id.addEditIngredientUnits2)).setSelection(getUnitPosition(recipe.get_ingredients().get(1).get_units().toString()));
            }
            if(recipe.get_ingredients().size() >= 3) {
                ((TextView) findViewById(R.id.addEditIngredientName3)).setText(recipe.get_ingredients().get(2).get_name());
                ((TextView) findViewById(R.id.addEditIngredientQuantity3)).setText(String.valueOf(recipe.get_ingredients().get(2).get_quantity()));
                ((Spinner) findViewById(R.id.addEditIngredientUnits3)).setSelection(getUnitPosition(recipe.get_ingredients().get(2).get_units().toString()));
            }
            if(recipe.get_ingredients().size() >= 4) {
                ((TextView) findViewById(R.id.addEditIngredientName4)).setText(recipe.get_ingredients().get(3).get_name());
                ((TextView) findViewById(R.id.addEditIngredientQuantity4)).setText(String.valueOf(recipe.get_ingredients().get(3).get_quantity()));
                ((Spinner) findViewById(R.id.addEditIngredientUnits4)).setSelection(getUnitPosition(recipe.get_ingredients().get(3).get_units().toString()));
            }
            if(recipe.get_ingredients().size() >= 5) {
                ((TextView) findViewById(R.id.addEditIngredientName5)).setText(recipe.get_ingredients().get(4).get_name());
                ((TextView) findViewById(R.id.addEditIngredientQuantity5)).setText(String.valueOf(recipe.get_ingredients().get(4).get_quantity()));
                ((Spinner) findViewById(R.id.addEditIngredientUnits5)).setSelection(getUnitPosition(recipe.get_ingredients().get(4).get_units().toString()));
            }

            String directions = "";

            for(int i = 0; i < recipe.get_direction().size(); i++){
                directions = directions + recipe.get_direction().get(i) + "\n";
            }

            ((TextView) findViewById(R.id.addEditDirections)).setText(directions);
        }else{ //otherwise a new local recipe is created waiting to be filled
            addEditRecipe = new Recipe();
        }
    }

    public void onClickAddEditCancelButton (View view){
        super.onBackPressed();
    } //the cancel button returns you to the previous activity

    public void onClickAddEditSaveButton (View view){// the save button reads all the fields, sets them in the recipe object and then passes the Recipe object to write_ex to be saved, then the user is sent back to the previous activity
        addEditRecipe.set_name(((TextView) findViewById(R.id.addEditRecipeName)).getText().toString());
        addEditRecipe.set_class(setClassFromPosition(((Spinner) findViewById(R.id.addEditClassSpinner)).getSelectedItemPosition()));
        addEditRecipe.set_type(setTypeFromPosition(((Spinner) findViewById(R.id.addEditTypeSpinner)).getSelectedItemPosition()));
        addEditRecipe.set_category(setCategoryFromPosition(((Spinner) findViewById(R.id.addEditCategorySpinner)).getSelectedItemPosition()));

        Recipe.Ingredient ingr = new Recipe.Ingredient();

        List<Recipe.Ingredient> ingr_list = new ArrayList<Recipe.Ingredient>();

        ingr.set_name((((TextView) findViewById(R.id.addEditIngredientName1)).getText().toString()));
        ingr.set_quantity(Float.valueOf(((TextView) findViewById(R.id.addEditIngredientQuantity1)).getText().toString()));
        ingr.set_units((setUnitsFromPosition(((Spinner) findViewById(R.id.addEditIngredientUnits1)).getSelectedItemPosition())));
        ingr_list.add(ingr);

        ingr = new Recipe.Ingredient();
        ingr.set_name((((TextView) findViewById(R.id.addEditIngredientName2)).getText().toString()));
        ingr.set_quantity(Float.valueOf(((TextView) findViewById(R.id.addEditIngredientQuantity2)).getText().toString()));
        ingr.set_units((setUnitsFromPosition(((Spinner) findViewById(R.id.addEditIngredientUnits2)).getSelectedItemPosition())));
        ingr_list.add(ingr);

        ingr = new Recipe.Ingredient();
        ingr.set_name((((TextView) findViewById(R.id.addEditIngredientName3)).getText().toString()));
        ingr.set_quantity(Float.valueOf(((TextView) findViewById(R.id.addEditIngredientQuantity3)).getText().toString()));
        ingr.set_units((setUnitsFromPosition(((Spinner) findViewById(R.id.addEditIngredientUnits3)).getSelectedItemPosition())));
        ingr_list.add(ingr);

        ingr = new Recipe.Ingredient();
        ingr.set_name((((TextView) findViewById(R.id.addEditIngredientName4)).getText().toString()));
        ingr.set_quantity(Float.valueOf(((TextView) findViewById(R.id.addEditIngredientQuantity4)).getText().toString()));
        ingr.set_units((setUnitsFromPosition(((Spinner) findViewById(R.id.addEditIngredientUnits4)).getSelectedItemPosition())));
        ingr_list.add(ingr);

        ingr = new Recipe.Ingredient();
        ingr.set_name((((TextView) findViewById(R.id.addEditIngredientName5)).getText().toString()));
        ingr.set_quantity(Float.valueOf(((TextView) findViewById(R.id.addEditIngredientQuantity5)).getText().toString()));
        ingr.set_units((setUnitsFromPosition(((Spinner) findViewById(R.id.addEditIngredientUnits5)).getSelectedItemPosition())));
        ingr_list.add(ingr);

        addEditRecipe.set_ingredients(ingr_list);

        String multiLines = ((TextView) findViewById(R.id.addEditDirections)).getText().toString();
        String[] direction;
        String delimiter = "\n";
        direction = multiLines.split(delimiter);

        List<String> directionList = new ArrayList<>();
        for (int i = 0; i < direction.length; i++){
            directionList.add(direction[i]);
        }

        addEditRecipe.set_direction(directionList);

        write_ex.write(addEditRecipe, this);

        super.onBackPressed();
    }

    private int getClassPosition (String comp){// these are helper classes that line up with the order that the ENUM variables are in the Recipe class, this is to make setting and getting the values of the spinner m,ore easily since the values of the spinner are hardcoded in the strings.xml resource
        if (comp.equals("BEEF")){
            return 1;
        }else if (comp.equals("CHICKEN")){
            return 2;
        }else if (comp.equals("SEAFOOD")){
            return 3;
        }else if (comp.equals("VEGGIE")){
            return 4;
        }
        return -1;
    }

    private int getTypePosition (String comp){
        if (comp.equals("ITALIAN")){
            return 1;
        }else if (comp.equals("CHINESE")){
            return 2;
        }else if (comp.equals("MIDDLE_EASTERN")){
            return 3;
        }else if (comp.equals("INDIAN")){
            return 4;
        }else if (comp.equals("AMERICAN")){
            return 5;
        }
        return -1;
    }

     private int getCategoryPosition (String comp){
        if (comp.equals("STARTER")){
            return 1;
        }else if (comp.equals("MAIN_DISH")){
            return 2;
        }else if (comp.equals("DESSERT")){
            return 3;
        }else if (comp.equals("DRINK")){
            return 4;
        }else if (comp.equals("SAUCE")){
            return 5;
        }else if (comp.equals("SALAD")){
            return 6;
        }
        return -1;
    }

    private int getUnitPosition (String comp){
        if (comp.equals("TE_SPOON")){
            return 1;
        }else if (comp.equals("TABLE_SPOON")){
            return 2;
        }else if (comp.equals("CUP")){
            return 3;
        }else if (comp.equals("GRAM")){
            return 4;
        }else if (comp.equals("KILOGRAM")){
            return 5;
        }else if (comp.equals("OUNCE")){
            return 6;
        }else if (comp.equals("PIECE")){
            return 7;
        }
        return -1;
    }

    private Recipe.Class setClassFromPosition (int pos){
        if (pos == 1){
            return Recipe.Class.BEEF;
        }else if (pos == 2){
            return Recipe.Class.CHICKEN;
        }else if (pos == 3){
            return Recipe.Class.SEAFOOD;
        }else if (pos == 4){
            return Recipe.Class.VEGGIE;
        }
        return null;
    }

    private Recipe.Type setTypeFromPosition (int pos){
        if (pos == 1){
            return Recipe.Type.ITALIAN;
        }else if (pos == 2){
            return Recipe.Type.CHINESE;
        }else if (pos == 3){
            return Recipe.Type.MIDDLE_EASTERN;
        }else if (pos == 4){
            return Recipe.Type.INDIAN;
        }else if (pos == 5){
            return Recipe.Type.AMERICAN;
        }
        return null;
    }

    private Recipe.Category setCategoryFromPosition (int pos){
        if (pos == 1){
            return Recipe.Category.STARTER;
        }else if (pos == 2){
            return Recipe.Category.MAIN_DISH;
        }else if (pos == 3){
            return Recipe.Category.DESSERT;
        }else if (pos == 4){
            return Recipe.Category.DRINK;
        }else if (pos == 5){
            return Recipe.Category.SAUCE;
        }else if (pos == 6){
            return Recipe.Category.SALAD;
        }
        return null;
    }

    private Recipe.Measure setUnitsFromPosition (int pos){
        if (pos == 1){
            return Recipe.Measure.TEA_SPOON;
        }else if (pos == 2){
            return Recipe.Measure.TABLE_SPOON;
        }else if (pos == 3){
            return Recipe.Measure.CUP;
        }else if (pos == 4){
            return Recipe.Measure.GRAM;
        }else if (pos == 5){
            return Recipe.Measure.KILO_GRAM;
        }else if (pos == 6){
            return Recipe.Measure.OUNCE;
        }else if (pos == 7) {
            return Recipe.Measure.PIECE;
        }
        return null;
    }

}
