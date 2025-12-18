package kr.java.restapi.controller;

import kr.java.restapi.model.dto.ItemCreateRequest;
import kr.java.restapi.model.dto.ItemResponse;
import kr.java.restapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping // ("/api/items")
    // request <- name, price, description
//    @ResponseStatus(HttpStatus.CREATED) // 하나의 메서드가 다양한 status를 가져야한다면?
//    public ItemResponse create(
    public ResponseEntity<ItemResponse> create(
            // body -> parameter
            @RequestBody ItemCreateRequest request
//            , HttpServletResponse response
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
}
