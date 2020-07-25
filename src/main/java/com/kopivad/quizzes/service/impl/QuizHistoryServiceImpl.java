package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.mapper.QuizHistoryMapper;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import com.kopivad.quizzes.service.QuizHistoryService;
import com.kopivad.quizzes.service.QuizSessionService;
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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuizHistoryServiceImpl implements QuizHistoryService {
    @Value("${file.dir}")
    private String FILE_DIR;
    private final QuizHistoryRepository quizHistoryRepository;
    private final QuizSessionService quizSessionService;
    private final QuizAnswerService quizAnswerService;
    private final QuizHistoryMapper quizHistoryMapper;


    @Override
    public long save(QuizHistory quizHistory) {
        return quizHistoryRepository.save(quizHistory);
    }

    @Override
    @SneakyThrows
    public Resource getPDF(long id) {
        String pdfFilename = quizHistoryRepository.findById(id).getPdfFilename();
        Path path = Path.of(FILE_DIR + pdfFilename);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists()) return resource;
        else throw new RuntimeException("File not found");
    }

    @Override
    @SneakyThrows
    public Resource getCSV(long id) {
        String csvFilename = quizHistoryRepository.findById(id).getCsvFilename();
        Path path = Path.of(FILE_DIR + csvFilename);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists()) return resource;
        else throw new RuntimeException("File not found");
    }

    private void createPDF(String filename, long id) {
        QuizHistory history = quizHistoryRepository.findById(id);
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
        QuizHistory history = quizHistoryRepository.findById(id);
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
        QuizSession session = quizSessionService.getById(sessionId);
        int total = calculateTotal(quizAnswerService.getAllBySessionId(sessionId));
        String rating = calculateRating(total, session.getQuiz().getEvaluationSteps());
        String pdfFilename = UUID.randomUUID().toString() +  ".pdf";
        String csvFilename = UUID.randomUUID().toString() +  ".csv";

        QuizHistory quizHistory =
                QuizHistory
                        .builder()
                        .total(total)
                        .rating(rating)
                        .csvFilename(csvFilename)
                        .pdfFilename(pdfFilename)
                        .user(session.getUser())
                        .session(session)
                        .build();
        long id = save(quizHistory);
        createPDF(pdfFilename, id);
        createCSV(csvFilename, id);
        return id;
    }

    @Override
    public QuizHistory getById(long id) {
        return quizHistoryRepository.findById(id);
    }

    @Override
    public List<QuizHistoryDto> getAll() {
        List<QuizHistory> quizHistories = quizHistoryRepository.findAll();
        return quizHistories
                .stream()
                .map(quizHistoryMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private String calculateRating(int total, List<EvaluationStep> steps) {
        AtomicReference<String> rating = new AtomicReference<>("Lowest rating");
        steps.forEach(step -> {
            if(step.getMinTotal() <= total && step.getMaxTotal() >= total) rating.set(step.getRating());
        });
        return rating.get();
    }

    private int calculateTotal(List<QuizAnswer> quizAnswers) {
        AtomicInteger total = new AtomicInteger(0);
        quizAnswers.forEach(a -> {
            if (a.getAnswer().isRight()) {
                total.getAndAdd(a.getQuestion().getValue());
            }
        });
        return total.get();
    }
}
