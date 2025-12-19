package com.michaelzhu.exchange.assets;

import com.michaelzhu.exchange.enums.AssetEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AssetServiceTest {

    static final Long DEBT = 1L;
    static final Long USER_A = 2000L;
    static final Long USER_B = 3000L;
    static final Long USER_C = 4000L;

    AssetService assetService;

    @BeforeEach
    public void setUp() {
        assetService = new AssetService();
        init();
    }

    @Test
    void tryTransfer() {
        // A USD -> B ok
        assetService.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, USER_A, USER_B, AssetEnum.USD, new BigDecimal("12000"),
                true);
        assertBDEquals(300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(12000 + 45600, assetService.getAsset(USER_B, AssetEnum.USD).available);

        // A USD -> B failed
        assertFalse(assetService.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, USER_A, USER_B, AssetEnum.USD,
                new BigDecimal("301"), true));

        assertBDEquals(300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(12000 + 45600, assetService.getAsset(USER_B, AssetEnum.USD).available);
    }

    @Test
    void transfer() {
        // A USD -> A frozen:
        assetService.transfer(Transfer.AVAILABLE_TO_FROZEN, USER_A, USER_A, AssetEnum.USD, new BigDecimal("9000"));
        assertBDEquals(3300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(9000, assetService.getAsset(USER_A, AssetEnum.USD).frozen);

        // A frozen -> C available:
        assetService.transfer(Transfer.FROZEN_TO_AVAILABLE, USER_A, USER_C, AssetEnum.USD, new BigDecimal("8000"));
        assertBDEquals(1000, assetService.getAsset(USER_A, AssetEnum.USD).frozen);
        assertBDEquals(8000, assetService.getAsset(USER_C, AssetEnum.USD).available);

        // A frozen -> B available failed:
        assertThrows(RuntimeException.class, () -> {
            assetService.transfer(Transfer.FROZEN_TO_AVAILABLE, USER_A, USER_B, AssetEnum.USD, new BigDecimal("1001"));
        });
    }

    @Test
    void tryFreeze() {
        // freeze 12000 ok:
        assetService.tryFreeze(USER_A, AssetEnum.USD, new BigDecimal("12000"));
        assertBDEquals(300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(12000, assetService.getAsset(USER_A, AssetEnum.USD).frozen);

        // freeze 301 failed:
        assertFalse(assetService.tryFreeze(USER_A, AssetEnum.USD, new BigDecimal("301")));

        assertBDEquals(300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(12000, assetService.getAsset(USER_A, AssetEnum.USD).frozen);
    }

    @Test
    void unfreeze() {
        // freeze 12000 ok:
        assetService.tryFreeze(USER_A, AssetEnum.USD, new BigDecimal("12000"));
        assertBDEquals(300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(12000, assetService.getAsset(USER_A, AssetEnum.USD).frozen);

        // unfreeze 9000 ok:
        assetService.unfreeze(USER_A, AssetEnum.USD, new BigDecimal("9000"));
        assertBDEquals(9300, assetService.getAsset(USER_A, AssetEnum.USD).available);
        assertBDEquals(3000, assetService.getAsset(USER_A, AssetEnum.USD).frozen);

        // unfreeze 3001 failed:
        assertThrows(RuntimeException.class, () -> {
            assetService.unfreeze(USER_A, AssetEnum.USD, new BigDecimal("3001"));
        });
    }

    void init() {
        assetService.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_A, AssetEnum.USD, BigDecimal.valueOf(12300),
                false);
        assetService.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_A, AssetEnum.BTC, BigDecimal.valueOf(12),
                false);
        assetService.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_B, AssetEnum.USD, BigDecimal.valueOf(45600),
                false);
        assetService.tryTransfer(Transfer.AVAILABLE_TO_AVAILABLE, DEBT, USER_C, AssetEnum.BTC, BigDecimal.valueOf(34),
                false);

        assertBDEquals(-57900, assetService.getAsset(DEBT, AssetEnum.USD).available);
        assertBDEquals(-46, assetService.getAsset(DEBT, AssetEnum.BTC).available);

    }

    void assertBDEquals(long value, BigDecimal bd) {
        assertBDEquals(String.valueOf(value), bd);
    }

    void assertBDEquals(String value, BigDecimal bd) {
        assertEquals(0, new BigDecimal(value).compareTo(bd), String.format("Expected %s but actual %s.", value, bd.toPlainString()));
    }
}
