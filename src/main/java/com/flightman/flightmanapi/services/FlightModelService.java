package com.flightman.flightmanapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.FlightModel;
import com.flightman.flightmanapi.repositories.FlightModelRepository;

@Service
public class FlightModelService {
        @Autowired
        private FlightModelRepository flightModelRepository;

        public List<FlightModel> getAllFlightModels() {
                return flightModelRepository.findAll();
        }

        public FlightModel save(FlightModel flightModel) {
                return flightModelRepository.save(flightModel);
        }

        public Integer deleteModelById(Integer id) {
                return this.flightModelRepository.deleteByFlightModelId(id);
        }
}
