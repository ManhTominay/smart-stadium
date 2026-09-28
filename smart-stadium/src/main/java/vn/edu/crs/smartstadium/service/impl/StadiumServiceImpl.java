package vn.edu.crs.smartstadium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.crs.smartstadium.entity.Field;
import vn.edu.crs.smartstadium.entity.Stadium;
import vn.edu.crs.smartstadium.repository.FieldRepository;
import vn.edu.crs.smartstadium.repository.StadiumRepository;
import vn.edu.crs.smartstadium.service.StadiumService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class StadiumServiceImpl implements StadiumService {

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Override
    public List<Stadium> getAllStadiums() {
        return stadiumRepository.findAll();
    }

    @Override
    public Optional<Stadium> getStadiumById(Long id) {
        return stadiumRepository.findById(id);
    }

    @Override
    public Stadium saveStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

    @Override
    public void deleteStadium(Long id) {
        stadiumRepository.deleteById(id);
    }

    @Override
    public List<Field> findAvailableFields(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return fieldRepository.findAvailableFields(date, startTime, endTime);
    }
}