package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webml.prj.repository.RepairRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repository;



}
