package com.sakila.repository;

import com.sakila.entity.FilmActorEntity;
import com.sakila.entity.FilmActorPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActorEntity, FilmActorPK> {
    List<FilmActorEntity> findAllByFilmId(Integer filmId);

    List<FilmActorEntity> findAllByActorId(Integer actorId);
}
