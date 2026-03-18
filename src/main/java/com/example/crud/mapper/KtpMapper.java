package com.example.crud.mapper;

import com.example.crud.model.dto.KtpAddRequest;
import com.example.crud.model.dto.KtpDto;
import com.example.crud.model.entity.Ktp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Memberitahu Spring agar class ini bisa di-@Autowired
public interface KtpMapper {

    // Otomatis ubah Entity ke DTO (untuk output ke user)
    KtpDto toDto(Ktp ktp);

    // Otomatis ubah Request ke Entity (untuk input ke database)
    Ktp toEntity(KtpAddRequest request);
}