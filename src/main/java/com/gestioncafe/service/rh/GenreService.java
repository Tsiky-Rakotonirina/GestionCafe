package com.gestioncafe.service.rh;

import com.gestioncafe.model.Genre;
import com.gestioncafe.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
