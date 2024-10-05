package com.bulq.bulq_commerce.payload.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionViewDTO {

    private long id;

    private String name;

    private String description;

    private String billboard;

}