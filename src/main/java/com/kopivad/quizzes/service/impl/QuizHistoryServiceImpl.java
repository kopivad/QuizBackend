package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.service.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {
    private final String FILE_DIR;
    private final QuizHistoryRepository quizHistoryRepository;
    private final QuizSessionService quizSessionService;
    private final QuizAnswerService quizAnswerService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final EvaluationStepService stepService;

    @Autowired
    public QuizHistoryServiceImpl(
            @Value("#{environment.FILE_DIR}") String FILE_DIR,
            QuizHistoryRepository quizHistoryRepository,
            QuizSessionService quizSessionService,
            QuizAnswerService quizAnswerService,
            AnswerService answerService,
            QuestionService questionService,
            EvaluationStepService stepService
    ) {
        this.FILE_DIR = FILE_DIR;
        this.quizHistoryRepository = quizHistoryRepository;
        this.quizSessionService = quizSessionService;
        this.quizAnswerService = quizAnswerService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.stepService = stepService;
    }

    @Override
    public long save(QuizHistoryDto dto) {
        return quizHistoryRepository.save(dto);
    }

    @Override
    public Optional<byte[]> getPDF(long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        return history.map(resultHistory -> {
            String pdfFilename = history.get().getPdfFilename();
            try(InputStream inputStream = getClass().getResourceAsStream(FILE_DIR + pdfFilename);) {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Optional<byte[]> getCSV(long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        return history.map(resultHistory -> {
            String csvFilename = history.get().getCsvFilename();
            try(InputStream inputStream = getClass().getResourceAsStream(FILE_DIR + csvFilename)) {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
    public Optional<Long> createHistory(long sessionId) {
        Optional<QuizSession> session = quizSessionService.getById(sessionId);
        return session.map(resultSession -> {
            int total = calculateTotal(quizAnswerService.getAllBySessionId(sessionId));
            String rating = calculateRating(total, session.get().getQuizId());
            String pdfFilename = UUID.randomUUID().toString() + ".pdf";
            String csvFilename = UUID.randomUUID().toString() + ".csv";
            QuizHistoryDto dto = new QuizHistoryDto(total, rating, sessionId, session.get().getUserId(), pdfFilename, csvFilename);
            long id = save(dto);
            createPDF(pdfFilename, id);
            createCSV(csvFilename, id);
            return id;
        });
    }

    @Override
    public Optional<QuizHistory> getById(long id) {
        return quizHistoryRepository.findById(id);
    }

    @Override
    public List<QuizHistory> getAll() {
        return quizHistoryRepository.findAll();
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
                .map(answer -> answerService.getById(answer.getAnswerId()).orElseThrow())
                .filter(Answer::isRight)
                .map(answer ->
                        questionService.getById(answer.getQuestionId())
                        .map(
                                question -> question.getQuestion().getValue()
                        ).orElseThrow())
                .mapToInt(value -> value).sum();
    }
}
