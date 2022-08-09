package Masterarbeit.NarrationMicroservice.Request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class SetStoryRequest {

    @Getter
    @Setter
    @Min(1)
    private Long narrationId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String text;

    @Getter
    @Setter
    @NotNull
    @Min(1)
    private int nr;

    @Getter
    @Setter
    @NotNull
    private boolean last;

    @Getter
    @Setter
    private String imagePath;


}
