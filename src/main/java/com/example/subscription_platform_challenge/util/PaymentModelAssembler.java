package com.example.subscription_platform_challenge.util;

import com.example.subscription_platform_challenge.controller.PaymentController;
import com.example.subscription_platform_challenge.dto.PaymentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentModelAssembler implements
        RepresentationModelAssembler<PaymentDTO, EntityModel<PaymentDTO>> {

    @Override
    public EntityModel<PaymentDTO> toModel(PaymentDTO paymentDTO) {

        return EntityModel.of(paymentDTO,
                linkTo(methodOn(PaymentController.class).one(paymentDTO.getId())).withSelfRel(),
                linkTo(methodOn(PaymentController.class).all()).withRel("payments"));
    }
}
