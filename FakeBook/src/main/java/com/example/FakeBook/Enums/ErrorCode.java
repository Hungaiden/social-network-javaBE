package com.example.FakeBook.Enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //Loi chung
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(9998, "Chua xac thuc", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(9997, "Khong duoc uy quyen", HttpStatus.FORBIDDEN),
    INVALID_KEY(9996, "Tu khoa khong hop le", HttpStatus.BAD_REQUEST),
    //Loi ton tai
    USER_EXIST(1001, "Nguoi dung da ton tai", HttpStatus.BAD_REQUEST),
    USERNAME_EXIST(1002, "Username da ton tai", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST(1003, "Email da ton tai", HttpStatus.BAD_REQUEST),
    FRIEND_REQUEST_EXIST(1004, "FriendRequest da ton tai", HttpStatus.BAD_REQUEST),
    //Loi khong hop le
    DISPLAYNAME_INVALID(2001, "Ten hien thi phai toi thieu 10 ky tu", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(2002, "Username phai toi thieu 10 ky tu", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2003, "Password phai toi thieu 8", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(2004, "Email khong hop le", HttpStatus.BAD_REQUEST),
    PAGE_SIZE_INVALID(1015, "Page size phai lon hon 0", HttpStatus.BAD_REQUEST),
    PAGE_INDEX_INVALID(1016, "Page index phai lon hon hoac bang 0", HttpStatus.BAD_REQUEST),
    //Loi khong dc null
    DISPLAYNAME_NOT_NULL(3001, "Ten hien thi khong duoc null", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_NULL(3002, "Username khong duoc null", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_NULL(3003, "Password khong duoc null", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_NULL(3004, "Email khong duoc null", HttpStatus.BAD_REQUEST),
    //Loi khong tim thay
    USER_NOT_FOUND(4001, "Khong tim thay User", HttpStatus.NOT_FOUND),
    FRIEND_REQUEST_NOT_FOUND(4002, "Khong tim thay FriendRequest", HttpStatus.NOT_FOUND),
    FRIEND_NOT_FOUND(4003, "Hai nguoi khong phai ban be", HttpStatus.NOT_FOUND),
    POST_NOT_FOUND(4004, "Khong tim thay Post", HttpStatus.NOT_FOUND),
    //Loi khac
    PASSWORD_DUPLICATED(1013, "Mat khong moi giong mat khau cu", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(1014, "Mat khau sai", HttpStatus.BAD_REQUEST),
    INVALID_UUID(1015, "Invalid UUID format", HttpStatus.BAD_REQUEST),
    ID_DUPLICATED(1016, "ID giong nhau", HttpStatus.BAD_REQUEST),
    ID_NULL(1017, "ID bi null", HttpStatus.BAD_REQUEST),

    // conversation : 1100-1199
    CONVERSATION_EXIST(1101, "Cuoc hoi thoai da ton tai", HttpStatus.BAD_REQUEST),
    CONVERSATION_NOT_FOUND(1102, "Khong tim thay cuoc hoi thoai", HttpStatus.BAD_REQUEST),
    DUPLICATE_MEMBERS(1103, "Trung member trong danh sach", HttpStatus.BAD_REQUEST),
    MINIMUM_MEMBERS_REQUIRED(1104, "Group chat phai co toi thieu 3 thanh vien", HttpStatus.BAD_REQUEST),
    INVALID_CONVERSATION_TYPE(1105, "Type conversation khong hop le", HttpStatus.BAD_REQUEST),
    PERMISSION_DENIED(1106, "Nguoi dung hien tai khong phai admin", HttpStatus.BAD_REQUEST),
    MESSAGE_EMPTY(1107, "Tin nhan rong", HttpStatus.BAD_REQUEST),
    //post, content: 1200-1299

    POST_TOO_FAST(1201,"Ban dang thao tac qua nhanh,thu lai sau 60s!!", HttpStatus.TOO_MANY_REQUESTS),
    DISPLAYTITLE_NOT_NULL(1202, "Tieu de khong duoc de trong", HttpStatus.BAD_REQUEST),
    DISPLAYTITLE_INVALID(1203, "Tieu de khong hop le (toi da 500 ky tu)", HttpStatus.BAD_REQUEST),
    DISPLAYCONTENT_INVALID(1204,"Noi dung khong dươc qua 5000 ky tu", HttpStatus.BAD_REQUEST),
    POST_EDIT_FORBIDDEN(1205,"Ban khong co quyen chinh sua bai viet", HttpStatus.FORBIDDEN),
    POST_DELETE_FORBIDDEN(1206,"Ban khong co quyen xoa bai viet", HttpStatus.FORBIDDEN),
    FRIEND_ALREADY(5003, "Hai nguoi da la ban be", HttpStatus.BAD_REQUEST)
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
