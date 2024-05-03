package bul.nik.tldtesttask;

import bul.nik.tldtesttask.exception.RegionNotFoundException;
import bul.nik.tldtesttask.region.dto.RegionDto;
import bul.nik.tldtesttask.region.dto.RegionDtoMapper;
import bul.nik.tldtesttask.region.model.Region;
import bul.nik.tldtesttask.region.repository.RegionRepository;
import bul.nik.tldtesttask.region.service.RegionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RegionControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionRepository regionRepository;

    @Test
    @Order(1)
    void canSaveNewRegion() throws Exception {
        RegionDto regionDto = new RegionDto(0, "testRegion", "tstRgn");

        mockMvc.perform(
                        post("/region")
                                .content(objectMapper.writeValueAsString(regionDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Region savedRegion = regionRepository.getRegionById(1).orElse(null);
        assertNotNull(savedRegion);
        assertThat(savedRegion.getName()).isEqualTo(regionDto.getName());
        assertThat(savedRegion.getShortName()).isEqualTo(regionDto.getShortName());
    }

    @Test
    @Order(2)
    void canGetRegionById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/region/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).contains("\"name\":\"testRegion\"");
        assertThat(response.getContentAsString()).contains("\"shortName\":\"tstRgn\"");
    }

    @Test
    @Order(3)
    void canGetRegionsListByFromAndSize() throws Exception {
        Region region1 = new Region(0, "testRegion1", "tstRgn1");
        Region region2 = new Region(0, "testRegion2", "tstRgn2");
        regionService.saveRegion(RegionDtoMapper.toRegionDto(region1));
        regionService.saveRegion(RegionDtoMapper.toRegionDto(region2));

        MockHttpServletResponse response = mockMvc.perform(
                        get("/region")
                                .param("from", "0")
                                .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<RegionDto> regionDtos = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<RegionDto>>() {
        });
        assertThat(regionDtos.size()).isEqualTo(3);

        MockHttpServletResponse response1 = mockMvc.perform(
                        get("/region")
                                .param("from", "1")
                                .param("size", "2"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<RegionDto> regionDtos1 = objectMapper.readValue(response1.getContentAsString(), new TypeReference<List<RegionDto>>() {
        });
        assertThat(regionDtos1.size()).isEqualTo(2);

        MockHttpServletResponse response2 = mockMvc.perform(
                        get("/region")
                                .param("from", "2")
                                .param("size", "1"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<RegionDto> regionDtos2 = objectMapper.readValue(response2.getContentAsString(), new TypeReference<List<RegionDto>>() {
        });
        assertThat(regionDtos2.size()).isEqualTo(1);
        assertThat(regionDtos2.get(0).getName()).isEqualTo(region2.getName());
    }

    @Test
    @Order(4)
    void canChangeExistingRegion() throws Exception {
        RegionDto regionDto = new RegionDto(1, "changedTestRegion", "chtstRgn");

        mockMvc.perform(
                        patch("/region")
                                .content(objectMapper.writeValueAsString(regionDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Region changedRegion = regionRepository.getRegionById(1).orElse(null);
        assertNotNull(changedRegion);
        assertThat(changedRegion.getName()).isEqualTo(regionDto.getName());
        assertThat(changedRegion.getShortName()).isEqualTo(regionDto.getShortName());
    }

    @Test
    @Order(5)
    void canDeleteRegionById() throws Exception {
        mockMvc.perform(
                        delete("/region/1"))
                .andExpect(status().isOk());
        assertThrows(RegionNotFoundException.class, () -> regionService.getRegionById(1));
    }

    @Test
    @Order(6)
    void responseWith404NotFoundStatus() throws Exception {
        mockMvc.perform(
                        get("/region/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void responseWith409ConflictStatusOnExistingName() throws Exception {
        RegionDto regionDto = new RegionDto(0, "testRegion1", "tstRgn11");

        mockMvc.perform(
                        post("/region")
                                .content(objectMapper.writeValueAsString(regionDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(8)
    void responseWith409ConflictStatusOnExistingShortName() throws Exception {
        RegionDto regionDto = new RegionDto(0, "testRegion11", "tstRgn1");

        mockMvc.perform(
                        post("/region")
                                .content(objectMapper.writeValueAsString(regionDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
