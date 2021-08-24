package com.example.flightsbookingsystem.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.example.flightsbookingsystem.models.Customer;
import com.example.flightsbookingsystem.models.Flight;
import com.example.flightsbookingsystem.models.Role;
import com.example.flightsbookingsystem.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerAPI extends RoleTo {
    private final CustomerService customerService;

    @PostMapping("/customer/saveCustomer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customer/saveCustomer").toUriString());
        return ResponseEntity.created(uri).body(customerService.saveCustomer(customer));
    }

    @GetMapping("/customer/searchFlightByFare/{fare}")
    public ResponseEntity<List<Flight>> searchFlightByFare(@PathVariable long fare) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customer/searchFlightByFare").toUriString());
        return ResponseEntity.created(uri).body(customerService.searchForFlight(fare));
    }

    @GetMapping("/customer/searchFlightByLocations/{departurePlace}/{arrivePlace}")
    public ResponseEntity<List<Flight>> searchFlightByLocations(@PathVariable String departurePlace, @PathVariable String arrivePlace) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customer/searchFlightByLocations").toUriString());
        return ResponseEntity.created(uri).body(customerService.searchForFlight(departurePlace, arrivePlace));
    }

    @PostMapping("/customer/bookFlight/{userName}/{flightId}")
    public ResponseEntity<?> bookFlight(@PathVariable String userName, @PathVariable Long flightId) {
        customerService.bookFlight(userName, flightId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/customer/upgradeFlight/{userName}/{flightId}/{classType}")
    public ResponseEntity<?> upgradeFlight(@PathVariable String userName, @PathVariable Long flightId, @PathVariable String classType) {
        customerService.upgradeFlight(userName, flightId, classType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customer/cancelFlight/{userName}/{flightId}")
    public ResponseEntity<?> cancelFlight(@PathVariable String userName, @PathVariable Long flightId) {
        customerService.cancelFlight(userName, flightId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customerToken/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("BrightSkies ")) {
            try {
                String refresh_token = authorizationHeader.substring("BrightSkies ".length());
                Algorithm algorithm = Algorithm.HMAC256("brightskies".getBytes()); // this is not the right way to do it but for the purpose of the task.
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Customer customer = customerService.getCustomer(username);

                String access_token = JWT.create()
                        .withSubject(customer.getUserName())
                        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 15 * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", customer.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
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
