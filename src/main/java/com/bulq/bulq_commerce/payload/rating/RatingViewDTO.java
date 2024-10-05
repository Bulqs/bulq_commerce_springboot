package com.bulq.bulq_commerce.payload.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingViewDTO {
    
    private Long id;

    private String username;

    private String userImage;

    private String comment;

    private String stars;
}
