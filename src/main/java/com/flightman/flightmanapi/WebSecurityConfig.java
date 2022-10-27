// package com.flightman.flightmanapi;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @Configuration
// @EnableWebSecurity
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         String encoded = "$2a$10$crpxHWNDARalaJuJdwxKbOrLmIozjWTMiM3M.zRHfSt02ELd/mxDe";
//         auth.inMemoryAuthentication()
//             .passwordEncoder(new BCryptPasswordEncoder())
//                 .withUser("srishti")
//                 .password(encoded)
//                 .roles("USER")
//             .and()
//                 .withUser("ajay")
//                 .password(encoded)
//                 .roles("USER")
//             .and()
//                 .withUser("abhilash")
//                 .password(encoded)
//                 .roles("USER")
//             .and()
//                 .withUser("miloni")
//                 .password(encoded)
//                 .roles("USER")
//             .and()
//                 .withUser("otito")
//                 .password(encoded)
//                 .roles("USER")
//             .and()
//                 .withUser("peter")
//                 .password(encoded)
//                 .roles("USER")
//             .and()
//                 .withUser("professor_kaiser")
//                 .password(encoded)
//                 .roles("USER")
//             ;
//     }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests()
//             .anyRequest().authenticated()
//             .and()
//             .httpBasic();
//     }
// }
