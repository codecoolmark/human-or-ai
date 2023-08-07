package at.codecool.humanoraiserver.quotegenerator;

import com.hexadevlabs.gpt4all.LLModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ChatGPT4AllQuoteGenerator implements QuoteGenerator {

    private final LLModel llModel;

    public ChatGPT4AllQuoteGenerator(@Value("${quotegenerator.chatgpt4all.modelfile}") String modelFile) {
        this.llModel = new LLModel(Path.of(modelFile));
    }

    @Override
    public synchronized String generateQuote() {
        LLModel.GenerationConfig config = LLModel.config()
                .withTemp(1.0f)
                .withNPredict(512)
                .build();
        return llModel.generate("This is a very inspiring fake quote ", config, false);
    }
}
