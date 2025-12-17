package kr.java.restapi.model.dto;

import kr.java.restapi.model.entity.Item;

import java.time.Instant;

/**
 * 상품 응답 DTO
 */
public record ItemResponse(
        Long id,
        String name,
        Integer price,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
    // Entity → DTO 변환 (정적 팩토리 메서드)
    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}