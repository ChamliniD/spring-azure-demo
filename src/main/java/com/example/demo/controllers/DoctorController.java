package com.example.demo.controllers;

import com.example.demo.DoctorService;
import com.example.demo.dtos.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String getAllDoctors(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctors";
    }

    @GetMapping("/doctor/newForm")
    public String addDoctorForm(Model model) {
        DoctorDTO doctor = new DoctorDTO();
        model.addAttribute("doctor", doctor);
        return "add_doctor";
    }

    @PostMapping("/doctor/add")
    public String addDoctor(@ModelAttribute("doctor") DoctorDTO doctorDTO) {
        doctorService.addDoctor(doctorDTO);
        return "redirect:/";
    }

    @GetMapping("/doctor/updateForm/{id}")
    public String updateDoctorForm(@PathVariable Long id, Model model) {
        DoctorDTO doctor = doctorService.getDoctorDTOById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            return "update_doctor";
        }
        return "error_page";

    }

    @PostMapping("/doctor/update/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute("doctor") DoctorDTO doctorDTO) {
        doctorService.updateDoctor(id, doctorDTO);
        return "redirect:/";
    }

    @GetMapping("/doctor/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/";
    }

}
