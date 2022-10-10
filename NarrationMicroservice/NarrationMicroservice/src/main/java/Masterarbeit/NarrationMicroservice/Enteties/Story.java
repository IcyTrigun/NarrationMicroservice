package Masterarbeit.NarrationMicroservice.Enteties;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
@Table(name = "Stories")
public class Story {

    @Id
    @GeneratedValue(generator = "story_generator")
    @SequenceGenerator(name = "story_generator", sequenceName = "story_sequence", initialValue = 1)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String story;

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "narration_id")
    private Narration narration;

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
