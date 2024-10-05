package com.bulq.bulq_commerce.payload.order;

import com.bulq.bulq_commerce.util.constants.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderPayloadDTO {

    @Schema(description = "Input status of the item", defaultValue = "PENDING, NEW, CANCELLED, COMPLETED")
    Status status;
}
