package at.codecool.humanoraiserver.quotegenerator;

import com.hexadevlabs.gpt4all.LLModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class ChatGPT4AllQuoteGenerator implements QuoteGenerator {

    private final LLModel llModel;

    private final BlockingQueue<String> quotes;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ChatGPT4AllQuoteGenerator(@Value("${quotegenerator.chatgpt4all.modelfile}") String modelFile,
            @Value("${quotegenerator.chatgpt4all.cachesize}") int cacheSize) {
        this.llModel = new LLModel(Path.of(modelFile));
        this.quotes = new LinkedBlockingQueue<>(cacheSize);
    }

    @Override
    public String generateQuote() throws InterruptedException {
        return this.quotes.take();
    }

    @Scheduled(fixedDelayString = "${quotegenerator.chatgpt4all.checkcacheintervalms}", timeUnit = TimeUnit.MILLISECONDS)
    private void fillQueue() throws InterruptedException {
        logger.info("Refilling quotes. Currently the queue contains {} quotes", this.quotes.size());
        while (this.quotes.remainingCapacity() > 0) {
            this.quotes.put(runPrompt());
        }
    }

    private synchronized String runPrompt() {
        var startTime = System.nanoTime();
        LLModel.GenerationConfig config = LLModel.config()
                .withTemp(1.0f)
                .withNPredict(512)
                .build();
        var newQuote = llModel.generate("This is a very inspiring fake quote ", config, false);
        var endTime = System.nanoTime();
        var ellapsedTime = endTime - startTime;
        logger.info("Generating a new quote took {} ms", ellapsedTime / 1000 / 1000);
        return newQuote;
    }
}
