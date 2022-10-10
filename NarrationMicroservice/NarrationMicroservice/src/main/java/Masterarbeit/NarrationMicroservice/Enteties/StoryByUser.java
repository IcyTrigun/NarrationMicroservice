package Masterarbeit.NarrationMicroservice.Enteties;


import Masterarbeit.NarrationMicroservice.Enteties.Story;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "StoryByUser")
public class StoryByUser {

    @Id
    @GeneratedValue(generator = "story_by_user_generator")
    @SequenceGenerator(name = "story_by_user_generator", sequenceName = "story_by_user_sequence", initialValue = 1)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
    @NotNull
    @Min(1)
    private long userId;

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @Getter
    @Setter
    @NotNull
    @Min(1)
    private Long narrationId;

    @Getter
    @Setter
    @NotNull
    @Min(1)
    private int storyNr;

    @Getter
    @Setter
    @NotNull
    private boolean canBeSeen;

    @Getter
    @Setter
    @NotNull
    private boolean finished;
}
