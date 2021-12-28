package ru.chernov.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class Keyboard {

    private Long id;

    private String type;

    @JsonProperty("inlineRowsResponse")
    private List<InlineRow> inlineRows;

    @JsonProperty("replyRowsResponse")
    private List<ReplyRow> replyRows;

    @Data
    public static class InlineRow {


        private Long id;

        @JsonProperty("inlineButtonsResponse")
        private List<InlineButton> inlineButtons;

        @Data
        public static class InlineButton {

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
    public static class ReplyRow {

        private Long id;

        @JsonProperty("replyButtonsResponse")
        private List<ReplyButton> replyButtons;

        @Data
        public static class ReplyButton {

            private Long id;

            private String text;

            private String description;

            private Boolean requestContact;

            private Boolean requestLocation;

            private Long keyboardId;
        }
    }
}
