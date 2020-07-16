import java.util.HashMap;
import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationDummySpec {

    @Test
    public void testValidation() {
      //Initalize Variables
      ValidationDummy validationDummy = new ValidationDummy();
      HashMap validMap = new HashMap();
      HashMap invalidMap = new HashMap();

      //Add values to Testmap
      validMap.put("MyBool", "true");
      invalidMap.put("MyBool", 3);

      //Run Tests
      assertEquals("Validation successful", validationDummy.validateexec("parent.schema.json", validMap), "Parentschema should validate");
      assertEquals("Validation successful", validationDummy.validateexec("child.schema.json", validMap), "Childschema should validate");
      assertEquals("Validation failed", validationDummy.validateexec("parent.schema.json", invalidMap), "Parentschema should not validate");
      assertEquals("Validation failed", validationDummy.validateexec("child.schema.json", invalidMap), "Childschema should not validate");


    }

  }
