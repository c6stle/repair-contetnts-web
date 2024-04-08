package webml.base.core.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.base.entity.Member;
import webml.base.repository.MemberRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) {
        log.info("userId : {}", userId);
        Member member = memberRepository.findByUserId(userId)
                .map(m -> {
                    m.update(LocalDateTime.now());
                    return m;
                })
                .orElseThrow(
                () -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다.")
        );
        return new MemberDetails(member);
    }
}
