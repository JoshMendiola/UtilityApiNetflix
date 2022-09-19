package com.example.utilityapi.controller;

import com.example.utilityapi.models.CustomErrorResponse;
import com.example.utilityapi.controller.CalculatorExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CalculatorController {

    public CalculatorController()
    {
    }

    @RequestMapping(value = "/calculator/divide", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public double divide(@RequestParam int value1, @RequestParam int value2)
    {
        if(value2 == 0)
        {
            return 0.0;
        }
        return value1 / value2;
    }

    @RequestMapping(value = "/calculator/square/{value}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public int square(@PathVariable int value)
    {
        long square = (long)value * (long)value;
        if(square <= Integer.MAX_VALUE)
        {
            return value * value;
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Number too large");
    }
}
