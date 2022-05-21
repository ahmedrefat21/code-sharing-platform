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
    public ResponseEntity<?> getCode(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.codeService.getCodeById(id));
    }


    @PostMapping("/new")
    public ResponseEntity<?> submitCode(@RequestBody Map<String, String> codeSnippet) {
        return ResponseEntity.ok(this.codeService.createNewCode(codeSnippet));
    }
}
