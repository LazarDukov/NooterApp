package apps.nooterapp.util;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class CustomPasswordGenerator {
    private static final String SPECIAL_CHAR_ERROR_CODE = "INSUFFICIENT_SPECIAL";
    PasswordGenerator passwordGenerator = new PasswordGenerator();
    public String generatePassayPassword() {
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return SPECIAL_CHAR_ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule specialCharRule = new CharacterRule(specialChars);
        specialCharRule.setNumberOfCharacters(2);
        List<CharacterRule> rules = Arrays.asList(
                lowerCaseRule,
                upperCaseRule,
                digitRule,
                specialCharRule
        );

        return passwordGenerator.generatePassword(10, rules);
    }
}
