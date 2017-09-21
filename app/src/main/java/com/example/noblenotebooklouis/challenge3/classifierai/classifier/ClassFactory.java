package nl.utwente.mod6.ai.classifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.utwente.mod6.ai.exceptions.ExceptionMessages;

import java.io.*;

/**
 * Created by omer on 30-11-15.
 * This class is not used anymore
 * This class would read from a file and make JSON objects and
 * then a list of classes would be made with these JSON objects.
 */
public class ClassFactory {

    private String path;

    public ClassFactory(String path) {
        this.path = path;
    }

    public String readFromFile() {
        String result = "";

        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(this.path));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (FileNotFoundException e) {
            System.out.println(ExceptionMessages.FILE_NOT_FOUND);
            System.exit(ExceptionMessages.FILE_NOT_FOUND_EXIT);
        } catch (IOException e) {
            System.out.println(ExceptionMessages.IO);
            System.exit(ExceptionMessages.IO_EXIT);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(ExceptionMessages.IO);
                    System.exit(ExceptionMessages.IO_EXIT);
                }
            }
        }
        return result;
    }

    public void makeClassesFromJSON(String json, Classifier classifier) {
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("classList");
        for (int i = 0; i < jsonArray.size(); i++) {
            ClassifierClass c = new ClassifierClass(jsonArray.get(i).toString());
            //CLASSIFIER.addClass(c);
            System.out.println(c.toString());
        }

    }
}
