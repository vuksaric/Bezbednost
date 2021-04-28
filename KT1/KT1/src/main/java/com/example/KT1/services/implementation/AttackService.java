package com.example.KT1.services.implementation;
import com.example.KT1.dto.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AttackService {

    public ResponseDTO escaping(String input)
    {
        final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%"};

        for (int i = 0 ; i < metaCharacters.length ; i++){
            if(input.contains(metaCharacters[i])){
                input = input.replace(metaCharacters[i],'\\' + metaCharacters[i]);
            }
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setUsername(input);
        return responseDTO;
    }

    public ResponseDTO emailValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        return responseDTO;
    }

    public ResponseDTO passwordValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        return responseDTO;
    }

    public ResponseDTO nameValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^[a-zA-Z_ \']+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        return responseDTO;
    }

    public ResponseDTO organisationValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^[a-zA-Z0-9_ \']+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        return responseDTO;
    }




}