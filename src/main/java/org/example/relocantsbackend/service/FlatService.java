package org.example.relocantsbackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.relocantsbackend.entity.Flat;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class FlatService {

    private static final String FLAT_JSON_FILE_PATH = "src/main/resources/json/flat.json";

    public List<Flat> getFlats() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Flat> flats = objectMapper.readValue(new File(FLAT_JSON_FILE_PATH), new TypeReference<List<Flat>>() {});
        Collections.shuffle(flats);
        return flats;
    }
}
