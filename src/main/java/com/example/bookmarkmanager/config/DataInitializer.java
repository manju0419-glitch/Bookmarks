package com.example.bookmarkmanager.config;

import com.example.bookmarkmanager.model.Bookmark;
import com.example.bookmarkmanager.repository.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedBookmarks(BookmarkRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Bookmark spring = new Bookmark();
                spring.setTitle("Spring Official");
                spring.setUrl("https://spring.io");
                spring.setCategory("Framework");
                spring.setTags("java,spring,backend");
                spring.setNotes("Official website for Spring ecosystem.");
                spring.setFavorite(true);

                Bookmark github = new Bookmark();
                github.setTitle("GitHub");
                github.setUrl("https://github.com");
                github.setCategory("Development");
                github.setTags("code,repository,git");
                github.setNotes("Source code hosting and collaboration.");
                github.setFavorite(false);

                repository.save(spring);
                repository.save(github);
            }
        };
    }
}
