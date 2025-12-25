package com.michaelzhu.exchange.user;

import com.michaelzhu.exchange.ApiError;
import com.michaelzhu.exchange.ApiException;
import com.michaelzhu.exchange.enums.UserType;
import com.michaelzhu.exchange.model.ui.PasswordAuthEntity;
import com.michaelzhu.exchange.model.ui.UserEntity;
import com.michaelzhu.exchange.model.ui.UserProfileEntity;
import com.michaelzhu.exchange.support.AbstractDbService;
import com.michaelzhu.exchange.util.HashUtil;
import com.michaelzhu.exchange.util.RandomUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserService extends AbstractDbService {

    public UserProfileEntity getUserProfile(Long userId) {
        return db.get(UserProfileEntity.class, userId);
    }

    @Nullable
    public UserProfileEntity fetchUserProfileByEmail(String email) {
        return db.from(UserProfileEntity.class).where("email = ?", email).first();
    }

    public UserProfileEntity getUserProfileByEmail(String email) {
        UserProfileEntity userProfile = fetchUserProfileByEmail(email);
        if (userProfile == null) {
            throw new ApiException(ApiError.AUTH_SIGNIN_FAILED);
        }
        return userProfile;
    }

    public UserProfileEntity signup(String email, String name, String passwd) {
        final long ts = System.currentTimeMillis();
        // insert user:
        var user = new UserEntity();
        user.type = UserType.TRADER;
        user.createdAt = ts;
        db.insert(user);
        // insert user profile:
        var up = new UserProfileEntity();
        up.userId = user.id;
        up.email = email;
        up.name = name;
        up.createdAt = up.updatedAt = ts;
        db.insert(up);
        // insert password auth:
        var pa = new PasswordAuthEntity();
        pa.userId = user.id;
        pa.random = RandomUtil.createRandomString(32);
        pa.passwd = HashUtil.hmacSha256(passwd, pa.random);
        db.insert(pa);
        return up;
    }

    public UserProfileEntity signin(String email, String passwd) {
        UserProfileEntity userProfile = getUserProfileByEmail(email);
        // find PasswordAuthEntity by user id:
        PasswordAuthEntity pa = db.fetch(PasswordAuthEntity.class, userProfile.userId);
        if (pa == null) {
            throw new ApiException(ApiError.USER_CANNOT_SIGNIN);
        }
        // check password hash:
        String hash = HashUtil.hmacSha256(passwd, pa.random);
        if (!hash.equals(pa.passwd)) {
            throw new ApiException(ApiError.AUTH_SIGNIN_FAILED);
        }
        return userProfile;
    }
}