//package mimingucci.baomau.baomausecurity;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////	@Bean
////	public UserDetailsService userDetailsService() {
////		return new BaoMauUserDetailsService();
////	}
//
////	@Bean
////	public DaoAuthenticationProvider authenticationProvider() {
////		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////		authProvider.setUserDetailsService(userDetailsService());
////		authProvider.setPasswordEncoder(passwordEncoder());
////
////		return authProvider;
////	}
//
//   @Bean
//   public PasswordEncoder passwordEncoder() {
//	   return new BCryptPasswordEncoder();
//   }
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().permitAll();
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//	}
//
//	//	@Bean
////   public AuthenticationManager authenticationManager(
////           AuthenticationConfiguration authConfig) throws Exception {
////       return authConfig.getAuthenticationManager();
////   }
//
////   @Bean
////   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////	   http.authenticationProvider(authenticationProvider());
////       http.authorizeRequests().antMatchers("/user/create").permitAll()
////       .anyRequest().permitAll();
////       .antMatchers("/states/list_by_country/**").hasAnyAuthority("Admin", "Salesperson")
////		.antMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
////		.antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
////
////		.antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
////
////		.antMatchers("/products/edit/**", "/products/save", "/products/check_unique")
////			.hasAnyAuthority("Admin", "Editor", "Salesperson")
////
////		.antMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
////			.hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
////
////		.antMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
////
////		.antMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
////
////		.antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Assistant")
////
////		.antMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("Admin", "Salesperson")
////
////		.antMatchers("/orders_shipper/update/**").hasAuthority("Shipper")
////
////		.antMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistant")
////       .antMatchers("/login").permitAll()
////       .anyRequest().authenticated()
////           .and()
////           .formLogin()
////               .loginPage("/login")
////               .loginProcessingUrl("/login")
////               .defaultSuccessUrl("/test")
////               .usernameParameter("email")
////               .permitAll()
////            .and().logout().permitAll()
////            .and()
////               .rememberMe()
////                   .key("toivaban12345")
////                   .tokenValiditySeconds(7 * 24 * 60 * 60);
////       http.headers().frameOptions().sameOrigin();
////       return http.build();
////   }
//
////	@Bean
////	public WebSecurityCustomizer webSecurityCustomizer() {
////		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
////
////		//ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
////	}
//}
