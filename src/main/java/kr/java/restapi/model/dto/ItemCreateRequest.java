package kr.java.restapi.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.java.restapi.model.entity.Item;

/**
 * 상품 생성 요청 DTO
 * - Record: 불변 객체, getter/equals/hashCode/toString 자동 생성
 */
public record ItemCreateRequest(

        @NotBlank(message = "상품명은 필수입니다")
        @Size(max = 100, message = "상품명은 100자 이하로 입력해주세요")
        String name,

        @NotNull(message = "가격은 필수입니다")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다")
        Integer price,

        @Size(max = 500, message = "설명은 500자 이하로 입력해주세요")
        String description
) {
    // DTO → Entity 변환
    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();
    }
}