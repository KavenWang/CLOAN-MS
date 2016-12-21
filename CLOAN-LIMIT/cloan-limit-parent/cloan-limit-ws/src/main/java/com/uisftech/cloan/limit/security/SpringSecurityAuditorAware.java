package com.uisftech.cloan.limit.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.uisftech.cloan.common.constants.Constants;


@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
    }
}
