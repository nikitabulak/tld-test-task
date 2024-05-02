package bul.nik.tldtesttask.region.dto;

import bul.nik.tldtesttask.region.model.Region;

public class RegionDtoMapper {
    public static Region toRegion(RegionDto regionDto) {
        return Region.builder()
                .id(regionDto.getId())
                .name(regionDto.getName())
                .shortName(regionDto.getShortName())
                .build();
    }

    public static RegionDto toRegionDto(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .name(region.getName())
                .shortName(region.getShortName())
                .build();
    }
}
