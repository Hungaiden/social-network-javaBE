# FakeBook API Documentation

## Mục lục
- [REST API](#rest-api)
  - [Authentication APIs](#authentication-apis)
  - [User APIs](#user-apis)
  - [Post APIs](#post-apis)
  - [Friend APIs](#friend-apis)
  - [Friend Request APIs](#friend-request-apis)
  - [Conversation APIs](#conversation-apis)
- [WebSocket APIs](#websocket-apis)
  - [Kết nối WebSocket](#kết-nối-websocket)
  - [WebSocket Topics](#websocket-topics)

---

## REST API

### Base URL
```
http://localhost:8080/api/v1
```

### Authentication
Hầu hết các API yêu cầu JWT token trong header:
```
Authorization: Bearer <token>
```

---

## Authentication APIs

### 1. Đăng nhập
**Endpoint:** `POST /api/v1/auth/login`

**Mô tả:** Xác thực người dùng và trả về JWT token

**Security:** Public endpoint

**Request Body:**
```json
{
  "email": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "string",
  "Result": {
    "token": "string",
    "authenticated": true
  }
}
```

---

### 2. Đăng xuất
**Endpoint:** `POST /api/v1/auth/logout`

**Mô tả:** Đăng xuất người dùng và vô hiệu hóa token

**Security:** Yêu cầu Bearer Token

**Response:**
```json
{
  "code": 1000,
  "message": "Logout thanh cong!"
}
```

---

### 3. Lấy thông tin cá nhân
**Endpoint:** `GET /api/v1/auth/myInfo`

**Mô tả:** Lấy thông tin chi tiết của người dùng đang đăng nhập

**Security:** Yêu cầu Bearer Token

**Response:**
```json
{
  "code": 1000,
  "message": "Lay thanh cong thong tin nguoi dung",
  "Result": {
    "userId": "uuid",
    "username": "string",
    "email": "string",
    "displayName": "string",
    "role": "string"
  }
}
```

---

## User APIs

### 1. Tạo người dùng mới
**Endpoint:** `POST /api/v1/user`

**Mô tả:** Tạo tài khoản người dùng mới

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "displayName": "string"
}
```

**Response:**
```json
{
  "code": 1000,
  "Result": {
    "userId": "uuid",
    "username": "string",
    "email": "string",
    "displayName": "string"
  }
}
```

---

### 2. Cập nhật thông tin người dùng
**Endpoint:** `PUT /api/v1/user`

**Mô tả:** Cập nhật thông tin người dùng

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "displayName": "string",
  "email": "string"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "Cap nhat thanh cong thong tin nguoi dung",
  "Result": {
    "userId": "uuid",
    "username": "string",
    "email": "string",
    "displayName": "string"
  }
}
```

---

### 3. Tìm kiếm người dùng
**Endpoint:** `GET /api/v1/user/search?email={email}`

**Mô tả:** Tìm kiếm người dùng theo email

**Security:** Yêu cầu Bearer Token

**Query Parameters:**
- `email` (required): Email của người dùng cần tìm

**Response:**
```json
{
  "code": 1000,
  "message": "Tim kiem thanh cong!",
  "Result": {
    "userId": "uuid",
    "username": "string",
    "email": "string",
    "displayName": "string"
  }
}
```

---

### 4. Lấy danh sách tất cả người dùng
**Endpoint:** `GET /api/v1/user`

**Mô tả:** Lấy danh sách tất cả người dùng (chỉ ADMIN)

**Security:** Yêu cầu Bearer Token + Role ADMIN

**Response:**
```json
{
  "code": 1000,
  "Result": [
    {
      "userId": "uuid",
      "username": "string",
      "email": "string",
      "displayName": "string"
    }
  ]
}
```

---

### 5. Lấy danh sách bài viết của người dùng
**Endpoint:** `GET /api/v1/user/{userId}/post`

**Mô tả:** Lấy danh sách bài viết của một người dùng cụ thể (phân trang)

**Security:** Yêu cầu Bearer Token

**Path Parameters:**
- `userId` (required): UUID của người dùng

**Query Parameters:**
- `page` (optional): Số trang (default: 0)
- `size` (optional): Kích thước trang (default: 20)
- `sort` (optional): Sắp xếp (ví dụ: createdAt,desc)

**Response:**
```json
{
  "code": 1000,
  "Result": {
    "content": [
      {
        "postId": "uuid",
        "content": "string",
        "createdAt": "timestamp",
        "owner": {
          "userId": "uuid",
          "displayName": "string"
        }
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 100,
    "totalPages": 5
  }
}
```

---

## Post APIs

### 1. Tạo bài viết mới
**Endpoint:** `POST /api/v1/post`

**Mô tả:** Đăng một bài viết mới

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "content": "string",
  "visibility": "PUBLIC|FRIENDS|PRIVATE"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "Dang bai thanh cong",
  "Result": {
    "postId": "uuid",
    "content": "string",
    "createdAt": "timestamp",
    "owner": {
      "userId": "uuid",
      "displayName": "string"
    }
  }
}
```

**WebSocket Notification:** Khi tạo bài viết thành công, tất cả bạn bè sẽ nhận được thông báo qua WebSocket tại `/user/{userId}/queue/new-post`

---

### 2. Cập nhật bài viết
**Endpoint:** `PATCH /api/v1/post/{postId}`

**Mô tả:** Cập nhật nội dung bài viết (chỉ chủ bài viết hoặc ADMIN)

**Security:** Yêu cầu Bearer Token

**Path Parameters:**
- `postId` (required): UUID của bài viết

**Request Body:**
```json
{
  "content": "string",
  "visibility": "PUBLIC|FRIENDS|PRIVATE"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "Cap nhat bai viet thanh cong",
  "Result": {
    "postId": "uuid",
    "content": "string",
    "updatedAt": "timestamp"
  }
}
```

---

### 3. Xóa bài viết
**Endpoint:** `DELETE /api/v1/post/{postId}`

**Mô tả:** Xóa bài viết (chỉ chủ bài viết hoặc ADMIN)

**Security:** Yêu cầu Bearer Token

**Path Parameters:**
- `postId` (required): UUID của bài viết

**Response:**
```json
{
  "code": 1000,
  "message": "Xoa bai viet thanh cong"
}
```

---

### 4. Xem chi tiết bài viết
**Endpoint:** `GET /api/v1/post/{postId}`

**Mô tả:** Lấy thông tin chi tiết của một bài viết

**Security:** Yêu cầu Bearer Token

**Path Parameters:**
- `postId` (required): UUID của bài viết

**Response:**
```json
{
  "code": 1000,
  "message": "Tim kiem thanh cong",
  "Result": {
    "postId": "uuid",
    "content": "string",
    "createdAt": "timestamp",
    "owner": {
      "userId": "uuid",
      "displayName": "string"
    },
    "reactions": [],
    "comments": []
  }
}
```

**Note:** Chỉ có thể xem bài viết của bạn bè hoặc bài viết công khai

---

### 5. Lấy tất cả bài viết (Admin)
**Endpoint:** `GET /api/v1/post`

**Mô tả:** Lấy danh sách tất cả bài viết (chỉ ADMIN, có phân trang)

**Security:** Yêu cầu Bearer Token + Role ADMIN

**Query Parameters:**
- `page` (optional): Số trang (default: 0)
- `size` (optional): Kích thước trang (default: 20)
- `sort` (optional): Sắp xếp

**Response:**
```json
{
  "code": 1000,
  "Result": {
    "content": [...],
    "page": 0,
    "size": 20,
    "totalElements": 500,
    "totalPages": 25
  }
}
```

---

## Friend APIs

### 1. Lấy danh sách bạn bè
**Endpoint:** `GET /api/v1/friends/myFriend`

**Mô tả:** Lấy danh sách tất cả bạn bè của người dùng hiện tại (phân trang)

**Security:** Yêu cầu Bearer Token

**Query Parameters:**
- `page` (optional): Số trang
- `size` (optional): Kích thước trang

**Response:**
```json
{
  "code": 1000,
  "message": "ok",
  "Result": {
    "content": [
      {
        "userId": "uuid",
        "displayName": "string",
        "email": "string"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 50,
    "totalPages": 3
  }
}
```

---

### 2. Xóa bạn bè
**Endpoint:** `DELETE /api/v1/friends?friendID={friendID}`

**Mô tả:** Hủy kết bạn với một người dùng

**Security:** Yêu cầu Bearer Token

**Query Parameters:**
- `friendID` (required): UUID của người bạn cần xóa

**Response:**
```json
{
  "code": 1000,
  "message": "Xoa ban be thanh cong!"
}
```

**WebSocket Notification:** Người bạn bị xóa sẽ nhận được thông báo qua WebSocket tại `/user/{userId}/queue/friend-delete`

---

## Friend Request APIs

### 1. Gửi lời mời kết bạn
**Endpoint:** `POST /api/v1/friend-request`

**Mô tả:** Gửi lời mời kết bạn tới một người dùng

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "receiverId": "uuid",
  "message": "string"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "ok",
  "Result": {
    "requestId": "uuid",
    "senderId": "uuid",
    "receiverId": "uuid",
    "status": "PENDING",
    "message": "string",
    "createdAt": "timestamp"
  }
}
```

**WebSocket Notification:** Người nhận sẽ nhận được thông báo qua WebSocket tại `/user/{userId}/queue/friend-request`

---

### 2. Phản hồi lời mời kết bạn
**Endpoint:** `POST /api/v1/friend-request/response`

**Mô tả:** Chấp nhận hoặc từ chối lời mời kết bạn

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "requestId": "uuid",
  "receiverId": "uuid",
  "action": "ACCEPT|REJECT"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "ok",
  "Result": {
    "requestId": "uuid",
    "status": "ACCEPTED|REJECTED"
  }
}
```

**WebSocket Notification:** Người gửi lời mời sẽ nhận được thông báo qua WebSocket tại `/user/{userId}/queue/friend-response`

---

## Conversation APIs

### 1. Tạo cuộc hội thoại riêng tư
**Endpoint:** `POST /api/v1/conversation/private`

**Mô tả:** Tạo một cuộc hội thoại 1-1 với người dùng khác

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "participantId": "uuid"
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "Complete create private conversation",
  "Result": {
    "conversationId": "uuid",
    "type": "PRIVATE",
    "createdAt": "timestamp"
  }
}
```

---

### 2. Tạo cuộc hội thoại nhóm
**Endpoint:** `POST /api/v1/conversation/group`

**Mô tả:** Tạo một cuộc hội thoại nhóm

**Security:** Yêu cầu Bearer Token

**Request Body:**
```json
{
  "groupName": "string",
  "memberIds": ["uuid", "uuid", "uuid"]
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "Complete create group conversation",
  "Result": {
    "conversationId": "uuid",
    "groupName": "string",
    "type": "GROUP",
    "createdAt": "timestamp"
  }
}
```

---

### 3. Thêm thành viên vào nhóm
**Endpoint:** `POST /api/v1/conversation/{conversationId}/members`

**Mô tả:** Thêm thành viên mới vào cuộc hội thoại nhóm

**Security:** Yêu cầu Bearer Token

**Path Parameters:**
- `conversationId` (required): UUID của cuộc hội thoại

**Request Body:**
```json
{
  "memberIds": ["uuid", "uuid"]
}
```

**Response:**
```json
{
  "code": 1000,
  "message": "Complete add member to {conversationId}",
  "Result": {
    "conversationId": "uuid",
    "addedMembers": [
      {
        "userId": "uuid",
        "displayName": "string"
      }
    ]
  }
}
```

---

## WebSocket APIs

### Kết nối WebSocket

#### WebSocket Endpoint
```
ws://localhost:8080/ws
```

hoặc với SockJS:
```
http://localhost:8080/ws
```

### Xác thực
Khi kết nối WebSocket, cần gửi JWT token trong header:
```javascript
const headers = {
  'Authorization': 'Bearer <your-jwt-token>'
};

// Sử dụng STOMP
const stompClient = Stomp.over(socket);
stompClient.connect(headers, onConnected, onError);
```

### Cấu hình WebSocket
- **Application Destination Prefix:** `/app`
- **User Destination Prefix:** `/user`
- **Simple Broker:** `/topic`, `/queue`

---

## WebSocket Topics

### 1. Thông báo lời mời kết bạn
**Topic:** `/user/{userId}/queue/friend-request`

**Mô tả:** Nhận thông báo khi có người gửi lời mời kết bạn

**Subscribe:**
```javascript
stompClient.subscribe('/user/queue/friend-request', (message) => {
  console.log(message.body); // "Bạn có một lời mời kết bạn mới!"
});
```

**Kích hoạt:** Khi có người gửi lời mời kết bạn qua API `POST /api/v1/friend-request`

---

### 2. Thông báo phản hồi lời mời kết bạn
**Topic:** `/user/{userId}/queue/friend-response`

**Mô tả:** Nhận thông báo khi lời mời kết bạn được chấp nhận hoặc từ chối

**Subscribe:**
```javascript
stompClient.subscribe('/user/queue/friend-response', (message) => {
  console.log(message.body); 
  // "Lời mời của bạn đã được chấp nhận bởi {displayName}"
  // hoặc "Lời mời của bạn đã bị từ chối bởi {displayName}"
});
```

**Kích hoạt:** Khi người nhận phản hồi lời mời qua API `POST /api/v1/friend-request/response`

---

### 3. Thông báo xóa bạn bè
**Topic:** `/user/{userId}/queue/friend-delete`

**Mô tả:** Nhận thông báo khi bị ai đó xóa khỏi danh sách bạn bè

**Subscribe:**
```javascript
stompClient.subscribe('/user/queue/friend-delete', (message) => {
  console.log(message.body); // "{displayName} da huy ket ban voi ban!"
});
```

**Kích hoạt:** Khi có người xóa bạn bè qua API `DELETE /api/v1/friends`

---

### 4. Thông báo bài viết mới
**Topic:** `/user/{userId}/queue/new-post`

**Mô tả:** Nhận thông báo khi bạn bè đăng bài viết mới

**Subscribe:**
```javascript
stompClient.subscribe('/user/queue/new-post', (message) => {
  console.log(message.body); // "{displayName} vừa đăng một bài viết mới!"
});
```

**Kích hoạt:** Khi bạn bè đăng bài viết mới qua API `POST /api/v1/post`

---

## Ví dụ kết nối WebSocket

### Sử dụng SockJS + STOMP

```javascript
// 1. Import thư viện
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

// 2. Tạo kết nối
const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

// 3. Kết nối với JWT token
const token = 'your-jwt-token';
const headers = {
  'Authorization': `Bearer ${token}`
};

stompClient.connect(headers, (frame) => {
  console.log('Connected: ' + frame);
  
  // 4. Subscribe các topic
  stompClient.subscribe('/user/queue/friend-request', (message) => {
    console.log('Friend request:', message.body);
  });
  
  stompClient.subscribe('/user/queue/friend-response', (message) => {
    console.log('Friend response:', message.body);
  });
  
  stompClient.subscribe('/user/queue/friend-delete', (message) => {
    console.log('Friend deleted:', message.body);
  });
  
  stompClient.subscribe('/user/queue/new-post', (message) => {
    console.log('New post:', message.body);
  });
  
}, (error) => {
  console.error('Connection error:', error);
});

// 5. Ngắt kết nối
function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log('Disconnected');
}
```

---

## Mã lỗi phổ biến

| Code | Message | HTTP Status |
|------|---------|-------------|
| 1000 | Success | 200 |
| 1001 | Uncategorized Exception | 400 |
| 1002 | User Not Found | 404 |
| 1003 | Unauthorized | 401 |
| 1004 | Invalid UUID | 400 |
| 1005 | Post Not Found | 404 |
| 1006 | Post Edit Forbidden | 403 |
| 1007 | Post Delete Forbidden | 403 |
| 1008 | Friend Not Found | 404 |

---

## Lưu ý bảo mật

1. **JWT Token:** Tất cả các API (trừ login và register) đều yêu cầu JWT token hợp lệ
2. **WebSocket Authentication:** WebSocket connection cũng yêu cầu JWT token trong header khi connect
3. **Rate Limiting:** Các API tạo bài viết có rate limiting để tránh spam
4. **CORS:** Server được cấu hình cho phép CORS từ các origin được chỉ định
5. **Role-based Access:** Một số API yêu cầu quyền ADMIN

---

## Thông tin kỹ thuật

- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL
- **Cache:** Redis
- **WebSocket:** STOMP over SockJS
- **Authentication:** JWT (JSON Web Token)
- **Server Port:** 8080

---

## Liên hệ & Hỗ trợ

Để biết thêm thông tin chi tiết, vui lòng tham khảo source code hoặc liên hệ team phát triển.
