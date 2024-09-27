package steparrik.utils.validate.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import steparrik.utils.exception.ApiException;

import java.util.List;

@Component
public class UsernameValidate {

    public void checkUsername(String username){
        if(username.isEmpty()){
            throw new ApiException("Ник не может быть пустым", HttpStatus.BAD_REQUEST);
        }
        List<String> forbiddenSymbols = List.of(
                "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я",
                "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я",
                "\u0000", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\u0008", "\u0009", "\n", "\u000B", "\u000C", "\r", "\u000E", "\u000F",
                "\u0010", "\u0011", "\u0012", "\u0013", "\u0014", "\u0015", "\u0016", "\u0017", "\u0018", "\u0019", "\u001A", "\u001B", "\u001C", "\u001D", "\u001E", "\u001F",
                "\u007F",
                " ", "\t",
                "(", ")", "<", ">", "@", ",", ";", ":", "\\", "\"", "/", "[", "]", "?", "=", "{", "}"
        );

        String[] symbolsFromUsername = username.split("");
        for(String letter : symbolsFromUsername){
            if(forbiddenSymbols.contains(letter)){
                throw new ApiException("Ник содержит запрещенные символы, используйте только анлглийский алфавит", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
