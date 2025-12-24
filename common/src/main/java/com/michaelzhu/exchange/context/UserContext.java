package com.michaelzhu.exchange.context;

import com.michaelzhu.exchange.ApiError;
import com.michaelzhu.exchange.ApiException;

public class UserContext implements AutoCloseable {
    static final ThreadLocal<Long> THREAD_LOCAL_CTX = new ThreadLocal<>();

    /**
     * Get current user id, or throw exception if no user.
     */
    public static Long getRequiredUserId() {
        Long userId = getUserId();
        if (userId == null) {
            throw new ApiException(ApiError.AUTH_SIGNIN_REQUIRED, null, "Need signin first.");
        }
        return userId;
    }

    /**
     * Get current user id, or null if no user.
     */
    public static Long getUserId() {
        return THREAD_LOCAL_CTX.get();
    }

    public UserContext(Long userId) {
        THREAD_LOCAL_CTX.set(userId);
    }

    @Override
    public void close() throws Exception {
        THREAD_LOCAL_CTX.remove();
    }
}
