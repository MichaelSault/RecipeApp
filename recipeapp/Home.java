package uottawa.ca.recipeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        generateTests();//initial generation of the recipe list
    }

    protected void onResume(){// on resume is used to run the generate results funtion to update the list of recipes
        super.onResume();
        generateResults();
    }

    public void onClickHomeSearchButton(View view) {//find results passing the required information from the on screen fields to the generate function

        generateResults(
                ((TextView) findViewById(R.id.homeSearchBox)).getText().toString(),
                ((Spinner) findViewById(R.id.homeClassSpinner)).getSelectedItem().toString(),
                ((Spinner) findViewById(R.id.homeTypeSpinner)).getSelectedItem().toString(),
                ((Spinner) findViewById(R.id.homeCategorySpinner)).getSelectedItem().toString());

    }

    public void onClickHomeResetButton (View view) { // resets the screen lcearing the search fields and loading all recipes back to the list

        ((TextView) findViewById(R.id.homeSearchBox)).setText("");
        ((Spinner) findViewById(R.id.homeTypeSpinner)).setSelection(0);
        ((Spinner) findViewById(R.id.homeCategorySpinner)).setSelection(0);
        ((Spinner) findViewById(R.id.homeClassSpinner)).setSelection(0);

        generateResults();

    }

    public void onClickHomeAddButton(View view){// launches the add activity passing nothing to it knows its an add and not an edit
        Intent intent = new Intent(Home.this, AddEdit.class);

        intent.putExtra("selectedItem", "");
        startActivity(intent);
    }




    public void generateResults(String singredient, String clas, String type, String category) {

        ArrayList<String> results = new ArrayList<String>();

        ListView view = (ListView) findViewById(R.id.homeRecipeList);

        File dir = new File(String.valueOf(getFilesDir()));
        File[] directoryListing = dir.listFiles(new FilenameFilter() {//this is used so only .json files are loaded, otherwise a default folder in the default directory will be read giving a null object error
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".json");
            }
        });
        if (directoryListing != null) {
            for (File child : directoryListing) {
                Recipe recipe = read_ex.read(child.getName(), this);
                if (recipe != null) {
                    if (((recipe.get_class().toString().equalsIgnoreCase(clas)) || (recipe.get_type().toString().equalsIgnoreCase(type))) && (recipe.get_category().toString().equalsIgnoreCase(category))){ //filtering of recipes is dont here, every object is loaded and then the internals are searched for the required fields
                        for (Recipe.Ingredient ingredient : recipe.get_ingredients()){
                            if (ingredient.get_name().toString().equalsIgnoreCase(singredient)) {
                                results.add(recipe.get_name());
                            }
                        }
                    }
                }
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);

        view.setAdapter(arrayAdapter);

        view.setOnItemClickListener(new ListClickHandler());

    }


    public void generateResults() { //variation of generate results that populates the list with all recipes

        ArrayList<String> results = new ArrayList<String>();

        ListView view = (ListView) findViewById(R.id.homeRecipeList);

        File dir = new File(String.valueOf(getFilesDir()));
        File[] directoryListing = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".json");
            }
        });
        if (directoryListing != null) {
            for (File child : directoryListing) {
                Recipe recipe = read_ex.read(child.getName(), this);
                if (recipe != null) {
                    results.add(recipe.get_name());
                }
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);

        view.setAdapter(arrayAdapter);

        view.setOnItemClickListener(new ListClickHandler());

    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {

            String item = ((TextView) view).getText().toString();

            Intent intent = new Intent(Home.this, Details.class);

            intent.putExtra("selectedItem", item + ".json");
            startActivity(intent);

        }

    }

    private void generateTests(){// class to generate 3 test recipies
        Recipe recipe = new Recipe();
        recipe.set_name("Spaghetti");
        recipe.set_class(Recipe.Class.VEGGIE);
        recipe.set_category(Recipe.Category.MAIN_DISH);
        recipe.set_type(Recipe.Type.ITALIAN);
        Recipe.Ingredient ingr = new Recipe.Ingredient();
        List<Recipe.Ingredient> ingr_list = new ArrayList<Recipe.Ingredient>();
        ingr.set_name("Spaghetti");
        ingr.set_quantity(200);
        ingr.set_units(Recipe.Measure.GRAM);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Water");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.KILO_GRAM);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Salt");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.TEA_SPOON);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Butter");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.TABLE_SPOON);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Tomato Sauce");
        ingr.set_quantity(12);
        ingr.set_units(Recipe.Measure.OUNCE);
        ingr_list.add(ingr);
        recipe.set_ingredients(ingr_list);
        List<String> dire = new ArrayList<String>();
        dire.add("1. Make spaghetti");
        dire.add("2. Eat spaghetti");
        recipe.set_direction(dire);
        write_ex.write(recipe, this);

        recipe = new Recipe();
        recipe.set_name("Cheeseburger");
        recipe.set_class(Recipe.Class.BEEF);
        recipe.set_category(Recipe.Category.MAIN_DISH);
        recipe.set_type(Recipe.Type.AMERICAN);
        ingr = new Recipe.Ingredient();
        ingr_list = new ArrayList<Recipe.Ingredient>();
        ingr.set_name("Bun");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.PIECE);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Ground Beef");
        ingr.set_quantity(500);
        ingr.set_units(Recipe.Measure.GRAM);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Cheese");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.PIECE);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Ketchup");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.TABLE_SPOON);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Mustard");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.TEA_SPOON);
        ingr_list.add(ingr);
        recipe.set_ingredients(ingr_list);
        dire = new ArrayList<String>();
        dire.add("1. Make cheeseburger");
        dire.add("2. Eat cheeseburger");
        recipe.set_direction(dire);
        write_ex.write(recipe, this);

        recipe = new Recipe();
        recipe.set_name("Chicken Fried Rice");
        recipe.set_class(Recipe.Class.CHICKEN);
        recipe.set_category(Recipe.Category.STARTER);
        recipe.set_type(Recipe.Type.CHINESE);
        ingr = new Recipe.Ingredient();
        ingr_list = new ArrayList<Recipe.Ingredient>();
        ingr.set_name("Rice");
        ingr.set_quantity(2);
        ingr.set_units(Recipe.Measure.CUP);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Water");
        ingr.set_quantity(4);
        ingr.set_units(Recipe.Measure.CUP);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Soy Sauce");
        ingr.set_quantity(3);
        ingr.set_units(Recipe.Measure.TABLE_SPOON);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Chicken");
        ingr.set_quantity(1);
        ingr.set_units(Recipe.Measure.KILO_GRAM);
        ingr_list.add(ingr);
        ingr = new Recipe.Ingredient();
        ingr.set_name("Carrots");
        ingr.set_quantity(250);
        ingr.set_units(Recipe.Measure.GRAM);
        ingr_list.add(ingr);
        recipe.set_ingredients(ingr_list);
        dire = new ArrayList<String>();
        dire.add("1. Make chicken fried rice");
        dire.add("2. Eat chicken fried rice");
        recipe.set_direction(dire);
        write_ex.write(recipe, this);
    }

}
