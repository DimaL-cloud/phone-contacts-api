package ua.dmytrolutsyuk.phonecontactsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dmytrolutsyuk.phonecontactsapi.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    void deleteById(Integer tokenId);
}