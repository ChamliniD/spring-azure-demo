package com.example.demo.controllers;

import com.example.demo.services.BlobStorageService;
import com.example.demo.services.DoctorService;
import com.example.demo.dtos.DoctorDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class DoctorController {

    public static final String DEFAULT_PERSON_FILE_NAME= "person.jpg";

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private BlobStorageService blobService;

    private DoctorDTO selectedDoctor;

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

    @GetMapping("/doctor/showDoc/{id}")
    public String showDoc(@PathVariable Long id, Model model) {
        DoctorDTO doctorDTO = doctorService.getDoctorDTOById(id);
        this.selectedDoctor = doctorDTO;
        model.addAttribute("doctor", this.selectedDoctor);

        String photoName = this.selectedDoctor.getPhotoName();
        if (photoName == null || photoName.isEmpty()) {
            photoName = DEFAULT_PERSON_FILE_NAME;
        }
        model.addAttribute("photoName", photoName);

        return "doctor_details";
    }

    @GetMapping("/doctor/displayImage")
    @ResponseBody
    public void displayImage(HttpServletResponse response) throws IOException {
        response.setContentType("image/*");
        String photoName = this.selectedDoctor.getPhotoName();
        if (photoName == null || photoName.isEmpty()) {
            photoName = DEFAULT_PERSON_FILE_NAME;
        }

        InputStream data = blobService.downloadBlob(photoName);
        if (data != null) {
            IOUtils.copy(data, response.getOutputStream());
        }
    }

    @GetMapping("/doctor/uploadPhotoView/{id}")
    public String uploadPhotoView(@PathVariable("id") Long id, Model model) {
        DoctorDTO doctor = doctorService.getDoctorDTOById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            this.selectedDoctor = doctor;
        }
        return "upload_photo";
    }

    @PostMapping("/doctor/uploadPhoto")
    public String uploadPhoto(@RequestParam("image") MultipartFile file, Model model) throws IOException {
        if (this.selectedDoctor != null) {
            model.addAttribute("doctor", this.selectedDoctor);
        }

        String originalFilename = file.getOriginalFilename();
        String fileNameToBeSaved = getFileNameToBeSaved(getFileNameToBeSaved(originalFilename));

        blobService.uploadBlob(fileNameToBeSaved, file);
        // Save info to db
        this.selectedDoctor.setPhotoName(fileNameToBeSaved);
        doctorService.updateDoctor(Long.valueOf(selectedDoctor.getId()), selectedDoctor);

        return "redirect:/doctor/showDoc/"+selectedDoctor.getId();
    }

    private String getFileNameToBeSaved(String originalFilename) {
        return this.selectedDoctor.getRegisteredNumber() + "." + getFileExtension(originalFilename);
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return ".jpg";
    }

}
