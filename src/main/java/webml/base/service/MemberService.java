package webml.base.service;

import webml.base.core.exception.MessageException;
import webml.base.dto.MemberDto;
import webml.base.dto.NewMemberDto;
import webml.base.entity.Authority;
import webml.base.entity.Member;
import webml.base.repository.AuthorityRepository;
import webml.base.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final AuthorityRepository authorityRepository;

    public List<MemberDto> getMemberList() {
        return memberRepository.findAll().stream().map(MemberDto::new).collect(Collectors.toList());
    }

    public MemberDto getMemberInfo(Long memberIdx) {
        return memberRepository.findById(memberIdx).map(MemberDto::new).orElseThrow(() -> new MessageException("선택한 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public void saveMember(NewMemberDto newMemberDto) {
        try {
            Authority authority = authorityRepository.findById(newMemberDto.getAuthorityCd()).orElseThrow(() -> new MessageException("선택한 권한을 찾을 수 없습니다."));
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Member newMember = Member.builder()
                    .userId(newMemberDto.getUserId())
                    .userNm(newMemberDto.getUserNm())
                    .password(passwordEncoder.encode(newMemberDto.getPassword()))
                    .authority(authority)
                    .build();
            memberRepository.save(newMember);
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

    @Transactional
    public void updateMember(MemberDto memberDto) {
        Authority authority = authorityRepository.findById(memberDto.getAuthorityCd()).orElseThrow(() -> new MessageException("선택한 권한을 찾을 수 없습니다."));
        Member findMember = memberRepository.findById(memberDto.getMemberIdx()).orElseThrow(() -> new MessageException("사용자를 찾을 수 없습니다."));
        findMember.update(memberDto.getUserNm(), authority);
    }

    @Transactional
    public void deleteMember(Long memberIdx) {
        memberRepository.deleteById(memberIdx);
    }

    //아이디중복체크
    public MemberDto checkIdDup(String userId) {
        return memberRepository.findByUserId(userId).map(MemberDto::new).orElse(null);
    }
}
