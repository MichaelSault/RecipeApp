package uottawa.ca.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jordan on 12/4/2016.
 */

public class Details extends AppCompatActivity {

    Recipe detailsRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {// loads the recipe from disk and populates all the fields
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        String selected = getIntent().getStringExtra("selectedItem");

        Recipe recipe = read_ex.read(selected, this);

        detailsRecipe = recipe;

        ((TextView) findViewById(R.id.detailsRecipeName)).setText(recipe.get_name());
        ((TextView) findViewById(R.id.detailsClass)).setText(recipe.get_class().toString());
        ((TextView) findViewById(R.id.detailsCategory)).setText(recipe.get_category().toString());
        ((TextView) findViewById(R.id.detailsType)).setText(recipe.get_type().toString());

        ListView ingredients = (ListView) findViewById(R.id.detailsIngredientsList);
        ListView directions = (ListView) findViewById(R.id.detailsDirectionsList);

        ArrayList<String> ingredientString= new ArrayList<String>();
        for (Recipe.Ingredient ingredient : recipe.get_ingredients()){
            ingredientString.add(ingredient.get_name() + "  " + ingredient.get_quantity() + "  " + ingredient.get_units());
        }

        ArrayList<String> dire = new ArrayList<String>();

        for(int i = 0; i < recipe.get_direction().size(); i++){
            dire.add(recipe.get_direction().get(i));
        }

        ArrayAdapter<String> ingredientsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientString);
        ArrayAdapter<String> directionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dire);

        ingredients.setAdapter(ingredientsArrayAdapter);
        directions.setAdapter(directionsArrayAdapter);

    }

    protected void onResume(){ //on resume is used to refresh the page with the updated information after an edit, since the edit screen will send you back here
        super.onResume();

        Recipe recipe = read_ex.read(detailsRecipe.get_name() + ".json", this);
        detailsRecipe = recipe;

        ((TextView) findViewById(R.id.detailsRecipeName)).setText(recipe.get_name());
        ((TextView) findViewById(R.id.detailsClass)).setText(recipe.get_class().toString());
        ((TextView) findViewById(R.id.detailsCategory)).setText(recipe.get_category().toString());
        ((TextView) findViewById(R.id.detailsType)).setText(recipe.get_type().toString());

        ListView ingredients = (ListView) findViewById(R.id.detailsIngredientsList);
        ListView directions = (ListView) findViewById(R.id.detailsDirectionsList);

        ArrayList<String> ingredientString= new ArrayList<String>();
        for (Recipe.Ingredient ingredient : recipe.get_ingredients()){
            ingredientString.add(ingredient.get_name() + "  " + ingredient.get_quantity() + "  " + ingredient.get_units());
        }

        ArrayList<String> dire = new ArrayList<String>();

        for(int i = 0; i < recipe.get_direction().size(); i++){
            dire.add(recipe.get_direction().get(i));
        }

        ArrayAdapter<String> ingredientsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientString);
        ArrayAdapter<String> directionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dire);

        ingredients.setAdapter(ingredientsArrayAdapter);
        directions.setAdapter(directionsArrayAdapter);
    }

    public void onClickDetailsDeleteButton (View view){
        File dir = getFilesDir();
        File file = new File(dir, detailsRecipe.get_name() + ".json");
        file.delete();
        super.onBackPressed();
    }

    public void onClickDetailsEditButton(View view){
        Intent intent = new Intent(Details.this, AddEdit.class);

        String item = ((TextView) view).getText().toString();

        intent.putExtra("selectedItem", detailsRecipe.get_name());
        startActivity(intent);
    }
}
