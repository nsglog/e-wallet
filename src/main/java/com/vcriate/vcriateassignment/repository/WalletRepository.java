package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet save (Wallet wallet);
    Optional<Wallet> getWalletByUserId(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select from wallet where user_id = ?1", nativeQuery = true)
    Optional<Wallet> getWalletByUserIdForUpdate (long id);

}
