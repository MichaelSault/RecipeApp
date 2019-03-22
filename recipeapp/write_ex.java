package uottawa.ca.recipeapp;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;            //Jackson must be compiled in the gradle script in order to import it
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
public class write_ex {

   public static void write(Recipe recipe, Context context) {   //write takes the recipe object to be written and since its a static class, a context needs to be passed as well
      ObjectMapper mapper = new ObjectMapper();
       mapper.enable(SerializationFeature.INDENT_OUTPUT);
       String jsonString;
      try {
          jsonString = mapper.writeValueAsString(recipe); //the object mapper generates a string from the given class
          FileOutputStream os = context.openFileOutput((recipe.get_name() + ".json"), context.MODE_PRIVATE);  //the object string is then saved using the recipe name and .json extension
          os.write(jsonString.getBytes());
          os.close();
      } catch (Exception e){
         System.out.println("Exception" + e);
      }
   }
}
