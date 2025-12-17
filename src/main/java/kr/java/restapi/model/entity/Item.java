package kr.java.restapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 엔티티
 * - JPA 영속성 모델 (DB 테이블과 매핑)
 * - API 응답에는 DTO 사용 (Entity 직접 노출 금지)
 */
@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 500)
    private String description;

    // 생성자 (Builder 패턴)
    @Builder
    public Item(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // 비즈니스 메서드: 엔티티 수정
    public void update(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
