package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ynu.elm.entity.User;
import ynu.elm.entity.UserLoginResp;
import ynu.elm.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/UserController")
public class UserController {
    /**
     * 用于操作用户信息的服务层对象。
     */
    @Resource
    private UserService userService;

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
     * 根据用户ID和密码获取用户信息并生成JWT令牌。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取用户信息，如果用户存在，则生成JWT令牌并返回。
     * 如果用户不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param userId  用户ID
     * @param password 用户密码
     * @return 包含用户信息和JWT令牌的响应实体
     */
    @GetMapping(value = "/getUserByIdByPass")
    public ResponseEntity<UserLoginResp> getUserByIdByPass(@RequestParam(value = "userId") String userId,
                                                           @RequestParam(value = "password") String password) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("userCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<User> userOptional = userService.getUserByIdByPass(userId, password);
            if (userOptional.isPresent()) {
                String token = Jwts.builder()
                        .setSubject(userOptional.get().getUserId().toString())
                        .claim("role", "user")
                        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                        .compact();

                UserLoginResp resp = new UserLoginResp();
                resp.setUser(userOptional.get());
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
     * 根据用户ID判断用户是否存在。
     * <p>
     * 该方法通过断路器模式调用服务层方法判断用户是否存在，如果服务不可用，则返回503状态码。
     *
     * @param userId 用户ID
     * @return 表示用户是否存在的布尔值响应实体
     */
    @GetMapping(value = "/getUserById")
    public ResponseEntity<Boolean> getUserExistsById(@RequestParam(value = "userId") String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("userCircuitBreaker");
        return circuitBreaker.run(() -> {
            boolean userExist = userService.isUserExist(userId);
            return ResponseEntity.ok(userExist);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 获取所有用户信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取所有用户信息，如果服务不可用，则返回503状态码。
     *
     * @return 所有用户信息响应实体
     */
    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("userCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<User> list = userService.getAllUsers();
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存用户信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存用户信息，如果保存成功，则返回保存后的用户信息。
     * 如果用户ID已存在，则返回409状态码。如果服务不可用，则返回503状态码。
     *
     * @param user 用户信息
     * @return 保存后的用户信息响应实体
     */
    @PostMapping(value = "/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("userCircuitBreaker");
        return circuitBreaker.run(() -> {
            try {
                User savedUser = userService.saveUser(user);
                return ResponseEntity.ok(savedUser);
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