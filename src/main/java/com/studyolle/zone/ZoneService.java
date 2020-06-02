package com.studyolle.zone;

import com.studyolle.domain.Account;
import com.studyolle.domain.AccountZone;
import com.studyolle.domain.Zone;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional
@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final AccountZoneRepository accountZoneRepository;

    @PostConstruct
    public void initZoneData() throws IOException {
        if(zoneRepository.count() == 0){
            Resource resource = new ClassPathResource("zones_kr.csv");
            List<Zone> zoneList = Files.readAllLines(resource.getFile().toPath(),
                    StandardCharsets.UTF_8).stream()
                    .map(line -> {
                        String[] split = line.split(",");
                        return Zone.builder()
                                .city(split[0])
                                .localNameOfCity(split[1])
                                .province(split[2]).build();
                    }).collect(toList());
            zoneRepository.saveAll(zoneList);
        }
    }

    public AccountZone addZone(Account account, Zone zone) {
        Zone selectZone = zoneRepository.findByCityAndProvince(zone.getCity(), zone.getProvince())
                .orElseGet(() -> zoneRepository.save(zone));
        return accountZoneRepository.save(AccountZone.builder()
                .account(account).zone(selectZone).build());
    }

    @Transactional(readOnly = true)
    public List<String> getZones(Account account){
        return accountZoneRepository.findAccountZoneByAccount(account).stream()
                .map(accountZone -> accountZone.getZone().toString()).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<String> getAllZones(){
        return zoneRepository.findAllByNameAsc().stream()
                .map(Zone::toString).collect(toList());
    }

    public boolean removeZone(Account account, Zone zone) {
        Optional<Zone> findZone =
                zoneRepository.findByCityAndProvince(zone.getCity(), zone.getProvince());
        if(findZone.isPresent()){
            Optional<AccountZone> accountZone =
                    accountZoneRepository.findAccountZoneByAccountAndZone(account, findZone.get());
            if(accountZone.isPresent()){
                accountZoneRepository.delete(accountZone.get());
                return true;
            }
        }
        return false;
    }
}
