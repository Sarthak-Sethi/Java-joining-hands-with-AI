package dev.langchain4j.example.vectordb;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

@Component
public class VectorDbInputService implements CommandLineRunner {

    private final VectorStore vectorStore;

    @Value("classpath:/pastesomeodfhere")
    private Resource marketPDF;

    public VectorDbInputService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
//        var pdfReader = new ParagraphPdfDocumentReader(marketPDF);
//        TextSplitter textSplitter = new TokenTextSplitter();
//        vectorStore.accept(textSplitter.apply(pdfReader.get()));
//        System.out.println("VectorStore Loaded with data!");

        String webUrl = "https://www.keystonefirstpa.com/content/dam/keystone-first/keystone-first-pa/pdf/provider/claims-billing/edifecs-clinical-insights.pdf.coredownload.inline.pdf";
        UrlResource resource = new UrlResource(webUrl);
        TikaDocumentReader reader = new TikaDocumentReader(resource);

        // 3. Split and Store
        var splitter = new TokenTextSplitter();
        vectorStore.accept(splitter.apply(reader.get()));
    }
}
