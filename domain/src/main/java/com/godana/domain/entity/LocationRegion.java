package com.godana.domain.entity;

import com.godana.domain.dto.locationRegion.LocationRegionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="location_region")
@Accessors(chain = true)
public class LocationRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_id")
    private String provinceId;
    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "district_id")
    private String districtId;
    @Column(name = "district_name")
    private String districtName;

    @Column(name = "ward_id")
    private String wardId;
    @Column(name = "ward_name")
    private String wardName;

    private String address;

    public LocationRegionDTO toLocationRegionDTO(){
        return new LocationRegionDTO()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address);
    }

}
