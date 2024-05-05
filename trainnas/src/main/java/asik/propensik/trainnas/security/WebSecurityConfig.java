// package asik.propensik.trainnas.security;

// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.Customizer;
// import
// org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class WebSecurityConfig {

// @Bean
// public UserDetailsService userDetailsService(PasswordEncoder encoder) {

// // InMemoryUserDetailsManager
// UserDetails admin = User.withUsername("Amiya")
// .password(encoder.encode("123"))
// .roles("ADMIN", "USER")
// .build();

// UserDetails user = User.withUsername("Ejaz")
// .password(encoder.encode("123"))
// .roles("USER")
// .build();

// return new InMemoryUserDetailsManager(admin, user);
// }

// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// http.securityMatcher("/**")
// .cors(Customizer.withDefaults())
// .csrf(AbstractHttpConfigurer::disable)
// .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
// .requestMatchers("/auth/welcome").permitAll()
// .requestMatchers("/auth/user/**").authenticated()
// .requestMatchers("/auth/admin/**").authenticated());

// return http.build();
// }

// // Password Encoding
// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// @Autowired
// private UserDetailsService userDetailsService;

// @Autowired
// public void configureGlobal(AuthenticationManagerBuilder auth) throws
// Exception {
// auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
// }

// @Bean
// public BCryptPasswordEncoder encoder() {
// return new BCryptPasswordEncoder();
// }

// @Bean
// public CorsFilter corsFilter() {
// UrlBasedCorsConfigurationSource source = new
// UrlBasedCorsConfigurationSource();
// CorsConfiguration config = new CorsConfiguration();
// config.addAllowedOrigin("*");
// config.addAllowedMethod("*");
// config.addAllowedHeader("*");
// source.registerCorsConfiguration("/**", config);
// return new CorsFilter(source);
// }

// }
