package com.example.bookmarkmanager.service;

import com.example.bookmarkmanager.model.Bookmark;
import com.example.bookmarkmanager.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<Bookmark> getAllBookmarks(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return bookmarkRepository.findAll();
        }
        return bookmarkRepository.findByTitleContainingIgnoreCaseOrUrlContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrTagsContainingIgnoreCase(
                keyword, keyword, keyword, keyword);
    }

    public Optional<Bookmark> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id);
    }

    public Bookmark saveBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }

    public void toggleFavorite(Long id) {
        bookmarkRepository.findById(id).ifPresent(bookmark -> {
            bookmark.setFavorite(!bookmark.isFavorite());
            bookmarkRepository.save(bookmark);
        });
    }

    public long getTotalCount() {
        return bookmarkRepository.count();
    }

    public long getFavoriteCount() {
        return bookmarkRepository.countByFavoriteTrue();
    }
}
