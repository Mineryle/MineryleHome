package com.minerylehome.dashboard;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.math.BigDecimal;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class CryptoDashboardRepository {

    private final DSLContext dsl;
    private final Gson gson = new Gson();

    public CryptoDashboardRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<CryptoDashboardController.CryptoDashboardData> findDashboard(long accountSid) {
        var cryptoPrice = DSL.table(DSL.name("crypto_price"));
        var baseTicker = DSL.table(DSL.name("crypto_ticker")).as("base_ticker");
        var quoteTicker = DSL.table(DSL.name("crypto_ticker")).as("quote_ticker");

        Record btcRecord = dsl.select(
                        DSL.field(DSL.name("crypto_price", "amount"), BigDecimal.class),
                        DSL.field(DSL.name("crypto_price", "trend_dir"), String.class),
                        DSL.field(DSL.name("crypto_price", "trend_amount"), BigDecimal.class),
                        DSL.field(DSL.name("crypto_price", "market_cap"), Long.class),
                        DSL.field(DSL.name("crypto_price", "volume"), Long.class),
                        DSL.field(DSL.name("crypto_price", "supply"), Long.class),
                        DSL.field(DSL.name("crypto_price", "all_time_high"), BigDecimal.class))
                .from(cryptoPrice)
                .join(baseTicker)
                .on(DSL.field(DSL.name("crypto_price", "base_ticker_sid"))
                        .eq(DSL.field(DSL.name("base_ticker", "crypto_ticker_sid"))))
                .join(quoteTicker)
                .on(DSL.field(DSL.name("crypto_price", "quote_ticker_sid"))
                        .eq(DSL.field(DSL.name("quote_ticker", "crypto_ticker_sid"))))
                .where(DSL.field(DSL.name("crypto_price", "account_sid")).eq(accountSid))
                .and(DSL.field(DSL.name("base_ticker", "symbol")).eq("BTC"))
                .and(DSL.field(DSL.name("quote_ticker", "symbol")).eq("USD"))
                .orderBy(DSL.field(DSL.name("crypto_price", "price_at")).desc())
                .limit(1)
                .fetchOne();

        Record pricesRecord = dsl.select(
                        DSL.field("btc", BigDecimal.class),
                        DSL.field("eth", BigDecimal.class),
                        DSL.field("bch", BigDecimal.class),
                        DSL.field("xrp", BigDecimal.class))
                .from(DSL.table(DSL.name("crypto_prices_component")))
                .where(DSL.field("account_sid").eq(accountSid))
                .fetchOne();

        Record walletsRecord = dsl.select(
                        DSL.field("btc", BigDecimal.class),
                        DSL.field("eth", BigDecimal.class),
                        DSL.field("bch", BigDecimal.class),
                        DSL.field("xrp", BigDecimal.class))
                .from(DSL.table(DSL.name("crypto_wallets_component")))
                .where(DSL.field("account_sid").eq(accountSid))
                .fetchOne();

        Record watchlistRecord = dsl.select(
                        DSL.field("items", String.class))
                .from(DSL.table(DSL.name("crypto_watchlist_component")))
                .where(DSL.field("account_sid").eq(accountSid))
                .fetchOne();

        if (btcRecord == null || pricesRecord == null || walletsRecord == null || watchlistRecord == null) {
            return Optional.empty();
        }

        var priceSeriesRecords = dsl.select(
                        DSL.field(DSL.name("crypto_price", "amount"), BigDecimal.class))
                .from(cryptoPrice)
                .join(baseTicker)
                .on(DSL.field(DSL.name("crypto_price", "base_ticker_sid"))
                        .eq(DSL.field(DSL.name("base_ticker", "crypto_ticker_sid"))))
                .join(quoteTicker)
                .on(DSL.field(DSL.name("crypto_price", "quote_ticker_sid"))
                        .eq(DSL.field(DSL.name("quote_ticker", "crypto_ticker_sid"))))
                .where(DSL.field(DSL.name("crypto_price", "account_sid")).eq(accountSid))
                .and(DSL.field(DSL.name("base_ticker", "symbol")).eq("BTC"))
                .and(DSL.field(DSL.name("quote_ticker", "symbol")).eq("USD"))
                .orderBy(DSL.field(DSL.name("crypto_price", "price_at")).desc())
                .limit(100)
                .fetch();

        com.google.gson.JsonArray priceData = new com.google.gson.JsonArray();
        int totalPoints = priceSeriesRecords.size();
        for (int i = totalPoints - 1; i >= 0; i--) {
            Record record = priceSeriesRecords.get(i);
            com.google.gson.JsonObject point = new com.google.gson.JsonObject();
            point.addProperty("x", i - totalPoints + 1);
            point.addProperty("y", record.get(DSL.field("amount", BigDecimal.class)));
            priceData.add(point);
        }
        com.google.gson.JsonObject priceSeries = new com.google.gson.JsonObject();
        priceSeries.addProperty("name", "Price");
        priceSeries.add("data", priceData);
        com.google.gson.JsonArray priceSeriesArray = new com.google.gson.JsonArray();
        priceSeriesArray.add(priceSeries);
        JsonElement priceSeriesElement = priceSeriesArray;
        JsonElement watchlistItems = gson.fromJson(
                watchlistRecord.get(DSL.field("items", String.class)),
                JsonElement.class);

        CryptoDashboardController.BtcData btc = new CryptoDashboardController.BtcData(
                btcRecord.get(DSL.field("amount", BigDecimal.class)),
                new CryptoDashboardController.TrendData(
                        btcRecord.get(DSL.field("trend_dir", String.class)),
                        btcRecord.get(DSL.field("trend_amount", BigDecimal.class))),
                btcRecord.get(DSL.field("market_cap", Long.class)),
                btcRecord.get(DSL.field("volume", Long.class)),
                btcRecord.get(DSL.field("supply", Long.class)),
                btcRecord.get(DSL.field("all_time_high", BigDecimal.class)),
                new CryptoDashboardController.PriceData(priceSeriesElement));

        CryptoDashboardController.PricesData prices = new CryptoDashboardController.PricesData(
                pricesRecord.get(DSL.field("btc", BigDecimal.class)),
                pricesRecord.get(DSL.field("eth", BigDecimal.class)),
                pricesRecord.get(DSL.field("bch", BigDecimal.class)),
                pricesRecord.get(DSL.field("xrp", BigDecimal.class)));

        CryptoDashboardController.WalletsData wallets = new CryptoDashboardController.WalletsData(
                walletsRecord.get(DSL.field("btc", BigDecimal.class)),
                walletsRecord.get(DSL.field("eth", BigDecimal.class)),
                walletsRecord.get(DSL.field("bch", BigDecimal.class)),
                walletsRecord.get(DSL.field("xrp", BigDecimal.class)));

        return Optional.of(new CryptoDashboardController.CryptoDashboardData(
                btc,
                prices,
                wallets,
                watchlistItems));
    }
}
