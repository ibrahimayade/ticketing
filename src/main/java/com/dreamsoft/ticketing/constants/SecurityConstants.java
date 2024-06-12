package com.dreamsoft.ticketing.constants;

import static com.dreamsoft.ticketing.constants.ResourceConstants.BASE_API_URL;

public class SecurityConstants {



    public static final String[] AUTH_WHITELIST = {
            "/**",
            //******************************* FOR SWAGGER CONFIG *********
            BASE_API_URL + "/swagger-ui.html/**",
            "/swagger-ui.html/**",
            BASE_API_URL + "/webjars/**",
            BASE_API_URL + "/swagger-resources/**",
            BASE_API_URL + "/v2/**",
            // ***************************************** FOR SWAGGER CONFIG ********
            BASE_API_URL + "/consumers/check/email",

            //swagger config
            "/swagger-ui.html",

            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger.json",
            "/api-docs/**"
    };

    public static final String[] AUTH_WHITELIST_START_WITH = {

            BASE_API_URL + "/auth/",
            "/cauri-money-backend/api/webhooks",
            "/swagger-ui.html",
            "/swagger-ui",
            "/swagger-resources",
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger.json",
            "/api-docs"
    };

    public static final String[] SWAGGER_ENDPOINTS = {
            "/swagger-ui.html",
            "/swagger-ui",
            "/swagger-resources",
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger.json",
            "/api-docs"
    };
    public static final String[] WEBHOOK_ENDPOINTS = {
            //weavr webhook
            "/cauri-money-backend/api/webhooks/send/watch",
            "/cauri-money-backend/api/webhooks/outgoing_wire_transfers/watch",
            "/cauri-money-backend/api/webhooks/managed_accounts/deposits/watch",
            "/cauri-money-backend/api/webhooks/managed_accounts/watch",
            "/cauri-money-backend/api/webhooks/consumers/kyc/watch",
            //aza webhook
            "/cauri-money-backend/api/webhooks/aza/**",
            //stripe webhook
            "/cauri-money-backend/api/webhooks/stripe/transactions",
            //orange webhook
            "/cauri-money-backend/api/webhooks/orange/cash-in/watch",
            "/cauri-money-backend/api/webhooks/orange/cash-in/watch",

            //sumsub webhook
            "/cauri-money-backend/api/webhooks/sumsub/kyc",
            //checkout webhook
            "/cauri-money-backend/api/webhooks/checkout/payment",
            //wave webhook
            "/cauri-money-backend/api/webhooks/wave",

    };
    public static final String SECRET = "fj32Jfv02Mq33g0f8ioDkw";
    public static final String REQUEST_HEADER_NAME = "Authorization";
    public static final String AUTH_HEADER_BEARER_NAME = "Bearer ";
    public static final String APP_HEADER_NAME = "app";
    public static final Long MOBIL_ACCESS_DURATION_MONTHS = 1000000L; // 1 ans
    public static final Long WEB_REFRESH_DURATION_HOURS = 1000000L;
    public static final Long MOBIL_RE_AUTH_DURATION_MIN = 15L; // 1 ans 6 mois
    public static final Long EMAIL_DURATION_MIN = 15L;
    public static final Long OTP_DURATION_MIN = 5L;

    private SecurityConstants() {
    }
}
