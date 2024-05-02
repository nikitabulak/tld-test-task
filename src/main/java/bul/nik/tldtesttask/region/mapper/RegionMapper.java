package bul.nik.tldtesttask.region.mapper;

import bul.nik.tldtesttask.region.model.Region;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionMapper {
    Region getRegionById(long id);

    void createNewRegion(Region region);

    void updateRegion(Region region);

    void deleteRegion(long id);

    List<Region> getRegions(int from, int size);

}
