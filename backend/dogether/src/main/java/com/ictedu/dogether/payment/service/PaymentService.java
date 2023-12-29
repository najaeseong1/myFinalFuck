package com.ictedu.dogether.payment.service;

import com.ictedu.dogether.payment.dto.PaymentRequest;
import com.ictedu.dogether.payment.dto.PaymentResponse;
import com.ictedu.dogether.payment.entity.CardInfo;
import com.ictedu.dogether.payment.entity.Payment;
import com.ictedu.dogether.payment.repository.CardInfoEntityRepository;
import com.ictedu.dogether.payment.repository.PaymentEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private PaymentEntityRepository paymentEntityRepository;
    @Autowired
    private CardInfoEntityRepository cardInfoEntityRepository;
    public PaymentResponse confirmPayment(PaymentRequest paymentRequest, String paymentKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", paymentKey);

        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);
        ResponseEntity<PaymentResponse> response = new RestTemplate().exchange(
                "https://api.tosspayments.com/v1/payments/confirm",
                HttpMethod.POST,
                entity,
                PaymentResponse.class
        );
        System.out.println(response.getBody());

        // response를 CardInfo로 변환
        CardInfo card = CardInfo.builder()
                .cardRegNo(response.getBody().getCard().getCardRegNo())
                .issuerCode(response.getBody().getCard().getIssuerCode())
                .acquirerCode(response.getBody().getCard().getAcquirerCode())
                .cardNumber(response.getBody().getCard().getCardNumber())
                .ownerType(response.getBody().getCard().getOwnerType())
                .installmentPlanMonths(response.getBody().getCard().getInstallmentPlanMonths())
                .isInterestFree(response.getBody().getCard().getIsInterestFree())
                .approveNo(response.getBody().getCard().getApproveNo())
                .acquireStatus(response.getBody().getCard().getAcquireStatus())
                .build();
        // 데이터베이스 카드 정보 저장
        cardInfoEntityRepository.save(card);
        
        // response를 PaymentEntity로 변환
        Payment payment = Payment.builder()
                .paymentKey(response.getBody().getPaymentKey())
                .orderId(response.getBody().getOrderId())
                .orderName(response.getBody().getOrderName())
                .amount(response.getBody().getTotalAmount())
                .method(response.getBody().getMethod())
                .status(response.getBody().getStatus())
                .requestedAt(response.getBody().getRequestedAt())
                .approvedAt(response.getBody().getApprovedAt())
                .build();

        // 데이터베이스에 저장
        paymentEntityRepository.save(payment);

        return response.getBody();
    }

}