package Masterarbeit.NarrationMicroservice.Repositories;

import Masterarbeit.NarrationMicroservice.Enteties.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {
    List<Story> getStoriesByNarrationId(Long narrationId);

    @Query(value = "SELECT * FROM story m WHERE m.narration_id =?1 ORDER BY nr ASC ",nativeQuery = true)
    List<Story> getStoriesOfNarrationSorted(Long narrationId);

    @Query(value = "SELECT * FROM story m WHERE m.narration_id =?1 ANd m.nr =?2",nativeQuery = true)
    Optional<Story> getStoryByNarrationIdAndNr(Long narrationId, int nr);
}
