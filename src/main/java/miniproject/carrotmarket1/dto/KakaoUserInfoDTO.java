package miniproject.carrotmarket1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDTO {

    private Long id;           // 카카오 사용자 고유 ID
    private String nickname;   // 닉네임
    private String email;      // 이메일

}
