package com.example.subscription_platform_challenge.controller;

import com.example.subscription_platform_challenge.dto.PaymentDTO;
import com.example.subscription_platform_challenge.exceptions.PaymentNotFoundException;
import com.example.subscription_platform_challenge.exceptions.SubscriptionNotInformedException;
import com.example.subscription_platform_challenge.integration.ExternalPaymentApiClient;
import com.example.subscription_platform_challenge.mapper.PaymentMapper;
import com.example.subscription_platform_challenge.service.PaymentService;
import com.example.subscription_platform_challenge.util.PaymentModelAssembler;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper mapper;
    private final PaymentModelAssembler assembler;
    private final ExternalPaymentApiClient externalPaymentApiClient;
    private final AmqpTemplate queueSender;

    PaymentController(
            PaymentService paymentService,
            PaymentMapper paymentMapper,
            PaymentModelAssembler assembler,
            ExternalPaymentApiClient externalPaymentApiClient,
            AmqpTemplate queueSender) {
        this.paymentService = paymentService;
        this.mapper = paymentMapper;
        this.assembler = assembler;
        this.externalPaymentApiClient = externalPaymentApiClient;
        this.queueSender = queueSender;
    }

    @GetMapping
    public CollectionModel<EntityModel<PaymentDTO>> all() {
        List<EntityModel<PaymentDTO>> payments = paymentService.getAll()
                .stream()
                .map(mapper::toDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(payments, linkTo(methodOn(PaymentController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<PaymentDTO> one(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.get(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        return assembler.toModel(paymentDTO);
    }

    @PostMapping
    ResponseEntity<EntityModel<PaymentDTO>> newPayment(@RequestBody PaymentDTO newPaymentDTO) {
        Optional.ofNullable(newPaymentDTO.getSubscriptionId())
                .orElseThrow(SubscriptionNotInformedException::new);

        PaymentDTO paymentDTO = mapper.toDto(paymentService.register(mapper.toPayment(newPaymentDTO)));

        externalPaymentApiClient.processPayment(paymentDTO, queueSender);

        EntityModel<PaymentDTO> entityModel = assembler.toModel(
                mapper.toDto(
                        paymentService.update(paymentDTO.getId(), mapper.toPayment(paymentDTO))));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<PaymentDTO>> updatePayment(@RequestBody PaymentDTO paymentDTO, @PathVariable Long id) {
        Optional.ofNullable(paymentDTO.getSubscriptionId())
                .orElseThrow(SubscriptionNotInformedException::new);
        EntityModel<PaymentDTO> entityModel = assembler.toModel(
                mapper.toDto(
                        paymentService.update(id, mapper.toPayment(paymentDTO))));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
