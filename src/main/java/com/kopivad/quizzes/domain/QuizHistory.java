package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@JsonDeserialize(builder = QuizHistory.QuizHistoryBuilder.class)
public class QuizHistory {
    long id;
    int total;
    String rating;
    QuizSession session;
    User user;
    String pdfFilename;
    String csvFilename;

    public String toString() {
        return "QuizHistory(id=" + this.getId() + ", rating=" + this.getRating() + ", total=" + this.getTotal() + ", session=" + this.getSession() + ", user=" + this.getUser() + ", pdfFilename=" + this.getPdfFilename() + ", csvFilename=" + this.getCsvFilename() + ")";
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizHistoryBuilder { }
}
