package ru.chernov.datastore.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class KeyboardResponse {

    private Long id;

    private String type;

    private List<InlineRowResponse> inlineRowsResponse;

    private List<ReplyRowResponse> replyRowsResponse;

    @Getter
    @Setter
    @Builder
    public static class InlineRowResponse {

        private Long id;

        private List<InlineButtonResponse> inlineButtonsResponse;

        @Getter
        @Setter
        @Builder
        public static class InlineButtonResponse {

            private Long id;

            private String text;

            private String description;

            private String url;

            private String callbackData;

            private Boolean pay;

            private Long keyboardId;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class ReplyRowResponse {

        private Long id;

        private List<ReplyButtonResponse> replyButtonsResponse;

        @Getter
        @Setter
        @Builder
        public static class ReplyButtonResponse {

            private Long id;

            private String text;

            private String description;

            private Boolean requestContact;

            private Boolean requestLocation;

            private Long keyboardId;
        }
    }
}
