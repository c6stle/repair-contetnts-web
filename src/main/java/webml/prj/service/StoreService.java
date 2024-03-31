package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.prj.dto.StoreDto;
import webml.prj.repository.StoreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    public List<StoreDto> getStoreList() {
        return storeRepository.findAll().stream().map(StoreDto::new).collect(Collectors.toList());
    }
}
