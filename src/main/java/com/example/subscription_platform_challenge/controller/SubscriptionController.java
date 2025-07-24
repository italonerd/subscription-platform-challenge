package com.example.subscription_platform_challenge.controller;

import com.example.subscription_platform_challenge.dto.SubscriptionDTO;
import com.example.subscription_platform_challenge.exceptions.SubscriptionNotFoundException;
import com.example.subscription_platform_challenge.mapper.SubscriptionMapper;
import com.example.subscription_platform_challenge.service.SubscriptionService;
import com.example.subscription_platform_challenge.util.SubscriptonModelAssembler;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper mapper;

    private final SubscriptonModelAssembler assembler;

    SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper,
                           SubscriptonModelAssembler assembler) {
        this.subscriptionService = subscriptionService;
        this.mapper = subscriptionMapper;
        this.assembler = assembler;
    }

    @GetMapping
    //TODO Fix cache connection problem @Cacheable(value = "subscriptions", key = "allSubscriptions")
    public CollectionModel<EntityModel<SubscriptionDTO>> all() {
        List<EntityModel<SubscriptionDTO>> subscriptionDTOS = subscriptionService.getAll()
                .stream()
                .map(mapper::toDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(subscriptionDTOS, linkTo(methodOn(SubscriptionController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    //TODO Fix cache connection problem @Cacheable(value = "subscriptions", key = "#id")
    public EntityModel<SubscriptionDTO> one(@PathVariable Long id) {
        SubscriptionDTO subscriptionDTO = subscriptionService.getOne(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));

        return assembler.toModel(subscriptionDTO);
    }

    @PostMapping
    //TODO Fix cache connection problem@CachePut(cacheNames = "subscriptions", key = "#id")
    ResponseEntity<EntityModel<SubscriptionDTO>> newSubscription(@RequestBody SubscriptionDTO newSubscription) {
        EntityModel<SubscriptionDTO> entityModel = assembler.toModel(
                mapper.toDto(
                        subscriptionService.register(
                                mapper.toSubscription(newSubscription))));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    /*
    //TODO Fix cache connection problem
    @Caching(
            evict = {@CacheEvict(value = "subscriptionsList", allEntries = true)},
            put = {@CachePut(value = "subscriptions", key = "#id")}
    )*/
    ResponseEntity<EntityModel<SubscriptionDTO>> updateSubscription(
            @RequestBody SubscriptionDTO subscriptionDTO,
            @PathVariable Long id
    ) {
        EntityModel<SubscriptionDTO> entityModel = assembler.toModel(
                mapper.toDto(
                        subscriptionService.update(id, mapper.toSubscription(subscriptionDTO))));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> cancelSubscription(@PathVariable Long id) {
        subscriptionService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
