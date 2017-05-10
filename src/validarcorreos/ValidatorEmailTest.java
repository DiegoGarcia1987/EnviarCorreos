package validarcorreos;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class ValidatorEmailTest {
 
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    
    public static boolean validateEmail(String email) {
        try
        {        
            Pattern pattern = Pattern.compile(PATTERN_EMAIL);        
            Matcher matcher = pattern.matcher(email.trim());
            return matcher.matches();
        }
        catch(Exception ex)
        {
            return false;
        }
 
    }
     public static void main(final String[] args) {
         if(ValidatorEmailTest.validateEmail(null))
         {
             System.out.println("Correo Correcto");
         }
         else         
         {
             System.out.println("Correo Invalido");
         }    
        
    }
 
}