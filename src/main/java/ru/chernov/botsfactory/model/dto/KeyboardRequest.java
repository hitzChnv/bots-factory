package ru.chernov.botsfactory.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class KeyboardRequest {

    private Long id;

    private String type;

    @JsonProperty("inlineRowsResponse")
    private List<InlineRowRequest> inlineRowsRequest;

    @JsonProperty("replyRowsResponse")
    private List<ReplyRowRequest> replyRowsRequest;

    @Data
    public static class InlineRowRequest {


        private Long id;

        @JsonProperty("inlineButtonsResponse")
        private List<InlineButtonRequest> inlineButtonsRequest;

        @Data
        public static class InlineButtonRequest {

            private Long id;

            private String text;

            private String description;

            private String url;

            private String callbackData;

            private Boolean pay;

            private Long keyboardId;
        }
    }

    @Data
    public static class ReplyRowRequest {

        private Long id;

        @JsonProperty("replyButtonsResponse")
        private List<ReplyButtonRequest> replyButtonsRequest;

        @Data
        public static class ReplyButtonRequest {

            private Long id;

            private String text;

            private String description;

            private Boolean requestContact;

            private Boolean requestLocation;

            private Long keyboardId;
        }
    }
}
