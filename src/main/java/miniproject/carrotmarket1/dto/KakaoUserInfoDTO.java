package miniproject.carrotmarket1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDTO {

    private Long id;           // 카카오 사용자 고유 ID
    private String nickname;   // 닉네임
    private String email;      // 이메일
    private String profile_image;//  프로필이미지

}
