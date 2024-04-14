package com.dirkadin.inventory;

import com.dirkadin.inventory.exception.ItemNotFoundException;
import com.dirkadin.inventory.model.Item;
import com.dirkadin.inventory.model.ItemModelAssembler;
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
public class InventoryController {

    private final InventoryRepository repository;
    private final ItemModelAssembler assembler;

    public InventoryController(InventoryRepository repository, ItemModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/items")
    public CollectionModel<EntityModel<Item>> getAll() {
        List<EntityModel<Item>> items = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(items, linkTo(methodOn(InventoryController.class).getAll()).withSelfRel());
    }

    @PostMapping("/items")
    ResponseEntity<?> newItem(@RequestBody Item newItem) {
        EntityModel<Item> entityModel = assembler.toModel(repository.save(newItem));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/items/{id}")
    public EntityModel<Item> one(@PathVariable Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return assembler.toModel(item);
    }

    @PutMapping("/items/{id}")
    ResponseEntity<?> replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
        Item updatedItem = repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setPrice(newItem.getPrice());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
        EntityModel<Item> entityModel = assembler.toModel(updatedItem);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity<?> deleteItem(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
