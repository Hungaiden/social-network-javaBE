package com.example.FakeBook.Config.websocket;

import com.example.FakeBook.Service.JWTService;
import com.nimbusds.jwt.SignedJWT;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
//@Order(Ordered.HIGHEST_PRECEDENCE + 99) // Đảm bảo chạy trước Spring Security Filter mặc định
public class JWTAuthChannelInterceptor implements ChannelInterceptor {
    private final JWTService jwtService;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        // Sử dụng MessageHeaderAccessor để đảm bảo lấy được accessor có thể chỉnh sửa (mutable)
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Lấy Authorization từ header
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            // Check format
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                try {
                    // Validate & parse token (Logic của bạn giữ nguyên)
                    SignedJWT signedJWT = jwtService.verifyToken(token);
                    String userId = signedJWT.getJWTClaimsSet().getSubject();
                    String scope = signedJWT.getJWTClaimsSet().getClaim("scope").toString();

                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + scope);

                    // Tạo Authentication
                    Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, List.of(authority));

                    // --- QUAN TRỌNG: Gắn user vào session ---
                    accessor.setUser(auth);

                    log.info("✅ WebSocket Auth Success for User: {}", userId);

                } catch (Exception e) {
                    log.error("❌ WebSocket Auth Failed: {}", e.getMessage());
                    // Không throw Exception ở đây để tránh crash client socket ngay lập tức,
                    // thay vào đó có thể return null để từ chối message CONNECT
                    return null;
                }
            } else {
                log.warn("⚠️ WebSocket Connect without valid Authorization header");
                // Tùy chọn: return null để chặn kết nối nếu không có token
            }
        }

        // Trả về message (đã được accessor tự động cập nhật header User vào bên trong)
        return message;
    }
}