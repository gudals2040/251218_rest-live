package kr.java.restapi.exception;

// #(3)-2
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    // NoSuchElementException -> NotFoundException
    public static NotFoundException forItem(Long itemId) {
        return new NotFoundException("상품을 찾을 수 없습니다. ID : " + itemId);
    }

    public static NotFoundException forFile(Long fileId) {
        return new NotFoundException("파일을 찾을 수 없습니다. ID : " + fileId);
    }
}
