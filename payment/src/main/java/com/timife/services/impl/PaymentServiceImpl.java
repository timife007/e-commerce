package com.timife.services.impl;

import com.timife.model.OrderDto;
import com.timife.model.OrderStatus;
import com.timife.repositories.PaymentRepository;
import com.timife.services.PaymentStatusPublishService;
import com.timife.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

//    @Autowired
//    public SimpleMailMessage template;



    @Override
    public void makePayment(OrderDto orderDto) {
        try {
            //Make payment here

            //Success email to that effect
            sendSimpleMessage("timothyademola226@gmail.com","#Order 123456 Successful","#Order 123456 successfully placed and Payment made");
            orderDto.setOrderStatus(OrderStatus.ORDER_SUCCESSFUL);
            //publish payment status with the orderResponse as successful.
            paymentStatusPublishService.publish(orderDto);
        } catch (Exception exception) {
            orderDto.setOrderStatus(OrderStatus.ORDER_FAILED);

            //Failure Email
            sendSimpleMessage("timothyademola226@gmail.com","#Order 123456 failed","#Order 123456 successfully placed and Payment made");
            paymentStatusPublishService.publish(orderDto);
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
}
