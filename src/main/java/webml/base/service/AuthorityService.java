package webml.base.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.base.repository.AuthorityRepository;
import webml.base.dto.AuthorityDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public List<AuthorityDto> getAuthorities() {
        return authorityRepository.findAll().stream().map(AuthorityDto::new).collect(Collectors.toList());
    }
}

