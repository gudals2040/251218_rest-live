package kr.java.restapi.model.repository;

import kr.java.restapi.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Item 저장소
 * - JpaRepository 상속으로 기본 CRUD 자동 제공
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    // save(), findById(), findAll(), deleteById() 등 자동 제공
}