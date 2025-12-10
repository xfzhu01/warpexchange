package com.michaelzhu.exchange.assets;

import com.michaelzhu.exchange.enums.AssetEnum;
import com.michaelzhu.exchange.support.LoggerSupport;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class AssetService extends LoggerSupport {
    final ConcurrentMap<Long, ConcurrentMap<AssetEnum, Asset>> userAssets = new ConcurrentHashMap<>();

    public Asset getAsset(Long userId, AssetEnum assetId) {
        ConcurrentMap<AssetEnum, Asset> assets = userAssets.get(userId);
        if (assets == null) {
            return null;
        }
        return assets.get(assetId);
    }

    public Map<AssetEnum, Asset> getAssets(Long userId) {
        ConcurrentMap<AssetEnum, Asset> assets = userAssets.get(userId);
        if (assets == null) {
            return Map.of();
        }
        return assets;
    }

    public Asset initAsset(Long userId, AssetEnum assetId) {
        ConcurrentMap<AssetEnum, Asset> assets = userAssets.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        Asset zeroAsset = new Asset();
        assets.put(assetId, zeroAsset);
        return zeroAsset;
    }

    public boolean tryTransfer(Transfer type, Long fromUserId, Long toUserId, AssetEnum assetId, BigDecimal amount, boolean checkBalance) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        Asset fromAsset = getAsset(fromUserId, assetId);
        if (fromAsset == null) {

        }
    }
}
