package ynu.elm.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Admin;
import ynu.elm.entity.AdminLoginResp;
import ynu.elm.service.AdminService;

import java.util.Optional;

@RestController
@RequestMapping("/AdminController")
public class AdminController {
    /**
     * 用于操作管理员信息的服务层对象。
     */
    @Resource
    private AdminService adminService;

    /**
     * JWT签名密钥，用于生成和验证JWT令牌。
     */
    private final String secret = "wang-yong-shuo-w-y-s-12345678901";

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据管理员ID和密码获取管理员信息并生成JWT令牌。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取管理员信息，如果管理员存在，则生成JWT令牌并返回。
     * 如果管理员不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param adminId 管理员ID
     * @param password 管理员密码
     * @return 包含管理员信息和JWT令牌的响应实体
     */
    @GetMapping("/getAdminByIdByPass")
    public ResponseEntity<AdminLoginResp> getAdminByIdByPass(@RequestParam String adminId,
                                                             @RequestParam String password) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("adminCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<Admin> adminOptional = adminService.getAdminByIdByPass(adminId, password);
            if (adminOptional.isPresent()) {
                String token = Jwts.builder()
                        .setSubject(adminOptional.get().getAdminId())
                        .claim("role", "admin")
                        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                        .compact();

                AdminLoginResp resp = new AdminLoginResp();
                resp.setAdmin(adminOptional.get());
                resp.setToken(token);
                return ResponseEntity.ok(resp);
            } else {
                return ResponseEntity.notFound().build();
            }
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据管理员ID判断管理员是否存在。
     * <p>
     * 该方法通过断路器模式调用服务层方法判断管理员是否存在，如果服务不可用，则返回503状态码。
     *
     * @param adminId 管理员ID
     * @return 表示管理员是否存在的布尔值响应实体
     */
    @GetMapping("/getAdminExistsById")
    public ResponseEntity<Boolean> getAdminExistsById(@RequestParam String adminId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("adminCircuitBreaker");
        return circuitBreaker.run(() -> {
            boolean exist = adminService.isAdminExist(adminId);
            return ResponseEntity.ok(exist);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存管理员信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存管理员信息，如果保存成功，则返回保存后的管理员信息。
     * 如果管理员ID已存在，则返回409状态码。如果服务不可用，则返回503状态码。
     *
     * @param admin 管理员信息
     * @return 保存后的管理员信息响应实体
     */
    @PostMapping("/saveAdmin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("adminCircuitBreaker");
        return circuitBreaker.run(() -> {
            try {
                Admin saved = adminService.saveAdmin(admin);
                return ResponseEntity.ok(saved);
            } catch (DataAccessException e) {
                if (e instanceof DuplicateKeyException) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                throw e;
            }
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}