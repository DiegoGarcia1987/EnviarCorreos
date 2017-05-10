package validarcorreos;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EnviarCorreoAdjunto 
{
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    
    public Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", "smtp.office365.com");
        config.put("mail.smtp.port", 587);
        return config;
    }
    
    public void sendMail(String mailServer, String from, String to,String subject, String messageBody,String[] RutaArchivos,String[] NombreArchivos) throws MessagingException, AddressException 
     { 
         try
         { 
             final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("office365@outlook.com", "supassword");
            }});             
             try 
            {
             final Message message = new MimeMessage(session); 
             message.setFrom(new InternetAddress(from)); 
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
             message.setSubject(subject);                        
             BodyPart messageBodyPart = new MimeBodyPart();              
             messageBodyPart.setContent("<div><h2>PRUEBA DE CORREO.</h2>"+                                        
                                        "<h5>"+messageBody+"</h5>"+                                       
                                        "Sitio Web: www.google.com<br>","text/html");
             Multipart multipart = new MimeMultipart();                        
             multipart.addBodyPart(messageBodyPart);                       
             addAtachments(RutaArchivos,NombreArchivos, multipart);                        
             message.setContent(multipart);                        
             Transport.send(message); 
             } catch (final MessagingException ex) {
            LOGGER.log(Level.WARNING, "Error al enviar el mensaje: " + ex.getMessage(), ex);
        } 
         }
         catch(Exception e)
         {
             System.out.println(e.getMessage());
         }
     } 
    protected void addAtachments(String[] RutaArchivos,String[] NombreArchivos, Multipart multipart)throws MessagingException, AddressException 
     { 
         for(int i = 0; i<= RutaArchivos.length -1; i++) 
         {              
            String NombreArchivo = NombreArchivos[i]; 
            String RutaArchivo = RutaArchivos[i]; 
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();                           
            DataSource source = new FileDataSource(RutaArchivo); 
            attachmentBodyPart.setDataHandler(new DataHandler(source));                           
            attachmentBodyPart.setFileName(NombreArchivo);                           
            multipart.addBodyPart(attachmentBodyPart); 
         } 
     }
    public void EnviarCorreo(String to,String subject,String[] RutaArchivos,String[] NombreArchivos) 
     { 
         try 
         { 
             String server="smtp.office365.com"; 
             String from="office365@outlook.com"; 
             String message="PRUEBA DE CORREO <br>"; 
             sendMail(server,from,to,subject,message,RutaArchivos,NombreArchivos); 
         } 
         catch(Exception e) 
         { 
             System.out.println(e.getMessage());	
         }           
     }       
    
    public static void main(String[] args) {
        EnviarCorreoAdjunto correo = new EnviarCorreoAdjunto();    
        //se agregar en una matriz los archivos que se va a adjuntar
        String []Ruta={"C:\\AS.pdf","C:\\Users\\AS.pdf"};
        //para cada archivos se debe especificar un nombre
        String []Nombres={"AS.PDF","AS.PDF"};
        //sucorreo@gmail.com es la cuenta a la q va dirigido el correo
        correo.EnviarCorreo("sucorreo@gmail.com","prueba de envio de correo",Ruta,Nombres);
    }
}
