package com.minerylehome.dashboard;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minerylehome.auth.AuthSessionAttributes;

@RestController
@RequestMapping("/api/dashboards/crypto")
public class CryptoDashboardController {

    private final CryptoDashboardRepository dashboardRepository;
    private final Gson gson = new Gson();

    public CryptoDashboardController(CryptoDashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDashboard(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findDashboard(userId)
                .orElseGet(this::emptyDashboard));
    }

    private ResponseEntity<String> jsonResponse(Object payload) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(payload));
    }

    private Long getUserId(HttpSession session) {
        Object id = session.getAttribute(AuthSessionAttributes.USER_ID);
        if (id instanceof Long userId) {
            return userId;
        }
        return null;
    }

    private CryptoDashboardData emptyDashboard() {
        JsonElement emptyArray = JsonParser.parseString("[]");
        BtcData btc = new BtcData(
                BigDecimal.ZERO,
                new TrendData("up", BigDecimal.ZERO),
                0L,
                0L,
                0L,
                BigDecimal.ZERO,
                new PriceData(emptyArray));
        PricesData prices = new PricesData(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO);
        WalletsData wallets = new WalletsData(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO);
        return new CryptoDashboardData(btc, prices, wallets, emptyArray);
    }

    public record CryptoDashboardData(
            BtcData btc,
            PricesData prices,
            WalletsData wallets,
            JsonElement watchlist) {
    }

    public record BtcData(
            BigDecimal amount,
            TrendData trend,
            long marketCap,
            long volume,
            long supply,
            BigDecimal allTimeHigh,
            PriceData price) {
    }

    public record TrendData(
            String dir,
            BigDecimal amount) {
    }

    public record PriceData(JsonElement series) {
    }

    public record PricesData(
            BigDecimal btc,
            BigDecimal eth,
            BigDecimal bch,
            BigDecimal xrp) {
    }

    public record WalletsData(
            BigDecimal btc,
            BigDecimal eth,
            BigDecimal bch,
            BigDecimal xrp) {
    }
}
