package bul.nik.tldtesttask.region.repository;

import bul.nik.tldtesttask.region.model.Region;

import java.util.List;
import java.util.Optional;


public interface RegionRepository {

    Optional<Region> getRegionById(long id);

    void createNewRegion(Region region);

    void updateRegion(Region region);

    void deleteRegion(long id);

    List<Region> getRegions(int from, int size);

}

