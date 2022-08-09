package Masterarbeit.NarrationMicroservice.Enteties;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Narrations")
public class Narration {

    @Id
    @GeneratedValue(generator = "narration_generator")
    @SequenceGenerator(name = "narration_generator", sequenceName = "narration_sequence", initialValue = 1)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String name;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String description;

    @Getter
    @Setter
    private String imagePath;

}
