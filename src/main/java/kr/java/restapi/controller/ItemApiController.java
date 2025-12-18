package kr.java.restapi.controller;

import jakarta.validation.Valid;
import kr.java.restapi.model.dto.ItemCreateRequest;
import kr.java.restapi.model.dto.ItemResponse;
import kr.java.restapi.model.dto.ItemUpdateRequest;
import kr.java.restapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// #(1)
//@Controller // String -> view를 return
@RestController // -> 일괄적으로 모든 접근하는 요청에 대해서 'ResponseBody'로 답하겠다
// View (thymeleaf, jsp X -> JSON, 데이터 형태로 응답하겠다)
@RequestMapping("/api/items") // 일반적으로 rest api -> '복수'를 사용함.
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;

//    @GetMapping
//    String index() {
//        return "index"; // view
//    }
//        @GetMapping("/hello")
////        @ResponseBody
//        public String hello() { // int, long, ...
//            return "hello";
//        }
//
//        @GetMapping("/record")
////        @ResponseBody
//        public ItemResponse item() { // class, record -> 객체 -> JSON
//            return new ItemResponse(1L, "1", 1, "1", Instant.now(), Instant.now());
//        }

    // https://developer.mozilla.org/ko/docs/Web/HTTP/Reference/Status
    // https://http.cat/
    // 항목 추가
    @PostMapping // ("/api/items")
    // request <- name, price, description
//    @ResponseStatus(HttpStatus.CREATED) // 하나의 메서드가 다양한 status를 가져야한다면?
//    public ItemResponse create(
    public ResponseEntity<ItemResponse> create(
            @Valid @RequestBody ItemCreateRequest request
    ) {
//        response.setStatus(201); // 패러미터가 지저분해짐.
//        return itemService.create(request);
            ItemResponse response = itemService.create(request);
        return
                ResponseEntity
//                        .status(201)
                        .status(HttpStatus.CREATED)
                        .body(response);
    }

    // 전체 읽기
    @GetMapping // ("/api/items")
    public ResponseEntity<List<ItemResponse>> findAll() {
        List<ItemResponse> response = itemService.findAll();
//        return ResponseEntity
//                .status(200)
//                .status(HttpStatus.OK)
//                .body(response);
        return ResponseEntity.ok(response);
    }

    // 개별 읽기
    @GetMapping("/{id}") // /api/items/{id}
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        ItemResponse response = itemService.findById(id);
        return ResponseEntity.ok(response);
    }

    // https://developer.mozilla.org/ko/docs/Web/HTTP/Reference/Methods
    @DeleteMapping("/{id}") // /api/items/{id}
    // -> Body X.
    // RestfulAPI -> Model X. Model, ModelAttribute -> Restful API X.
    // RequestParm (queryString), RequestBody (body)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}") // /api/items/{id}
    public ResponseEntity<ItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ItemUpdateRequest request
    ) {
        ItemResponse response = itemService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<ItemResponse> patch(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        ItemResponse oldItem = itemService.findById(id);
        ItemUpdateRequest request = new ItemUpdateRequest(name, oldItem.price(), oldItem.description());
        ItemResponse response = itemService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
