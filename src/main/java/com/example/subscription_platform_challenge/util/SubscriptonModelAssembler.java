package com.example.subscription_platform_challenge.util;

import com.example.subscription_platform_challenge.controller.SubscriptionController;
import com.example.subscription_platform_challenge.dto.SubscriptionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SubscriptonModelAssembler implements
        RepresentationModelAssembler<SubscriptionDTO, EntityModel<SubscriptionDTO>> {

    @Override
    public EntityModel<SubscriptionDTO> toModel(SubscriptionDTO subscriptionDTO) {

        return EntityModel.of(subscriptionDTO,
                linkTo(methodOn(SubscriptionController.class).one(subscriptionDTO.getId())).withSelfRel(),
                linkTo(methodOn(SubscriptionController.class).all()).withRel("subscriptions"));
    }
}
