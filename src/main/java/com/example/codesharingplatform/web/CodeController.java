package com.example.codesharingplatform.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/code")
public class CodeController {

    @GetMapping("new")
    public String newCodeSnippet() {
        return "newCode";
    }

    @PostMapping("new")
    public String processNewCodeSnippet() {
        return "redirect:/code/new";
    }
}
