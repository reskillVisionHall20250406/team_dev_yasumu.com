package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class ImageController {
    @GetMapping("/user")
    public String show() {
        return "user";
    }

    @PostMapping("/user")
    public String uploadeImage(
            @RequestParam("image") MultipartFile file,
            Model model) {
        try {
            String filename = file.getOriginalFilename();
            String filePath = "static/image/" + filename;
            byte[] content = file.getBytes();
            Files.write(Paths.get(filePath), content);

            String imageUrl = "/image/" + filename;
            model.addAttribute("imageUrl", imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user";
    }
}
