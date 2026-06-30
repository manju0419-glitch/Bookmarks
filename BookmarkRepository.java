package com.example.bookmarkmanager.repository;

import com.example.bookmarkmanager.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByTitleContainingIgnoreCaseOrUrlContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrTagsContainingIgnoreCase(
            String title, String url, String category, String tags);

    long countByFavoriteTrue();
}
