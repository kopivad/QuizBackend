package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;


@Service
@RequiredArgsConstructor
public class QuizHistoryServiceImpl implements QuizHistoryService {
    @Value("#{environment.FILE_DIR}")
    private String FILE_DIR;
    private final QuizHistoryRepository quizHistoryRepository;
    private final QuizSessionService quizSessionService;
    private final QuizAnswerService quizAnswerService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final EvaluationStepService stepService;


    @Override
    public long save(QuizHistory quizHistory) {
        return quizHistoryRepository.save(quizHistory);
    }

    @Override
    @SneakyThrows
    public Optional<Resource> getPDF(long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        if (history.isPresent()) {
            String pdfFilename = history.get().getPdfFilename();
            Path path = Path.of(FILE_DIR + pdfFilename);
            Resource resource = new UrlResource(path.toUri());
            return Optional.of(resource);
        }
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public Optional<Resource> getCSV(long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        if (history.isPresent()) {
            String csvFilename = history.get().getCsvFilename();
            Path path = Path.of(FILE_DIR + csvFilename);
            Resource resource = new UrlResource(path.toUri());
            return Optional.of(resource);
        }
        return Optional.empty();
    }

    private void createPDF(String filename, long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.beginText();
                contentStream.showText(history.toString());
                contentStream.endText();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            document.save(FILE_DIR + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCSV(String filename, long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        try (
                FileWriter out = new FileWriter(FILE_DIR + filename);
                CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)
        ) {
            printer.printRecord(history.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long createHistory(long sessionId) {
        Optional<QuizSession> session = quizSessionService.getById(sessionId);
        if (session.isPresent()) {
            int total = calculateTotal(quizAnswerService.getAllBySessionId(sessionId));
            String rating = calculateRating(total, session.get().getQuizId());
            String pdfFilename = UUID.randomUUID().toString() + ".pdf";
            String csvFilename = UUID.randomUUID().toString() + ".csv";
            QuizHistory quizHistory = new QuizHistory(1L, total, rating, sessionId, session.get().getUserId(), pdfFilename, csvFilename);
            long id = save(quizHistory);
            createPDF(pdfFilename, id);
            createCSV(csvFilename, id);
            return id;
        }
        return INTEGER_ZERO;
    }

    @Override
    public Optional<QuizHistory> getById(long id) {
        return quizHistoryRepository.findById(id);
    }

    @Override
    public List<QuizHistory> getAll() {
        return quizHistoryRepository.findAll();
    }

    @Override
    public List<QuizHistory> getAllBySessionId(long sessionId) {
        return quizHistoryRepository.findAllBySessionId(sessionId);
    }

    private String calculateRating(int total, long quizId) {
        List<EvaluationStep> steps = stepService.getByQuizId(quizId);
        return steps
                .stream()
                .filter(step -> step.getMinTotal() <= total && step.getMaxTotal() >= total)
                .map(EvaluationStep::getRating)
                .findFirst()
                .orElse("Lowest rating");
    }

    private int calculateTotal(List<QuizAnswer> quizAnswers) {
        return quizAnswers
                .stream()
                .map(a -> answerService.getById(a.getAnswerId()))
                .filter(a -> a.get().isRight())
                .map(a -> questionService.getById(a.get().getQuestionId()).get().getQuestion().getValue())
                .mapToInt(value -> value).sum();
    }
}
