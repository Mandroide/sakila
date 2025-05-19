package com.sakila.repository;

import com.sakila.entity.FilmCategoryEntity;
import com.sakila.entity.FilmCategoryPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategoryEntity, FilmCategoryPK> {
    List<FilmCategoryEntity> findAllByFilmId(Integer filmId);
}
