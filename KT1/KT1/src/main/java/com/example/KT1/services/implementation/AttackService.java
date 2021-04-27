package com.example.KT1.services.implementation;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AttackService {

    public String escaping(String input)
    {
        final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%"};

        for (int i = 0 ; i < metaCharacters.length ; i++){
            if(input.contains(metaCharacters[i])){
                input = input.replace(metaCharacters[i],"\\"+metaCharacters[i]);
            }
        }
        return input;
    }

    public boolean emailValidation(String input)
    {
        if (input == null) {
            return false;
        }
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public boolean passwordValidation(String input)
    {
        if (input == null) {
            return false;
        }

        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public boolean nameValidation(String input)
    {
        if (input == null) {
            return false;
        }

        String regex = "^[a-zA-Z_ \']+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }



    public boolean usernameValidation(String input)
    {
        if (input == null) {
            return false;
        }

        String regex = "[^a-z^A-Z^0-9\\^_]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }


}