package com.metauniverse.estore.registration;

import com.metauniverse.estore.email.EmailSender;
import com.metauniverse.estore.exception.email.InvalidEmailException;
import com.metauniverse.estore.exception.token.EmailAlreadyConfirmedException;
import com.metauniverse.estore.exception.token.TokenExpiredException;
import com.metauniverse.estore.exception.token.TokenNotFoundException;
import com.metauniverse.estore.registration.token.ConfirmationToken;
import com.metauniverse.estore.registration.token.ConfirmationTokenService;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;

    private final EmailValidator emailValidator;

    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailSender emailSender;

    public String registerUser(RegistrationRequest request) {

        boolean isEmailValid = emailValidator.test(request.getEmail());

        if (!isEmailValid) {
            throw new InvalidEmailException();
        }

        //TODO: THIS COULD BE THE ISSUE
        User user = userRepository.findByEmail(request.getEmail()).orElseGet(() -> new User(request.getFirstName(),
                request.getLastName(),
                request.getFirstName() + " " + request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                Collections.singleton(Role.ROLE_USER)
        ));

        log.info("USER ROLE: " + user.getRoles());
        if (user.getRoles().contains(Role.ROLE_OAUTH2USER)) {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getFirstName() + " " + request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRoles(Collections.singleton(Role.ROLE_OAUTH2USER));

            String token = userService.signUpUser(user);
            String link = "https://shrimp-curious-thoroughly.ngrok-free.app/api/v1/registration/confirm?token=" + token;
            emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));
            return "Confirm you email.";
        }
        log.info("USER: " + user.toString());
        String token = userService.signUpUser(
                new User(request.getFirstName(),
                        request.getLastName(),
                        request.getFirstName() + " " + request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        Collections.singleton(Role.ROLE_USER))
        );
        String link = "https://shrimp-curious-thoroughly.ngrok-free.app/api/v1/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));
        return "Confirm you email.";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }

        confirmationTokenService.setConfirmedAt(token);

        userService.enableUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
