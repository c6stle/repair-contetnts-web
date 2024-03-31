package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.prj.dto.PartnerDto;
import webml.prj.repository.PartnerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public List<PartnerDto> getPartnerList() {
        return partnerRepository.findAll().stream().map(PartnerDto::new).collect(Collectors.toList());
    }

}
