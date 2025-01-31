package miniproject.carrotmarket1.service;

import jakarta.servlet.http.HttpSession;
import miniproject.carrotmarket1.dto.KakaoUserInfoDTO;
import miniproject.carrotmarket1.dto.UserDTO;
import miniproject.carrotmarket1.entity.User;
import miniproject.carrotmarket1.repository.UserRepository;
import miniproject.carrotmarket1.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Value("${file.upload-dir}") // application.properties의 값을 주입
    private String uploadDir;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //로그인 시 패스워드 확인
    public UserDTO authenticate(String email, String password) {
        UserDTO user = userMapper.toDto(userRepository.findByEmail(email));
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    //로그인 시 사용자 위치 정보  update
    public void updateUserLocation(Long userId, Double latitude, Double longitude, String location) {
        userRepository.updateLocation(userId, latitude, longitude, location);
    }

    //프로필 수정시 사용
    public UserDTO getLoggedInUser(HttpSession session) {
        // 세션에서 로그인된 사용자 정보 가져오기
        return (UserDTO) session.getAttribute("loggedInUser");
    }

    //프로필 생성 및 업데이트
    public void saveOrUpdateUser(UserDTO user, MultipartFile profileImageFile) throws IOException {
        // 비밀번호 설정은 필요 시에만 수행
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // 필요에 따라 비밀번호 암호화 추가 가능
        }

        // 사용자 조회
        UserDTO existingUser = userMapper.toDto(userRepository.findByEmail(user.getEmail()));

        if (existingUser == null) {
            // 신규 회원인 경우
            if (profileImageFile != null && !profileImageFile.isEmpty()) {
                String fileName = saveProfileImage(profileImageFile, user);
                user.setProfileImage(fileName);
            }
            user.setUserGroup("GENERAL"); // 기본 사용자 그룹 설정
            User userEntity= userMapper.toEntity(user);
            userRepository.save(userEntity); // 새 사용자 추가
        } else {
            // 기존 사용자 업데이트
            user.setId(existingUser.getId()); // 기존 ID 유지

            if (profileImageFile != null && !profileImageFile.isEmpty()) {
                // 새 프로필 이미지가 업로드된 경우만 저장
                String fileName = saveProfileImage(profileImageFile, user);
                user.setProfileImage(fileName);
            } else {
                // 새 이미지가 없으면 기존 이미지 유지
                user.setProfileImage(existingUser.getProfileImage());
            }
            User userEntity= userMapper.toEntity(user);
            userRepository.save(userEntity); // 기존 사용자 업데이트
        }
    }

    // 프로필 이미지 저장 메소드
    private String saveProfileImage(MultipartFile profileImageFile, UserDTO user) throws IOException {

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        String fileExtension = profileImageFile.getOriginalFilename()
                .substring(profileImageFile.getOriginalFilename().lastIndexOf("."));
        String fileName = user.getEmail() + fileExtension;

        Path filePath = uploadPath.resolve(fileName);

        // 동일한 이름의 파일이 있으면 삭제
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // 파일 저장
        profileImageFile.transferTo(filePath.toFile());

        return "/profileImages/" + fileName; // 저장된 파일 경로 반환
    }

    public boolean emailExists(String email) {

        return userRepository.findByEmail(email) != null;
    }

    // USER 조회 By Id
    public UserDTO findById(Long id) {
        return userMapper.toDto(userRepository.findById(id).get());
    }

    public UserDTO findOrCreateKakaoUser(KakaoUserInfoDTO kakaoUserInfo) {
        // 1. 이메일을 기준으로 사용자 확인
        User existingUser = userRepository.findByEmail(kakaoUserInfo.getEmail());

        if (existingUser != null) {
            // 기존 사용자 반환
            return userMapper.toDto(existingUser);
        }

        // 2. 새 사용자 생성
        String randomPassword = passwordEncoder.encode(UUID.randomUUID().toString()); // 고정값 또는 랜덤값 사용
        User newUser = User.builder()
                .username(kakaoUserInfo.getNickname())
                .email(kakaoUserInfo.getEmail())
//                .password(randomPassword)
                .profileImage(kakaoUserInfo.getProfile_image())
                .userGroup("GENERAL")
                .lockedYn("N")
                .build();

        userRepository.save(newUser);
        return userMapper.toDto(newUser);
    }
}
