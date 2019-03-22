package uottawa.ca.recipeapp;

import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;  //Jackson must be compiled in the gradle script in order to import it

import java.io.FileInputStream;
import java.io.IOException;

public class read_ex {

   public static Recipe read(String name, Context context) {//files are saved using the recipe name as the file name so the reader takes a name as input and since its a static class it also needs a context

      ObjectMapper mapper = new ObjectMapper();
          try {
              int n;
              FileInputStream fis;
              fis = context.openFileInput(name);
              StringBuffer fileContent = new StringBuffer("");

              byte[] buffer = new byte[1024];

              while ((n = fis.read(buffer)) != -1) //the file is read and stored in a string buffer
              {
                  fileContent.append(new String(buffer, 0, n));
              }
              return mapper.readValue(fileContent.toString(), Recipe.class); //jackson then uses the string and the Recipe class info to recreate the recipe object for return
          } catch (IOException e) {
              Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
              e.printStackTrace();
          }
       return null;
   }

}
