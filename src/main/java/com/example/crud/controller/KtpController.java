package com.example.crud.controller;

import com.example.crud.mapper.KtpMapper;
import com.example.crud.model.dto.KtpAddRequest;
import com.example.crud.model.dto.KtpDto;
import com.example.crud.model.entity.Ktp;
import com.example.crud.service.KtpService;
import com.example.crud.util.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ktp")
public class KtpController {

    @Autowired
    private KtpService ktpService;

    @Autowired
    private KtpMapper ktpMapper;

    // GET ALL - Sekarang mengembalikan List KtpDto
    @GetMapping
    public WebResponse<List<KtpDto>> getAll() {
        List<KtpDto> listDto = ktpService.getAllKtp()
                .stream()
                .map(ktpMapper::toDto)
                .collect(Collectors.toList());
        return new WebResponse<>("Berhasil mengambil semua data", listDto);
    }

    // GET BY ID - Mengembalikan KtpDto tunggal
    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<KtpDto>> getById(@PathVariable Integer id) {
        return ktpService.getKtpById(id)
                .map(ktp -> ResponseEntity.ok(new WebResponse<>("Data ditemukan", ktpMapper.toDto(ktp))))
                .orElse(ResponseEntity.status(404).body(new WebResponse<>("Data tidak ditemukan", null)));
    }

    // POST - Menerima Entity, mengembalikan DTO
    @PostMapping
    public ResponseEntity<WebResponse<KtpDto>> create(@RequestBody KtpAddRequest request) {
        try {
            // Cukup panggil mapper.toEntity(request)
            Ktp ktp = ktpMapper.toEntity(request);

            Ktp savedKtp = ktpService.saveKtp(ktp);
            return ResponseEntity.ok(new WebResponse<>("Data KTP berhasil disimpan", ktpMapper.toDto(savedKtp)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new WebResponse<>(e.getMessage(), null));
        }
    }

    // PUT - Memperbarui data dan mengembalikan DTO hasil update
    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<?>> update(@PathVariable Integer id, @RequestBody Ktp ktp) {
        try {
            Ktp updatedKtp = ktpService.updateKtp(id, ktp);
            return ResponseEntity.ok(new WebResponse<>("Data KTP berhasil diperbarui", ktpMapper.toDto(updatedKtp)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new WebResponse<>(e.getMessage(), null));
        }
    }

    // DELETE - Pesan sukses tetap String
    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable Integer id) {
        try {
            ktpService.deleteKtp(id);
            return ResponseEntity.ok(new WebResponse<>("Data KTP berhasil dihapus", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new WebResponse<>(e.getMessage(), null));
        }
    }
}