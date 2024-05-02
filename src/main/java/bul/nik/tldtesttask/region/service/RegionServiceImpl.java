package bul.nik.tldtesttask.region.service;

import bul.nik.tldtesttask.exception.RegionNotFoundException;
import bul.nik.tldtesttask.region.dto.RegionDto;
import bul.nik.tldtesttask.region.dto.RegionDtoMapper;
import bul.nik.tldtesttask.region.model.Region;
import bul.nik.tldtesttask.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;


    @Override
    @Cacheable("regions")
    public RegionDto getRegionById(long id) {
        log.info(String.format("Getting region with id = %d", id));
        return RegionDtoMapper.toRegionDto(regionRepository.getRegionById(id).orElseThrow(() -> new RegionNotFoundException(id)));
    }

    @Override
    @Cacheable(value = "regionsLists")
    public List<RegionDto> getRegions(int from, int size) {
        log.info(String.format("Getting regions: from=%d, size = %d", from, size));
        return regionRepository.getRegions(from, size).stream().map(RegionDtoMapper::toRegionDto).toList();
    }

    @Override
    @CacheEvict(value = "regionsLists", allEntries = true)
    public void saveRegion(Region region) {
        regionRepository.createNewRegion(region);
        log.info(String.format("Saving region: %s", region));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions", key = "#region.id"),
            @CacheEvict(value = "regionsLists", allEntries = true)
    })
    public void changeRegion(Region region) {
        regionRepository.updateRegion(region);
        log.info(String.format("Changing region: %s", region));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions"),
            @CacheEvict(value = "regionsLists", allEntries = true)
    })
    public void deleteRegion(long id) {
        log.info(String.format("Deleting region with id = %d", id));
        regionRepository.deleteRegion(id);
    }
}
