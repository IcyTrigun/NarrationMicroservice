package Masterarbeit.NarrationMicroservice.Request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class SetStoryByUserRequestRequest {

    @Getter
    @Setter
    @Min(1)
    private long storyId;

    @Getter
    @Setter
    @Min(1)
    private long userId;

    @Getter
    @Setter
    @NotNull
    private boolean canBeSeen;


    @Getter
    @Setter
    @NotNull
    private boolean finished;


}
