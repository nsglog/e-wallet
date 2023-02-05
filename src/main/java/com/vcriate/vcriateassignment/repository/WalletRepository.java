package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Override
    Wallet save (Wallet wallet);
    Wallet getWalletByUserId(long id);

}
