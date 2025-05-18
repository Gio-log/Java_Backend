package com.example.pasir_kochanski_cezary.services;

import com.example.pasir_kochanski_cezary.dto.GroupTransactionDTO;
import com.example.pasir_kochanski_cezary.model.Debt;
import com.example.pasir_kochanski_cezary.model.Group;
import com.example.pasir_kochanski_cezary.model.Membership;
import com.example.pasir_kochanski_cezary.model.User;
import com.example.pasir_kochanski_cezary.repository.DebtRepository;
import com.example.pasir_kochanski_cezary.repository.GroupRepository;
import com.example.pasir_kochanski_cezary.repository.MembershipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupTransactionService {

    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final DebtRepository debtRepository;

    public GroupTransactionService(GroupRepository groupRepository, MembershipRepository membershipRepository, DebtRepository debtRepository) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.debtRepository = debtRepository;
    }

    public void addGroupTransaction(GroupTransactionDTO dto, User currentUser) {
        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Nie znaleniono grupy"));

        List<Membership> members = membershipRepository.findByGroupId(group.getId());

        if (members.isEmpty()) {
            throw new IllegalArgumentException("Brak członków w grupie!");
        }
        double amountPerUser = dto.getAmount() / members.size();

        for (Membership member : members) {
            User debtor = member.getUser();
            if (!debtor.getId().equals(currentUser.getId())) {
                Debt debt = new Debt();
                debt.setDebtor(debtor);
                debt.setCreditor(currentUser);
                debt.setGroup(group);
                debt.setAmount(amountPerUser);
                debt.setTitle(dto.getTitle());
                debtRepository.save(debt);
            }
        }
    }
}
