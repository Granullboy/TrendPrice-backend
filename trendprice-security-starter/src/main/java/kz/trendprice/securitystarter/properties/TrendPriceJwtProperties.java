package kz.trendprice.securitystarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "trendprice.security.jwt")
public class TrendPriceJwtProperties {

    private String secret;
    private String issuer;
    private String principalClaim = "sub";
    private String authoritiesClaim = "roles";
    private String authorityPrefix = "ROLE_";
    private String internalAuthority = "ROLE_INTERNAL_SERVICE";
    private List<String> permitAll = new ArrayList<>();
    private List<String> internalPaths = new ArrayList<>();

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPrincipalClaim() {
        return principalClaim;
    }

    public void setPrincipalClaim(String principalClaim) {
        this.principalClaim = principalClaim;
    }

    public String getAuthoritiesClaim() {
        return authoritiesClaim;
    }

    public void setAuthoritiesClaim(String authoritiesClaim) {
        this.authoritiesClaim = authoritiesClaim;
    }

    public String getAuthorityPrefix() {
        return authorityPrefix;
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        this.authorityPrefix = authorityPrefix;
    }

    public String getInternalAuthority() {
        return internalAuthority;
    }

    public void setInternalAuthority(String internalAuthority) {
        this.internalAuthority = internalAuthority;
    }

    public List<String> getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(List<String> permitAll) {
        this.permitAll = permitAll;
    }

    public List<String> getInternalPaths() {
        return internalPaths;
    }

    public void setInternalPaths(List<String> internalPaths) {
        this.internalPaths = internalPaths;
    }
}
