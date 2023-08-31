package com.kadirgurturk.emailSender.buisness.service;

import com.kadirgurturk.emailSender.domain.Confirmation;
import com.kadirgurturk.emailSender.domain.User;
import com.kadirgurturk.emailSender.repository.ConfirmationRepository;
import com.kadirgurturk.emailSender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ConfirmationRepository confirmationRepository;

    private final EmailService emailService;

    public void saveUser(User user)
    {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email alredy exist");
        }
        user.setEnable(false);

        Confirmation confirmation = new Confirmation(user);

        confirmationRepository.save(confirmation);

        userRepository.save(user);

        emailService.sendSimpleMailMessage(user.getFirstName(),user.getEmail(),confirmation.getToken());
    }

    public Boolean isExistToken(String token)
    {
        Confirmation confirmation = confirmationRepository.findByToken(token);

        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());

        user.setEnable(true);

        userRepository.save(user);

        return Boolean.TRUE;
    }

}
