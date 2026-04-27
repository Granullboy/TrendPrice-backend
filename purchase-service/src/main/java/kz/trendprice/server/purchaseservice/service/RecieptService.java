package kz.trendprice.server.purchaseservice.service;

import kz.trendprice.server.purchaseservice.entity.Reciept;
import kz.trendprice.server.purchaseservice.repository.RecieptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecieptService {
    private final RecieptRepository recieptRepository;

    public List<Reciept> getAllReciepts() { return recieptRepository.findAll(); }

    public Reciept getReciept(UUID id) { return recieptRepository.findById(id).orElse(null); }

    public Reciept createReciept(Reciept reciept) { return recieptRepository.save(reciept); }

    public Reciept updateReciept(UUID id, Reciept reciept) {
        Reciept oldReciept = recieptRepository.findById(id).orElse(null);
        if (oldReciept == null) return null;

        return recieptRepository.save(oldReciept);
    }

    public void deleteReciept(UUID id) { recieptRepository.deleteById(id); }
}
