package com.example.demo.services;

import com.example.demo.dtos.DoctorDTO;
import com.example.demo.entities.Doctor;
import com.example.demo.mappers.DoctorMapper;
import com.example.demo.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EmailService emailService;

    public List<DoctorDTO> findAll() {
        return doctorMapper.domainToDto(doctorRepository.findAll());
    }

    public void addDoctor(DoctorDTO doctorDTO) {
        Doctor savedDoctor = doctorRepository.save(doctorMapper.dtoToDomain(doctorDTO));
        sendEmailForDoctorOperations(savedDoctor, "Doctor Created");
    }

    public DoctorDTO getDoctorDTOById(Long id) {
        return doctorMapper.domainToDto(doctorRepository.findById(id.intValue()).get());
    }

    public void updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor savedDoctor = doctorRepository.save(doctorMapper.dtoToDomain(doctorDTO));
        sendEmailForDoctorOperations(savedDoctor, "Doctor Updated");
    }

    public void deleteDoctor(Long id) {
        Doctor beforeDelete = doctorMapper.dtoToDomain(getDoctorDTOById(id));
        doctorRepository.deleteById(id.intValue());
        sendEmailForDoctorOperations(beforeDelete, "Doctor Deleted");

    }

    private void sendEmailForDoctorOperations(Doctor doctor, String subject) {
        emailService.sendEmailToSingleRecipient(doctor.getEmail(), subject, doctor.toString());
    }
}
