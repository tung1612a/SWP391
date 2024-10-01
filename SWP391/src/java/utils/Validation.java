/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.regex.Pattern;

/**
 *
 * @author ADMIN
 */
public class Validation {
    public String ValidatePassword(String password){

        // Patterns
        String minLengthPattern = "^.{6,}$"; // At least 6 characters
        String upperCasePattern = "(?=.*[A-Z])"; // At least 1 uppercase letter
        String numberPattern = "(?=.*\\d)"; // At least 1 number
        String specialCharacterPattern = "(?=.*[!@#$%^&*()_+\\[\\]{};':\"\\\\|,.<>/?~`-])"; // At least 1 special character

        // Compile the patterns
        Pattern minLength = Pattern.compile(minLengthPattern);
        Pattern upperCase = Pattern.compile(upperCasePattern);
        Pattern number = Pattern.compile(numberPattern);
        Pattern specialCharacter = Pattern.compile(specialCharacterPattern);

        if (!minLength.matcher(password).find()) {
            return "Password must be at least 6 characters long.";
        }

        if (!upperCase.matcher(password).find()) {
            return "Password must contain at least 1 uppercase letter.";
        }

        if (!number.matcher(password).find()) {
            return "Password must contain at least 1 number.";
        }

        if (!specialCharacter.matcher(password).find()) {
            return "Password must contain at least 1 special character.";
        }

        return "true";
    }
    
    
}
