package Masterarbeit.NarrationMicroservice.Repositories;


import Masterarbeit.NarrationMicroservice.Enteties.Narration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NarrationRepository extends JpaRepository<Narration,Long> {

    Optional<Narration> getNarrationByName(String name);
}
