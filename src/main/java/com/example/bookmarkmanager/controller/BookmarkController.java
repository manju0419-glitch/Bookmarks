package com.example.bookmarkmanager.controller;

import com.example.bookmarkmanager.model.Bookmark;
import com.example.bookmarkmanager.service.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping({"", "/"})
    public String listBookmarks(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("bookmarks", bookmarkService.getAllBookmarks(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("totalCount", bookmarkService.getTotalCount());
        model.addAttribute("favoriteCount", bookmarkService.getFavoriteCount());
        return "index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bookmark", new Bookmark());
        model.addAttribute("formTitle", "Add Bookmark");
        return "form";
    }

    @PostMapping("/save")
    public String saveBookmark(@Valid @ModelAttribute("bookmark") Bookmark bookmark,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", bookmark.getId() == null ? "Add Bookmark" : "Edit Bookmark");
            return "form";
        }
        bookmarkService.saveBookmark(bookmark);
        redirectAttributes.addFlashAttribute("successMessage", "Bookmark saved successfully.");
        return "redirect:/bookmarks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return bookmarkService.getBookmarkById(id)
                .map(bookmark -> {
                    model.addAttribute("bookmark", bookmark);
                    model.addAttribute("formTitle", "Edit Bookmark");
                    return "form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Bookmark not found.");
                    return "redirect:/bookmarks";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteBookmark(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookmarkService.deleteBookmark(id);
        redirectAttributes.addFlashAttribute("successMessage", "Bookmark deleted successfully.");
        return "redirect:/bookmarks";
    }

    @PostMapping("/favorite/{id}")
    public String toggleFavorite(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookmarkService.toggleFavorite(id);
        redirectAttributes.addFlashAttribute("successMessage", "Bookmark updated.");
        return "redirect:/bookmarks";
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:/bookmarks";
    }
}
