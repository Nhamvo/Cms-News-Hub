package com.example.demo.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendEmailWithAttachment(String to, String subject)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("nhamvkph29720@fpt.edu.vn");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent(), true);

        // Uncomment if you need to attach an image
        // File image = new File("path/to/image.jpg");
        // helper.addInline("image", image);

        emailSender.send(message);
        System.out.println("Email sent successfully");
    }
    @Async
    public String htmlContent() {
        return "<h1>Thông báo mới</h1>"
                + "<p>Chúng tôi có tin tức mới cho bạn!</p>"
                + "<img src='cid:image' alt='New Image'/>"
                + "<p>Chi tiết hơn <a href='https://example.com'>tại đây</a>.</p>";
    }
}
