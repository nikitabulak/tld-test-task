package bul.nik.tldtesttask.region.controller;

import bul.nik.tldtesttask.region.dto.RegionDto;
import bul.nik.tldtesttask.region.dto.RegionDtoMapper;
import bul.nik.tldtesttask.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;

    @GetMapping("/{regionId}")
    public RegionDto getRegion(@PathVariable long regionId) {
        return regionService.getRegionById(regionId);
    }

    @GetMapping
    public List<RegionDto> getRegions(@RequestParam(required = false, defaultValue = "0") int from,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return regionService.getRegions(from, size);
    }

    @PostMapping
    public void saveRegion(@RequestBody RegionDto regionDto) {
        regionService.saveRegion(RegionDtoMapper.toRegion(regionDto));
    }

    @PatchMapping
    public void changeRegion(@RequestBody RegionDto regionDto) {
        regionService.changeRegion(RegionDtoMapper.toRegion(regionDto));
    }

    @DeleteMapping("/{regionId}")
    public void deleteRegion(@PathVariable long regionId) {
        regionService.deleteRegion(regionId);
    }
}
