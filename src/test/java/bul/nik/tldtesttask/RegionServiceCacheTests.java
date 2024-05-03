package bul.nik.tldtesttask;

import bul.nik.tldtesttask.region.dto.RegionDto;
import bul.nik.tldtesttask.region.dto.RegionDtoMapper;
import bul.nik.tldtesttask.region.model.Region;
import bul.nik.tldtesttask.region.service.RegionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RegionServiceCacheTests {
    @Autowired
    private RegionService service;

    @Autowired
    private CacheManager cacheManager;

    @Test
    @Order(1)
    void cachingSingleRegionValues() {
        Region region = new Region(0, "testRegion", "tstRgn");
        service.saveRegion(RegionDtoMapper.toRegionDto(region));

        RegionDto regionDto = service.getRegionById(1L);
        RegionDto cachedRegionDto = cacheManager.getCache("regions").get(1L, RegionDto.class);

        Assertions.assertEquals(regionDto, cachedRegionDto);
    }

    @Test
    @Order(2)
    void cachingRegionListValues() {
        Region region = new Region(0, "testRegion1", "tstRgn1");
        service.saveRegion(RegionDtoMapper.toRegionDto(region));

        List<RegionDto> regionDtos = service.getRegions(0, 2);
        List<RegionDto> cachedRegionDtos = cacheManager.getCache("regionsLists").get(new SimpleKey(0, 2), List.class);

        Assertions.assertEquals(regionDtos, cachedRegionDtos);
    }

    @Test
    @Order(3)
    void regionListsCacheEvictsAfterSaveRegion() {
        Region region = new Region(0, "testRegion2", "tstRgn2");
        service.saveRegion(RegionDtoMapper.toRegionDto(region));

        assertNull(cacheManager.getCache("regionsLists").get(new SimpleKey(0, 2)));
    }

    @Test
    @Order(4)
    void regionListsCacheEvictsAfterChangeRegion() {
        List<RegionDto> regionDtos = service.getRegions(0, 2);
        RegionDto regionDto = service.getRegionById(1L);
        RegionDto regionDto1 = service.getRegionById(2L);

        Region region = new Region(1, "testRegionChanged", "tstRgnChd");
        service.changeRegion(RegionDtoMapper.toRegionDto(region));

        assertNull(cacheManager.getCache("regions").get(1L));
        assertNull(cacheManager.getCache("regionsLists").get(new SimpleKey(0, 2)));
        assertNotNull(cacheManager.getCache("regions").get(2L));
    }

    @Test
    @Order(5)
    void regionListsCacheEvictsAfterDeleteRegion() {
        List<RegionDto> regionDtos = service.getRegions(0, 2);
        RegionDto regionDto = service.getRegionById(1L);
        RegionDto regionDto1 = service.getRegionById(2L);

        service.deleteRegion(1L);

        assertNull(cacheManager.getCache("regions").get(1L));
        assertNull(cacheManager.getCache("regionsLists").get(new SimpleKey(0, 2)));
        assertNotNull(cacheManager.getCache("regions").get(2L));
    }
}
