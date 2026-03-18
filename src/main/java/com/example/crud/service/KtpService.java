package com.example.crud.service;

import com.example.crud.model.dto.KtpDto;
import com.example.crud.model.entity.Ktp;
import java.util.List;
import java.util.Optional;

public interface KtpService {
    List<Ktp> getAllKtp();
    Optional<Ktp> getKtpById(Integer id);
    Ktp saveKtp(Ktp ktp);
    Ktp updateKtp(Integer id, Ktp ktpDetails);
    void deleteKtp(Integer id);
}