package com.example.expense_tracker.resources;


import com.example.expense_tracker.Constants;
import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> userMap){
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email,password);
        return new ResponseEntity<>(generateJwtToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String,Object> userMap){
     String firstName = (String) userMap.get("firstName");
     String lastName = (String) userMap.get("lastName");
     String email = (String) userMap.get("email");
     String password = (String) userMap.get("password");
     User user = userService.registerUser(firstName,lastName,email,password);
     return new ResponseEntity<>(generateJwtToken(user), HttpStatus.OK);
    }

    private Map<String,String> generateJwtToken(User user){
        long timeStamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.SECRET_API_KEY)
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp + Constants.TOKEN_VALIDITY))
                .claim("userId",user.getUserId())
                .claim("email",user.getEmail())
                .claim("firstName",user.getFirstName())
                .claim("lastName",user.getLastName())
                .compact();
        Map<String,String> map = new HashMap<>();
        map.put("message","Successful");
        map.put("token",token);
        return map;
    }
}
