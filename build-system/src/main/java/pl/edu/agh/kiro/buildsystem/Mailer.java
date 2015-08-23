package pl.edu.agh.kiro.buildsystem;


import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {

    private Session session;
    private String recipients;
    private String from;

    public Mailer(Configuration config) {
        this.recipients = config.getMailerRecipients();
        this.from = config.getMailerUser();

        Properties props = new Properties();
        props.put("mail.smtp.auth", config.getMailerAuth());
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", config.getMailerHost());
        props.put("mail.smtp.port", config.getMailerPort());

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getMailerUser(), config.getMailerPass());
            }
        });
    }

    public void send(String subject, String text, String attachmentFilePath, String attachmentFileName) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));

            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);
            DataSource source = new FileDataSource(attachmentFilePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentFileName);
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException ex) {
            System.out.println("Error while sending email notification");
            return;
        }
        System.out.println("Email notification sent successfully");
    }


}
