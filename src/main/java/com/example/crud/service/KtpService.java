package com.example.crud.service;

import com.example.crud.entity.Ktp;
import com.example.crud.repository.KtpRepository;
import com.example.crud.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KtpService {

    @Autowired
    private KtpRepository repository;

    @Autowired
    private ValidationUtil validationUtil;

    public List<Ktp> getAllKtp() {
        return repository.findAll();
    }

    public Optional<Ktp> getKtpById(Integer id) {
        return repository.findById(id);
    }

    public Ktp saveKtp(Ktp ktp) {
        validationUtil.validate(ktp);
        if (repository.findByNomorKtp(ktp.getNomorKtp()).isPresent()) {
            throw new RuntimeException("Nomor KTP sudah terdaftar!");
        }
        return repository.save(ktp);
    }

    public Ktp updateKtp(Integer id, Ktp ktpDetails) {
        // 1. Validasi dulu data yang dikirim dari Postman
        validationUtil.validate(ktpDetails);

        // 2. Cari data lama di database
        Ktp ktp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data KTP tidak ditemukan"));

        // 3. Update field nomorKtp
        ktp.setNomorKtp(ktpDetails.getNomorKtp());

        // 4. Update field lainnya
        ktp.setNamaLengkap(ktpDetails.getNamaLengkap());
        ktp.setAlamat(ktpDetails.getAlamat());
        ktp.setTanggalLahir(ktpDetails.getTanggalLahir());
        ktp.setJenisKelamin(ktpDetails.getJenisKelamin());

        // 5. Simpan perubahan ke database
        return repository.save(ktp);
    }

    public void deleteKtp(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ID tidak ditemukan");
        }
        repository.deleteById(id);
    }
}