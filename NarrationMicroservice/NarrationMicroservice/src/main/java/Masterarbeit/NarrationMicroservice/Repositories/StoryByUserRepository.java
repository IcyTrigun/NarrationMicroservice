package Masterarbeit.NarrationMicroservice.Repositories;

import Masterarbeit.NarrationMicroservice.Enteties.StoryByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryByUserRepository extends JpaRepository<StoryByUser, Long> {

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1 AND m.can_be_seen = TRUE ",nativeQuery = true)
    List<StoryByUser> getStoriesOfUserWhichCanBeSeen(Long userId);

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1 AND m.can_be_seen = TRUE GROUP BY narration_id",nativeQuery = true)
    List<StoryByUser> getStoriesOfUserWhichCanBeSeenGroupedByNarrationId(Long userId);

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1 AND m.story_id = ?2",nativeQuery = true)
     Optional<StoryByUser> getStoryByUserByUserIdAndStoryId(long userId, long storyId);

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1",nativeQuery = true)
    List<StoryByUser> getStoriesByUserByUserId(long userId);

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1 AND m.narration_id =?2 ORDER BY story_nr ASC",nativeQuery = true)
    List<StoryByUser> getStoriesByUserAndNarrationId(long userId, Long narrationId);


    @Query(value = "SELECT DISTINCT narration_id FROM story_by_user m WHERE m.user_id =?1 ",nativeQuery = true)
    List<Long> getNarrationIdsOfUser(Long userId);
}
