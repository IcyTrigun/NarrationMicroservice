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

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1 AND m.story_id = ?2",nativeQuery = true)
     Optional<StoryByUser> getStoryByUserByUserIdAndStoryId(long userId, long storyId);

    @Query(value = "SELECT * FROM story_by_user m WHERE m.user_id =?1",nativeQuery = true)
    List<StoryByUser> getStoriesByUserByUserId(long userId);
}
