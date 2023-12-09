package ru.skypro.socks_app.repository;

import ru.skypro.socks_app.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;


public interface SockRepository extends JpaRepository<Sock, Long> {

    @Query(value = "SELECT s.* FROM sock s  WHERE s.color=?1", nativeQuery = true)
    Collection<Sock> findByColor (String color);
    @Query(value = "SELECT s.* FROM sock s  WHERE s.color=?1 and s.cotton_part=?2", nativeQuery = true)
    Optional<Sock> findByColorAndCottonPart (String color, Integer cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM sock WHERE color = :color AND cotton_part = :cottonPart", nativeQuery = true)
    Integer findByCottonPartEquals(@Param("color") String color, @Param("cottonPart") Integer cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM sock WHERE color = :color AND cotton_part > :cottonPart", nativeQuery = true)
    Integer findByCottonPartMoreThan(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM sock WHERE color = :color AND cotton_part < :cottonPart", nativeQuery = true)
    Integer findByCottonPartLessThan(@Param("color") String color, @Param("cottonPart") int cottonPart);


}
