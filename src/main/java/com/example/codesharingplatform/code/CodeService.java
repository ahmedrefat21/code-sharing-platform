package com.example.codesharingplatform.code;

import com.example.codesharingplatform.code.exceptions.CodeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CodeService {
    
    private final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }
    public Map<String, Object> getCodeById(Long id) {
        final Code code = this.codeRepository.findById(id)
                .orElseThrow(CodeNotFoundException::new);
        return getCodeView(code);
    }

    private Map<String, Object> getCodeView(Code code) {
        int time = 0;
        int views = 0;
        if (code.isTimeRestricted()) {
            LocalDateTime createdAt = code.getDate();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastAccessibleTime = code.getCodeViewRestriction().getLastAccessibleDate();

            if (!createdAt.equals(lastAccessibleTime)) {
                time = lastAccessibleTime.toLocalTime().toSecondOfDay() - now.toLocalTime().toSecondOfDay();
                if (time <= 0)
                    throw new CodeNotFoundException();
            }
        }
        if (code.isViewRestricted()) {
            views = code.getCodeViewRestriction().getAllowedViews();
            if (views != 0) {
                if (views < 0) {
                    throw new CodeNotFoundException();
                } else if (views == 1) {
                    code.getCodeViewRestriction().setAllowedViews(-1);
                } else {
                    code.getCodeViewRestriction().setAllowedViews(views - 1);
                }
                views -= 1;
            }
        }
        this.codeRepository.save(code);

        String dateTimeFormat = "yyyy/MM/dd HH:mm:ss";
        return new HashMap<>(Map.of("code", code.getCode(),
                "date", code.getDate().format(DateTimeFormatter.ofPattern(dateTimeFormat)),
                "time", time,
                "views", views,
                "isTimeRestricted", code.isTimeRestricted(),
                "isViewRestricted", code.isViewRestricted()));
    }

    @Transactional
    public Map<String, String> createNewCode(Map<String, String> code) {
        final String id = saveCode(code)
                .getId()
                .toString();
        System.out.println(id);

        return Map.of("id", id);
    }

    private Code saveCode(Map<String, String> code) {
        Code codeSnippet = new Code();
        System.out.println(code);
        codeSnippet.setCode(code.get("code"));
        LocalDateTime createdAt = LocalDateTime.now();
        codeSnippet.setDate(createdAt);
        final int views = Integer.parseInt(code.get("views"));
        final int time = Integer.parseInt(code.get("time"));
        Code.CodeViewRestriction restrictions = new Code.CodeViewRestriction();


        if (views != 0 && time != 0) {
            restrictions.setAllowedViews(views);
            restrictions.setLastAccessibleDate(createdAt.plusSeconds(time));
            codeSnippet.setTimeRestricted(true);
            codeSnippet.setViewRestricted(true);
        } else if (views != 0){
            restrictions.setAllowedViews(views);
            restrictions.setLastAccessibleDate(createdAt);
            codeSnippet.setViewRestricted(true);
        } else if (time != 0){
            restrictions.setLastAccessibleDate(createdAt.plusSeconds(time));
            codeSnippet.setTimeRestricted(true);
        }

        codeSnippet.setCodeViewRestriction(restrictions);
        return this.codeRepository.save(codeSnippet);
    }


}
