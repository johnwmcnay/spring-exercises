package com.example.springtest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class DiceController {

    int sides = 6;

    @GetMapping("/roll-dice")
    public String rollDice(Model model) {

        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= sides; i++) {
            list.add(i);
        }

        model.addAttribute("list", list);

        return "roll-dice";
    }

    @PostMapping("/roll-dice/{guess}")
    public String guessRoll(@PathVariable int guess, Model model) {
        model.addAttribute("guess", guess);
        int roll = new Random().ints(1, sides + 1)
                .findFirst()
                .getAsInt();

        model.addAttribute("roll", roll);

        return "roll-dice";
    }

}
