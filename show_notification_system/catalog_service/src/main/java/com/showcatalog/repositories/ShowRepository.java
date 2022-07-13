package com.showcatalog.repositories;

import com.showcatalog.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Show getShowByNameLike(String name);
}
