# Flights Booking System

## Description
The system provides a facility to search for the available flights between two
locations. As well, demonstrating fare, departure and arrival times of the different
flights. It allows user to make a reservation of flight and upgrading the ticket class.

### A Code Structure directory layout

```
flightsbookingsystem package

└── controller package
    └── AdminAPI
    └── CustomerAPI
    └── RoleTo
    
└── models package
    └── Admin
    └── Customer
    └── Flight
    └── Role
    
└── repository package
    └── AdminRepo
    └── CustomerRepo
    └── FlightRepo
    └── RoleRepo
    
└── security package
    └── filter
        └── CustomAuthenticationFilter
        └── CustomAuthorizationFilter
    └── SecurityConfiguration
    
└── services package
    └── AdminService
    └── AdminServiceImplementation
    └── CustomerService
    └── CustomerServiceImplementation
    
└── FlightsBookingSystemApplication

```

For the security i used JWT. When admin try to login with username and password and if it successful will get a response of JSON that contain an access token and refresh token to access the allowed api.
when the access token expired. he  can use the refresh token to generate a new access token untill the refresh token is expired then he must login again.
