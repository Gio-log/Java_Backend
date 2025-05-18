package com.example.pasir_kochanski_cezary.services;

import com.example.pasir_kochanski_cezary.dto.DebtDTO;
import com.example.pasir_kochanski_cezary.model.Debt;
import com.example.pasir_kochanski_cezary.model.Group;
import com.example.pasir_kochanski_cezary.model.User;
import com.example.pasir_kochanski_cezary.repository.DebtRepository;
import com.example.pasir_kochanski_cezary.repository.GroupRepository;
import com.example.pasir_kochanski_cezary.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class DebtService {

    private final DebtRepository debtRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public DebtService(DebtRepository debtRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.debtRepository = debtRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Debt> getGroupDebts(Long groupId) {
        return debtRepository.findByGroupId(groupId);
    }

    public Debt createDebt(DebtDTO debtDTO) {
        Group group = groupRepository.findById(debtDTO.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono grupy o ID: " + debtDTO.getGroupId()));
        User debtor = userRepository.findById(debtDTO.getDebtorId()).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono dłużnika o ID: " + debtDTO.getDebtorId()));
        User creditor = userRepository.findById(debtDTO.getCreditorId()).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono wierzyciela o ID: " + debtDTO.getCreditorId()));

        Debt debt = new Debt();
        debt.setGroup(group);
        debt.setDebtor(debtor);
        debt.setCreditor(creditor);
        debt.setAmount(debtDTO.getAmount());
        debt.setTitle(debtDTO.getTitle());

        return debtRepository.save(debt);
    }
}
