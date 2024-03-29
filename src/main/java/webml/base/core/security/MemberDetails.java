package webml.base.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import webml.base.entity.Member;
import webml.base.core.constants.AuthorityConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MemberDetails implements UserDetails {

    private Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }

    public String getUserNm() {
        return member.getUserNm();
    }

    public Collection<String> getAuthoritiesStrArr() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorityCdList = new ArrayList<>();
        String authorityCd = member.getAuthority().getAuthorityCd();
        authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_001));
        if (AuthorityConstants.AUTH_002.equals(authorityCd)) {
            authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_002));
        } else if (AuthorityConstants.AUTH_003.equals(authorityCd)) {
            authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_002));
            authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_003));
        }
        return authorityCdList;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
