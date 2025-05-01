package com.quickpick.backend.auth.services;

import com.quickpick.backend.auth.entities.Authority;
import com.quickpick.backend.auth.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority createAuthority(String roleCode, String roleDescription) {
        Authority newAuthority = Authority.builder()
                                        .roleCode(roleCode)
                                        .roleDescription(roleDescription)
                                        .build();

        return authorityRepository.save(newAuthority);
    }


    public List<Authority> getAuthorityObjects(List<String> roleCodes) {
        List<Authority> authorities = new ArrayList<>();

        for (String role : roleCodes) {
            String roleUpdate = role.toUpperCase();
            Authority authority = authorityRepository.findByRoleCode(roleUpdate);

            if (authority != null) {
                authorities.add(authority);
            }

            else {
                System.out.println("Role not found: " + roleUpdate);
            }
        }

        return authorities;
    }


    public List<Authority> getUserAuthority() {
        List<Authority> authorities = new ArrayList<>();
        Authority authority = authorityRepository.findByRoleCode("USER");
        if (authority != null) {
            authorities.add(authority);
        }
        return authorities;
    }
}
