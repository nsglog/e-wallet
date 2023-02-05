package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Override
    Wallet save (Wallet wallet);
    Optional<Wallet> getWalletByUserId(long id);

}
