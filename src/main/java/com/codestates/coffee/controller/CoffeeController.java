package com.codestates.coffee.controller;

import com.codestates.coffee.entity.Coffee;
import com.codestates.coffee.mapper.CoffeeMapper;
import com.codestates.coffee.dto.*;
import com.codestates.coffee.service.CoffeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v5/coffees")
@Validated
public class CoffeeController {
    private final CoffeeService coffeeService;
    private final CoffeeMapper coffeeMapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }

    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeePostDto coffeePostDto) {
        // TODO CoffeeService 클래스와 연동하세요.
        // TODO DTO <-> Entity 변환 Mapper를 적용하세요.

        Coffee coffee = coffeeService.createCoffee(coffeeMapper.coffeePostToCoffee(coffeePostDto));
        CoffeeResponseDto responseDto = coffeeMapper.coffeeToCoffeeResponseDto(coffee);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id") @Positive long coffeeId,
                                      @Valid @RequestBody CoffeePatchDto coffeePatchDto) {
        coffeePatchDto.setCoffeeId(coffeeId);

        // TODO CoffeeService 클래스와 연동하세요.
        // TODO DTO <-> Entity 변환 Mapper를 적용하세요.
        Coffee coffee = coffeeService.updateCoffee(coffeeMapper.coffeePatchToCoffee(coffeePatchDto));
        CoffeeResponseDto responseDto = coffeeMapper.coffeeToCoffeeResponseDto(coffee);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {
        // TODO CoffeeService 클래스와 연동하세요.
        // TODO DTO <-> Entity 변환 Mapper를 적용하세요.

        Coffee coffee = coffeeService.findCoffee(coffeeId);
        CoffeeResponseDto rsponseDto = coffeeMapper.coffeeToCoffeeResponseDto(coffee);
        return new ResponseEntity<>(rsponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees() {
        // TODO CoffeeService 클래스와 연동하세요.
        // TODO DTO <-> Entity 변환 Mapper를 적용하세요.

        List<Coffee> coffees = coffeeService.findCoffees();
        List<CoffeeResponseDto> responseDtos = coffees.stream()
                .map(coffee -> coffeeMapper.coffeeToCoffeeResponseDto(coffee))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id") long coffeeId) {
        // TODO CoffeeService 클래스와 연동하세요.
        // TODO DTO <-> Entity 변환 Mapper를 적용하세요.

        coffeeService.deleteCoffee(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
