package bul.nik.tldtesttask.region.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Region {
    private long id;
    private String name;
    private String shortName;
}
