package com.example.flightsbookingsystem.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.flightsbookingsystem.models.Admin;
import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminAPI extends RoleTo {
    private final AdminService adminService;

    @GetMapping("/admin/getAllAdmins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok().body(adminService.getAdmins());
    }

    @GetMapping("/admin/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok().body(adminService.getCustomers());
    }

    @PostMapping("/admin/saveAdmin")
    public ResponseEntity<Admin> saveAdmins(@RequestBody Admin admin) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/saveAdmin").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveAdmin(admin));
    }

    @PostMapping("/admin/role/saveRole")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/saveRole").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveRole(role));
    }

    @PostMapping("/admin/role/addRoleAdmin")
    public ResponseEntity<?> addRoleToAdmin(@RequestBody RoleTo form) {
        adminService.addRoleToAdmin(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/role/addRoleCustomer")
    public ResponseEntity<?> addRoleToCustomer(@RequestBody RoleTo form) {
        adminService.addRoleToCustomer(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/saveFlight")
    public ResponseEntity<?> saveFlight(@RequestBody Flight flight) {
        adminService.saveFlight(flight);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/getFlight/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable("id") Long flightId) {
        return ResponseEntity.ok().body(adminService.getFlight(flightId));
    }

    @DeleteMapping("/admin/deleteFlight/{id}")
    public ResponseEntity<?> deleteFlight(@PathVariable("id") Long flightId) {
        adminService.deleteFlight(flightId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/updateFlightArrivePlace/{id}/{newArrivePlace}")
    public ResponseEntity<?> updateFlightArrivePlace(@PathVariable("id") Long flightId, @PathVariable("newArrivePlace") String newArrivePlace) {
        adminService.updateFlightArrivePlace(flightId, newArrivePlace);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/updateFlightDeparturePlace/{id}/{newDeparturePlace}")
    public ResponseEntity<?> updateFlightDeparturePlace(@PathVariable("id") Long flightId, @PathVariable("newDeparturePlace") String newDeparturePlace) {
        adminService.updateFlightDeparturePlace(flightId, newDeparturePlace);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/updateFlightDate/{id}/{newDate}")
    public ResponseEntity<?> updateFlightDate(@PathVariable("id") Long flightId, @PathVariable("newDate") String newDate) throws ParseException {
        adminService.updateFlightDate(flightId, newDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/adminToken/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("BrightSkies ")) {
            try {
                String refresh_token = authorizationHeader.substring("BrightSkies ".length());
                Algorithm algorithm = Algorithm.HMAC256("brightskies".getBytes()); // this is not the right way to do it but for the purpose of the task.
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Admin admin = adminService.getAdmin(username);

                String access_token = JWT.create()
                        .withSubject(admin.getUserName())
                        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 15 * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", admin.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {

                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("error_message", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}


