package com.example.pasir_kochanski_cezary.services;

import com.example.pasir_kochanski_cezary.dto.GroupDTO;
import com.example.pasir_kochanski_cezary.model.Group;
import com.example.pasir_kochanski_cezary.model.Membership;
import com.example.pasir_kochanski_cezary.model.User;
import com.example.pasir_kochanski_cezary.repository.GroupRepository;
import com.example.pasir_kochanski_cezary.repository.MembershipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipService membershipService;

    public GroupService(GroupRepository groupRepository, MembershipRepository membershipRepository, MembershipService membershipService) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.membershipService = membershipService;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @MutationMapping
    public Group createGroup(GroupDTO groupDTO) {
        User owner = membershipService.getCurrentUser();
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setOwner(owner);
        Group savedGroup = groupRepository.save(group);
        Membership membership = new Membership();
        membership.setUser(owner);
        membership.setGroup(savedGroup);
        membershipRepository.save(membership);
        return savedGroup;
    }

    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Grupa o ID " + id + " nie istnieje.");
        }
        groupRepository.deleteById(id);
    }
}
