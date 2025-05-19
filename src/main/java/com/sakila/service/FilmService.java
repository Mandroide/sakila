package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.ActorEntity;
import com.sakila.entity.FilmActorEntity;
import com.sakila.entity.FilmCategoryEntity;
import com.sakila.entity.FilmEntity;
import com.sakila.enums.Category;
import com.sakila.repository.ActorRepository;
import com.sakila.repository.FilmActorRepository;
import com.sakila.repository.FilmCategoryRepository;
import com.sakila.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmCategoryRepository filmCategoryRepository;
    private final FilmActorRepository filmActorRepository;
    private final ActorRepository actorRepository;

    public FilmFindAllResponse findAll(FilmFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<FilmDto> page = filmRepository.findAll(pageRequest).map(FilmEntity::toDto);
        List<FilmDto> content = page.getContent();
        content.forEach(this::fillCollections);

        return FilmFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .films(content)
                .build();
    }

    private void fillCollections(FilmDto filmDto) {
        List<Category> categories = filmCategoryRepository.findAllByFilmId(filmDto.getFilmId())
                .stream().map(FilmCategoryEntity::getCategoryId).map(Category::from).toList();
        filmDto.setCategories(categories);
        List<Integer> actorIds = filmActorRepository.findAllByFilmId(filmDto.getFilmId())
                .stream().map(FilmActorEntity::getActorId).toList();
        List<ActorDto> actors = actorRepository.findAllById(actorIds).stream().map(ActorEntity::toDto).toList();
        filmDto.setActors(actors);
    }

    public FilmFindByIdResponse findById(Integer id) {
        return FilmFindByIdResponse.builder()
                .film(filmRepository.findById(id).map(FilmEntity::toDto).orElseThrow())
                .build();
    }


    @Transactional
    public FilmFindByIdResponse create(FilmCreateRequest request) {
        FilmEntity filmEntity = FilmEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseYear(request.getReleaseYear())
                .language(request.getLanguage())
                .originalLanguage(request.getOriginalLanguage())
                .rentalDuration(request.getRentalDuration())
                .rentalRate(request.getRentalRate())
                .length(request.getLength())
                .replacementCost(request.getReplacementCost())
                .rating(request.getRating())
                .specialFeatures(request.getSpecialFeatures())
                .build();
        return getFindByIdResponse(filmEntity, request.getCategories(), request.getActorIds());
    }

    private FilmFindByIdResponse getFindByIdResponse(FilmEntity entity, Set<Category> categoryRequests, Set<Integer> actorIds) {
        FilmDto filmDto = filmRepository.save(entity).toDto();
        if (!categoryRequests.isEmpty()) {
            List<FilmCategoryEntity> filmCategories = filmCategoryRepository.saveAll(categoryRequests.stream().map(category -> {
                FilmCategoryEntity filmCategoryEntity = FilmCategoryEntity.builder()
                        .filmId(filmDto.getFilmId())
                        .categoryId(category.getId())
                        .build();
                return filmCategoryEntity;
            }).toList());

            List<Category> categories = filmCategories.stream().map(FilmCategoryEntity::getCategoryId).map(Category::from).toList();
            filmDto.setCategories(categories);
        }

        if (!actorIds.isEmpty()) {
            filmActorRepository.saveAll(actorIds.stream().map(actorId -> FilmActorEntity.builder()
                    .filmId(filmDto.getFilmId())
                    .actorId(actorId)
                    .build()).toList());

            List<ActorDto> actors = actorRepository.findAllById(actorIds).stream().map(ActorEntity::toDto).toList();
            filmDto.setActors(actors);
        }
        return FilmFindByIdResponse.builder()
                .film(filmDto)
                .build();
    }

    @Transactional
    public FilmFindByIdResponse update(FilmUpdateRequest request) {
        FilmEntity filmEntity = filmRepository.findById(request.getFilmId()).orElseThrow();
        filmEntity.setTitle(request.getTitle());
        filmEntity.setDescription(request.getDescription());
        filmEntity.setReleaseYear(request.getReleaseYear());
        filmEntity.setLanguage(request.getLanguage());
        filmEntity.setOriginalLanguage(request.getOriginalLanguage());
        filmEntity.setRentalDuration(request.getRentalDuration());
        filmEntity.setRentalRate(request.getRentalRate());
        filmEntity.setLength(request.getLength());
        filmEntity.setReplacementCost(request.getReplacementCost());
        filmEntity.setRating(request.getRating());
        filmEntity.setSpecialFeatures(request.getSpecialFeatures());
        return getFindByIdResponse(filmEntity, request.getCategories(), request.getActorIds());

    }

    @Transactional
    public FilmRemoveResponse delete(Integer id) {
        filmCategoryRepository.deleteAll(filmCategoryRepository.findAllByFilmId(id));
        filmActorRepository.deleteAll(filmActorRepository.findAllByFilmId(id));
        filmRepository.deleteById(id);
        return FilmRemoveResponse.builder()
                .message("Film with id " + id + " has been deleted")
                .build();
    }
}

