package com.example.codesharingplatform.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "code")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private CodeViewRestriction codeViewRestriction;

    @JsonIgnore
    private boolean isTimeRestricted;

    @JsonIgnore
    private boolean isViewRestricted;


    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CodeViewRestriction getCodeViewRestriction() {
        return codeViewRestriction;
    }

    public void setCodeViewRestriction(CodeViewRestriction codeViewRestriction) {
        this.codeViewRestriction = codeViewRestriction;
    }

    public boolean isTimeRestricted() {
        return isTimeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        isTimeRestricted = timeRestricted;
    }

    public boolean isViewRestricted() {
        return isViewRestricted;
    }

    public void setViewRestricted(boolean viewRestricted) {
        isViewRestricted = viewRestricted;
    }

    @Entity(name = "code_view_restrictions")
    @Table(name = "code_view_restrictions")
    static class CodeViewRestriction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;
        private LocalDateTime lastAccessibleDate;
        private int allowedViews;

        @OneToOne
        private Code code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public LocalDateTime getLastAccessibleDate() {
            return lastAccessibleDate;
        }

        public void setLastAccessibleDate(LocalDateTime lastAccessibleDate) {
            this.lastAccessibleDate = lastAccessibleDate;
        }

        public Code getCode() {
            return code;
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public int getAllowedViews() {
            return allowedViews;
        }

        public void setAllowedViews(int allowedViews) {
            this.allowedViews = allowedViews;
        }
    }
}