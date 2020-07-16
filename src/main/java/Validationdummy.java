import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaClient;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import java.util.Arrays;

class ValidationDummy {

  String validateexec(String schemaPath, HashMap testMap) {

    //Load Schema
    InputStream inputStream = getClass().getResourceAsStream(schemaPath);

    //Determine Maven resource path from given schema
    URL schemaURL = getClass().getResource(schemaPath);
    String[] splitClassPathArray = schemaURL.getPath().split("/");
    String[] finalizedsplitClasspathArray = Arrays
        .copyOfRange(splitClassPathArray, 0, splitClassPathArray.length - 1);
    String cutClasspath = String.join("/", finalizedsplitClasspathArray);
    String finalclasspath = "classpath:/" + cutClasspath + "/";

    // Convert schema and map into JsonObject
    JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
    JSONObject invalidJson = new JSONObject(testMap);

    //Set resolution scope with dynamically determined resourcefolder
    SchemaLoader schemaLoader = SchemaLoader.builder()
        .schemaClient(SchemaClient.classPathAwareClient())
        .schemaJson(rawSchema)
        .resolutionScope(finalclasspath)// setting the default resolution scope
        .build();

    //Generate Schema from Schemaloader
    Schema schema = schemaLoader.load().build();

    //Try to validate schema with everit package
    try {
      schema.validate(invalidJson);
      return "Validation successful";
    } catch (ValidationException validationException) {
      System.out.print(validationException.getErrorMessage());
      return "Validation failed";
    }

  }

}

