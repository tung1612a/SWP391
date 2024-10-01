/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author ADMIN
 */
public class EmailUtility {
    
    public static void sendEmail(String toEmail, String subject, String Content) throws MessagingException {
        // Set properties for the mail server
        String fromEmail = "5englandstrong@gmail.com";
        String password = "kdoa ghav dooo dopd";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // For TLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS encryption

        // Create session with authenticator
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // Create MimeMessage object
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        
        subject = "[5 Anh Lực] " + subject;
        
        // Encode the subject with UTF-8
        String encodedSubject;
        try {
            encodedSubject = MimeUtility.encodeText(subject, "UTF-8", "B");
            message.setSubject(encodedSubject);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set the email content (HTML format)
        String htmlContent = "<html lang=\"en\">";
        htmlContent += "<body>";
        htmlContent += "<h2>Quán ăn 5 Anh Lực gửi</h2>";
        htmlContent += "<h5>" + Content + "</h5>";
        htmlContent += "<h2 style=\"color: red; text-transform: uppercase;\">Đây là email tự động, xin đừng trả lời email này</h2>";
        htmlContent += "</body>";
        htmlContent += "</html>";
        
        
        // Set the email content (HTML format) and specify UTF-8 encoding
        message.setContent(htmlContent, "text/html; charset=UTF-8");


        // Send the email
        Transport.send(message);
    }
}
