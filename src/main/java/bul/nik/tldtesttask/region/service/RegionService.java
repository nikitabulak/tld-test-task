package bul.nik.tldtesttask.region.service;

import bul.nik.tldtesttask.region.dto.RegionDto;

import java.util.List;

public interface RegionService {
    RegionDto getRegionById(long id);

    void saveRegion(RegionDto regionDto);

    void changeRegion(RegionDto regionDto);

    void deleteRegion(long id);

    List<RegionDto> getRegions(int from, int size);
}
