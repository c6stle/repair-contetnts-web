package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.base.core.exception.MessageException;
import webml.prj.dto.ManagerDto;
import webml.prj.dto.PartnerDto;
import webml.prj.entity.Manager;
import webml.prj.entity.Partner;
import webml.prj.repository.ManagerRepository;
import webml.prj.repository.PartnerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerService {

    private final PartnerRepository partnerRepository;

    private final ManagerRepository managerRepository;

    public List<PartnerDto> getPartnerList() {
        return partnerRepository.findByDelYn("N").stream().map(PartnerDto::new).collect(Collectors.toList());
    }

    public PartnerDto getPartnerInfo(Long partnerIdx) {
        return partnerRepository.findById(partnerIdx).map(PartnerDto::new).orElseThrow(() -> new MessageException("선택한 거래처를 찾을 수 없습니다."));
    }

    @Transactional
    public void regPartner(PartnerDto partnerDto) {
        Partner partner = Partner.builder()
                .partnerNm(partnerDto.getPartnerNm())
                .delYn("N")
                .build();

        partnerRepository.save(partner);

        partnerDto.getManagers().forEach(managerDto -> {
            Manager manager = Manager.builder()
                    .managerMail(managerDto.getManagerMail())
                    .partner(partner)
                    .build();
            managerRepository.save(manager);
        });
    }

    @Transactional
    public void updatePartner(PartnerDto partnerDto) {
        Partner partner = partnerRepository.findById(partnerDto.getPartnerIdx()).orElseThrow(() -> new MessageException("선택한 거래처를 찾을 수 없습니다."));
        partner.update(partnerDto.getPartnerNm());

        List<ManagerDto> managers = partnerDto.getManagers();
        managers.forEach(managerDto -> {
            if (managerDto.getManagerIdx() != null) {
                Manager manager = managerRepository.findById(managerDto.getManagerIdx()).orElseThrow(() -> new MessageException("담당자를 찾을 수 없습니다."));
                manager.update(managerDto.getManagerMail());
            } else {
                Manager manager = Manager.builder()
                        .managerMail(managerDto.getManagerMail())
                        .partner(partner)
                        .build();
                managerRepository.save(manager);
            }
        });
    }

    @Transactional
    public void deletePartner(Long partnerIdx) {
        Partner partner = partnerRepository.findById(partnerIdx).orElseThrow(() -> new MessageException("선택한 거래처를 찾을 수 없습니다."));
        partner.delete("Y");
    }

    @Transactional
    public void deleteManager(Long managerIdx) {
        managerRepository.deleteById(managerIdx);
    }

}
