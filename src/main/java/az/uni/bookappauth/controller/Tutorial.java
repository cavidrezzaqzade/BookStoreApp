package az.uni.bookappauth.controller;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author caci
 * @since 13.05.2022
 */

@Data
public class Tutorial {
    private String name;
    public Tutorial(String s){
        name = s;
    }
}
