package bul.nik.tldtesttask.region.repository;

import bul.nik.tldtesttask.region.mapper.RegionMapper;
import bul.nik.tldtesttask.region.model.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryMyBatis implements RegionRepository {

    private final RegionMapper regionMapper;

    @Override
    public Optional<Region> getRegionById(long id) {
        return Optional.ofNullable(regionMapper.getRegionById(id));
    }

    @Override
    public List<Region> getRegions(int from, int size) {
        return regionMapper.getRegions(from, size);
    }

    @Override
    public void createNewRegion(Region region) {
        regionMapper.createNewRegion(region);
    }

    @Override
    public void updateRegion(Region region) {
        regionMapper.updateRegion(region);
    }

    @Override
    public void deleteRegion(long id) {
        regionMapper.deleteRegion(id);
    }
}
