package com.studyolle.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = "id")
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

    @Builder
    public Zone(String city, String localNameOfCity, String province){
        this.city = city;
        this.localNameOfCity = localNameOfCity;
        this.province = province;
    }
}
