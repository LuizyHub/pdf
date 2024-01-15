import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class Quiz {
    private String question;
    private String choices;
    private String answer;
    private String explanation;
}
