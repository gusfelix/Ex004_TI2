import com.azure.ai.textanalytics.*;
import com.azure.ai.textanalytics.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Context;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String endpoint = "https://teste-ti2cc-gus.cognitiveservices.azure.com/";
        String apiKey = System.getenv("AZURE_TEXT_ANALYTICS_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("API key not found. Please set the AZURE_TEXT_ANALYTICS_API_KEY environment variable.");
            return;
        }

        TextAnalyticsClient client = new TextAnalyticsClientBuilder()
                .apiKey(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();

        // Textos para análise de sentimento
        List<String> texts = Arrays.asList(
                "Eu amo este produto! É incrível.",
                "Não gostei da qualidade do serviço.",
                "O atendimento ao cliente é excelente.");

        // Executar análise de sentimento
        AnalyzeSentimentResultCollection sentimentResults = client.analyzeSentimentBatchWithResponse(
                texts, new AnalyzeSentimentBatchOptions().setIncludeStatistics(true), Context.NONE).getValue();

        for (AnalyzeSentimentResult result : sentimentResults) {
            System.out.println("Texto: " + result.getText());
            System.out.println("Sentimento: " + result.getDocumentSentiment().getSentiment());
            System.out.println("Pontuação: " + result.getDocumentSentiment().getScore());
            System.out.println();
        }
    }
}
