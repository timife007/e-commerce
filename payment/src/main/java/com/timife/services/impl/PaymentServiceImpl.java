package com.timife.services.impl;

import com.timife.feign.UserDetailsFeign;
import com.timife.model.OrderDto;
import com.timife.model.OrderStatus;
import com.timife.model.responses.UserResponse;
import com.timife.repositories.PaymentRepository;
import com.timife.services.PaymentStatusPublishService;
import com.timife.services.PaymentService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentStatusPublishService paymentStatusPublishService;

    @Autowired
    private PaymentRepository paymentRepository;


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private final UserDetailsFeign userDetailsFeign;


    @Override
    public void makePayment(OrderDto orderDto) {
        UserResponse userResponse = userDetailsFeign.getUserAddresses(orderDto.getUserId().intValue()).getBody();
        try {
            //Make payment here

            //Success email to that effect
            if (userResponse != null) {
                log.error(userResponse.toString());
                sendSimpleMessage(userResponse.address(),
                        "Your ASOS Order - "
                                + "#" +
                                orderDto.getId() +
                                "-" +
                                "successfully placed and Payment made",
                        "#Order ***** successfully placed and Payment made");
            }
            orderDto.setOrderStatus(OrderStatus.ORDER_SUCCESSFUL);
            //publish payment status with the orderResponse as successful.
            paymentStatusPublishService.publish(orderDto);
        } catch (Exception exception) {
            orderDto.setOrderStatus(OrderStatus.ORDER_FAILED);

            paymentStatusPublishService.publish(orderDto);

            //Failure Email
            if (userResponse != null) {
                sendSimpleMessage(userResponse.address(), "#Order" + orderDto.getId() + "failed", "#Order 123456 payment failed");
            }

            log.debug(exception.getLocalizedMessage());
        }
    }

    private void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("timife007@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    private void sendMessageWithAttachment(
            String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@baeldung.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File("pathToAttachment"));
        helper.addAttachment("Invoice", file);

        javaMailSender.send(message);
    }
}
