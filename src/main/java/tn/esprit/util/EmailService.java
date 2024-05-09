package tn.esprit.util;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;

public class EmailService {

    private static final String API_KEY = "mlsn.7ba98234356d4d4dd7422ad8e23a1f272ca09e779e81e6bffde0eacec7c5a50b";
    private static final String SENDER_EMAIL = "MS_vDuhzP@trial-ynrw7gyq5y242k8e.mlsender.net";
    public void sendEmail(String toEmail, String subject, String body) {
        if (toEmail == null || toEmail.isEmpty()) {
            System.err.println("Failed to send email: recipient email address is null or empty");
            return;
        }

        Email email = new Email();
        email.setFrom("Siyahi", SENDER_EMAIL);
        Recipient r = new Recipient("test", toEmail);
        email.AddRecipient(r);
        email.setSubject(subject);
        email.setHtml(body);

        MailerSend ms = new MailerSend();
        ms.setToken(API_KEY);
        try {

            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

}