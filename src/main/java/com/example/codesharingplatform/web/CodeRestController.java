package com.example.codesharingplatform.web;

import com.example.codesharingplatform.code.CodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/code")
public class CodeRestController {

    private final CodeService codeService;

    public CodeRestController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCode(@PathVariable("id") Long id) {
        var snippet = this.codeService.getCodeByIdProxy(id);
        return ResponseEntity.ok(snippet);
    }
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestCode() {
        return ResponseEntity.ok(this.codeService.getLatestCodeSnippets());
    }


    @PostMapping("/new")
    public ResponseEntity<?> submitCode(@RequestBody Map<String, String> codeSnippet) {
        return ResponseEntity.ok(this.codeService.createNewCode(codeSnippet));
    }
}
