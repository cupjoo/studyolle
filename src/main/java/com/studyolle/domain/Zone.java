package com.studyolle.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@EqualsAndHashCode(of = "id")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"city", "province"}))
@NoArgsConstructor
@Entity
public class Zone {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String localNameOfCity;

    @Column(nullable = true)
    private String province;

    @OneToMany(mappedBy = "zone")
    private List<AccountZone> accountZones = new ArrayList<>();

    @Builder
    public Zone(String city, String localNameOfCity, String province){
        this.city = city;
        this.localNameOfCity = localNameOfCity;
        this.province = province;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)/%s", city, localNameOfCity, province);
    }
}
