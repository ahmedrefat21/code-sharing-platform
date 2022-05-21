package com.example.codesharingplatform.web;

import com.example.codesharingplatform.code.CodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/code")
public class CodeController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/{id}")
    public String getCode(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Code");
        model.addAttribute("code", List.of(this.codeService.getCodeById(id)));
        return "code";
    }

    @GetMapping("latest")
    public String getLatestCode(Model model) {
        model.addAttribute("title", "Latest");
        model.addAttribute("code", this.codeService.getLatestCodeSnippets());
        return "code";
    }

    @GetMapping("new")
    public String newCodeSnippet() {
        return "newCode";
    }

    @PostMapping("new")
    public String processNewCodeSnippet() {
        return "redirect:/code/new";
    }
}
