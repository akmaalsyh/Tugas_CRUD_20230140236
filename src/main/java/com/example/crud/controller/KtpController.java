package com.example.crud.controller;

import com.example.crud.entity.Ktp;
import com.example.crud.service.KtpService;
import com.example.crud.util.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ktp")
public class KtpController {

    @Autowired
    private KtpService ktpService;

    @GetMapping
    public WebResponse<List<Ktp>> getAll() {
        return new WebResponse<>("Berhasil mengambil semua data", ktpService.getAllKtp());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<Ktp>> getById(@PathVariable Integer id) {
        return ktpService.getKtpById(id)
                .map(ktp -> ResponseEntity.ok(new WebResponse<>("Data ditemukan", ktp)))
                .orElse(ResponseEntity.status(404).body(new WebResponse<>("Data tidak ditemukan", null)));
    }

    @PostMapping
    public ResponseEntity<WebResponse<?>> create(@RequestBody Ktp ktp) {
        try {
            return ResponseEntity.ok(new WebResponse<>("Data KTP berhasil disimpan", ktpService.saveKtp(ktp)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new WebResponse<>(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<?>> update(@PathVariable Integer id, @RequestBody Ktp ktp) {
        try {
            return ResponseEntity.ok(new WebResponse<>("Data KTP berhasil diperbarui", ktpService.updateKtp(id, ktp)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new WebResponse<>(e.getMessage(), null));
        }
    }

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