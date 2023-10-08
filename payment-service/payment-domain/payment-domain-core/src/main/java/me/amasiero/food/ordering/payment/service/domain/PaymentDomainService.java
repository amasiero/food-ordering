package me.amasiero.food.ordering.payment.service.domain;

import java.util.List;

import me.amasiero.food.ordering.payment.service.domain.enttity.CreditEntry;
import me.amasiero.food.ordering.payment.service.domain.enttity.CreditHistory;
import me.amasiero.food.ordering.payment.service.domain.enttity.Payment;
import me.amasiero.food.ordering.payment.service.domain.event.PaymentEvent;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);
}
