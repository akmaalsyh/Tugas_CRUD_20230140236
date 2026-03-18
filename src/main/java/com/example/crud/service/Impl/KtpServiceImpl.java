package com.example.crud.service.Impl;

import com.example.crud.model.entity.Ktp;
import com.example.crud.repository.KtpRepository;
import com.example.crud.service.KtpService;
import com.example.crud.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KtpServiceImpl implements KtpService {

    @Autowired
    private KtpRepository repository;

    @Autowired
    private ValidationUtil validationUtil;

    @Override
    public List<Ktp> getAllKtp() { return repository.findAll(); }

    @Override
    public Optional<Ktp> getKtpById(Integer id) { return repository.findById(id); }

    @Override
    public Ktp saveKtp(Ktp ktp) {
        validationUtil.validate(ktp);
        if (repository.findByNomorKtp(ktp.getNomorKtp()).isPresent()) {
            throw new RuntimeException("Nomor KTP sudah terdaftar!");
        }
        return repository.save(ktp);
    }

    @Override
    public Ktp updateKtp(Integer id, Ktp ktpDetails) {
        validationUtil.validate(ktpDetails);
        Ktp ktp = repository.findById(id).orElseThrow(() -> new RuntimeException("Data tidak ditemukan"));
        ktp.setNomorKtp(ktpDetails.getNomorKtp());
        ktp.setNamaLengkap(ktpDetails.getNamaLengkap());
        ktp.setAlamat(ktpDetails.getAlamat());
        ktp.setTanggalLahir(ktpDetails.getTanggalLahir());
        ktp.setJenisKelamin(ktpDetails.getJenisKelamin());
        return repository.save(ktp);
    }

    @Override
    public void deleteKtp(Integer id) {
        if (!repository.existsById(id)) throw new RuntimeException("ID tidak ditemukan");
        repository.deleteById(id);
    }
}