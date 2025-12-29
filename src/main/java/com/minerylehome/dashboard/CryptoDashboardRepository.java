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
        Record btcRecord = dsl.select(
                        DSL.field("amount", BigDecimal.class),
                        DSL.field("trend_dir", String.class),
                        DSL.field("trend_amount", BigDecimal.class),
                        DSL.field("market_cap", Long.class),
                        DSL.field("volume", Long.class),
                        DSL.field("supply", Long.class),
                        DSL.field("all_time_high", BigDecimal.class),
                        DSL.field("price_series", String.class))
                .from(DSL.table(DSL.name("crypto_btc_component")))
                .where(DSL.field("account_sid").eq(accountSid))
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

        JsonElement priceSeries = gson.fromJson(
                btcRecord.get(DSL.field("price_series", String.class)),
                JsonElement.class);
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
                new CryptoDashboardController.PriceData(priceSeries));

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
