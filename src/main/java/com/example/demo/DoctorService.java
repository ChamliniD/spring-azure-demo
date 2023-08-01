package com.example.demo;

import com.example.demo.dtos.DoctorDTO;
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

    public List<DoctorDTO> findAll() {
        return doctorMapper.domainToDto(doctorRepository.findAll());
    }

    public void addDoctor(DoctorDTO doctorDTO) {
        doctorRepository.save(doctorMapper.dtoToDomain(doctorDTO));
    }

    public DoctorDTO getDoctorDTOById(Long id) {
        return doctorMapper.domainToDto(doctorRepository.findById(id.intValue()).get());
    }

    public void updateDoctor(Long id, DoctorDTO doctorDTO) {
        doctorRepository.save(doctorMapper.dtoToDomain(doctorDTO));
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id.intValue());
    }
}


