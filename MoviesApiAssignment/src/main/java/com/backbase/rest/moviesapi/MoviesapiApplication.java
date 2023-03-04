package com.backbase.rest.moviesapi;

import java.util.ArrayList;

import com.backbase.rest.moviesapi.domain.AppRole;
import com.backbase.rest.moviesapi.domain.AppUser;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.h2.server.web.WebServlet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Slf4j
public class MoviesapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoviesapiApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    @DependsOn({"userServiceImpl"})
    CommandLineRunner createSampleUserRoleData(UserService userService) {
        return args -> {
            userService.saveRole(new AppRole(null, "ROLE_USER"));
            userService.saveRole(new AppRole(null, "ROLE_MANAGER"));
            userService.saveRole(new AppRole(null, "ROLE_ADMIN"));

            userService.saveUser(new AppUser(null, "vivek", "vivek babu", "admin", new ArrayList<>()));

            userService.addRoleToUser("vivek", "ROLE_ADMIN");
            userService.addRoleToUser("vivek", "ROLE_MANAGER");
        };
    }

    @Bean
    @DependsOn({"movieRatingServiceImpl"})
    CommandLineRunner createMovieRatingData(MovieRatingService movieRatingService) {
        return args -> {
            movieRatingService.save(new MovieRating(1, "tt0947798abc", "abc", "Black Swan", "tt0947798", 7.5, "$106,954,678"));
            movieRatingService.save(new MovieRating(2, "tt0964517xyz", "xys", "The Fighter", "tt0964517", 6.5, "$93,617,009"));
            movieRatingService.save(new MovieRating(3, "tt0947798def", "def", "Black Swan", "tt0947798", 8.5, "$106,954,678"));
            movieRatingService.save(new MovieRating(4, "tt0964517def", "def", "The Fighter", "tt0964517", 8.5, "$93,617,009"));
            movieRatingService.save(new MovieRating(5, "tt0379725ghi", "ghi", "Capote", "tt0379725", 6.0, "$28,750,530"));
            movieRatingService.save(new MovieRating(6, "tt0379725abc", "abc", "Capote", "tt0379725", 7.0, "$28,750,530"));
            movieRatingService.save(new MovieRating(7, "tt0947798a", "a", "Black Swan", "tt0947798", 4.5, "$106,954,678"));
            movieRatingService.save(new MovieRating(8, "tt0964517b", "b", "The Fighter", "tt0964517", 5.5, "$93,617,009"));
            movieRatingService.save(new MovieRating(9, "tt0947798z", "z", "Black Swan", "tt0947798", 7.3, "$106,954,678"));
            movieRatingService.save(new MovieRating(10, "tt0964517t", "t", "The Fighter", "tt0964517", 6.5, "$93,617,009"));
            movieRatingService.save(new MovieRating(11, "tt0379725i", "i", "Capote", "tt0379725", 5.0, "$28,750,530"));
            movieRatingService.save(new MovieRating(12, "tt0379725m", "m", "Capote", "tt0379725", 5.0, "$28,750,530"));
            movieRatingService.save(new MovieRating(13, "tt1324076ac", "ac", "Fox", "tt1324076", 8.4, "N/A"));
            movieRatingService.save(new MovieRating(14, "tt1324076az", "az", "Fox", "tt1324076", 4.4, "N/A"));
            movieRatingService.save(new MovieRating(15, "tt0060025th", "th", "Skippy", "tt0060025", 7.7, "N/A"));
            movieRatingService.save(new MovieRating(16, "tt0060025ze", "ze", "Skippy", "tt0060025", 5.5, "N/A"));
        };
    }

}
