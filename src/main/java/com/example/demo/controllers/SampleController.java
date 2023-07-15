package com.example.demo.controllers;

import com.example.demo.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

//    @Autowired
//    private SampleRestController sampleRestController;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/message")
    public String message() {
        return "Congrats! App 10 deployed!";
    }

    @GetMapping("/message2")
    public String message2(Model model) {
//        model.addAttribute("doctors", sampleRestController.getAllDoctors("id"));
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors";
    }


}
