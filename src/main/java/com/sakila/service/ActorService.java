package com.sakila.service;

import com.sakila.dto.*;
import com.sakila.entity.ActorEntity;
import com.sakila.repository.ActorRepository;
import com.sakila.repository.FilmActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ActorService {
    private final FilmActorRepository filmActorRepository;
    private final ActorRepository actorRepository;

    public ActorFindAllResponse findAll(ActorFindAllRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<ActorDto> page = actorRepository.findAll(pageRequest).map(ActorEntity::toDto);
        List<ActorDto> content = page.getContent();

        return ActorFindAllResponse.builder()
                .page(request.getPageNumber())
                .totalPages(page.getTotalPages())
                .totalResults(page.getNumberOfElements())
                .actors(content)
                .build();
    }

    public ActorFindByIdResponse findById(Integer id) {
        return ActorFindByIdResponse.builder()
                .actor(actorRepository.findById(id).map(ActorEntity::toDto).orElseThrow())
                .build();
    }


    @Transactional
    public ActorFindByIdResponse create(ActorCreateRequest request) {
        ActorEntity entity = ActorEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        ActorDto actorDto = actorRepository.save(entity).toDto();
        return ActorFindByIdResponse.builder()
                .actor(actorDto)
                .build();
    }

    @Transactional
    public ActorFindByIdResponse update(ActorUpdateRequest request) {
        ActorEntity entity = actorRepository.findById(request.getActorId()).orElseThrow();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        ActorDto actorDto = actorRepository.save(entity).toDto();
        return ActorFindByIdResponse.builder()
                .actor(actorDto)
                .build();
    }

    @Transactional
    public ActorRemoveResponse delete(Integer id) {
        filmActorRepository.deleteAll(filmActorRepository.findAllByFilmId(id));
        actorRepository.deleteById(id);
        return ActorRemoveResponse.builder()
                .message("Actor with id " + id + " has been deleted")
                .build();
    }
}

