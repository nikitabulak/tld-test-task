package bul.nik.tldtesttask.region.service;

import bul.nik.tldtesttask.region.dto.RegionDto;
import bul.nik.tldtesttask.region.model.Region;

import java.util.List;

public interface RegionService {
    RegionDto getRegionById(long id);

    void saveRegion(Region region);

    void changeRegion(Region region);

    void deleteRegion(long id);

    List<RegionDto> getRegions(int from, int size);
}
