package dev.langchain4j.example.aiservice;

/* these methods set rejection reason to claim*/
public class ClaimProcessingUtil {


    public static String addIneligibilityReason(final String reason) {
        return "Ineligible claim";
    }

    public static String addRejectedFormatReason(final String reason) {
        return "Rejected format claim";
    }


}
