package com.puresoftware.raymondJames.pojos;

import lombok.Getter;
import lombok.Setter;


public class BearerTokenGeneratorDetails {

    public static class BearerTokenGeneratorResponse {
        @Getter
                @Setter
        private String accessToken;
    }
}
