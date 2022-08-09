package Masterarbeit.NarrationMicroservice.Services;


import Masterarbeit.NarrationMicroservice.Enteties.StoryByUser;
import Masterarbeit.NarrationMicroservice.Exceptions.DoubleResourceNotAllowedException;
import Masterarbeit.NarrationMicroservice.Exceptions.ResourceNotFoundException;
import Masterarbeit.NarrationMicroservice.HelperClasses.CopyPropertiesOfEntity;
import Masterarbeit.NarrationMicroservice.Repositories.StoryByUserRepository;
import Masterarbeit.NarrationMicroservice.Request.SetStoryByUserRequestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoryByUserService {

    @Autowired
    private StoryByUserRepository storyByUserRepository;

    @Autowired
    private StoryService storyService;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public StoryByUser getStoryByUser(Long id) {
        if (storyByUserRepository.findById(id).isPresent()) {
            return storyByUserRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no StoryByUser found at id" + id);
        }
    }

    public List<StoryByUser> getAllStoriesByUsers() {
        return storyByUserRepository.findAll();
    }

    public StoryByUser setStoryByUser(SetStoryByUserRequestRequest narrationByUserRequest) {

        if(!storyByUserRepository.getStoryByUserByUserIdAndStoryId(narrationByUserRequest.getUserId(), narrationByUserRequest.getStoryId()).isPresent()){
            StoryByUser storyByUser = new StoryByUser();
            storyByUser.setStory(storyService.getStory(narrationByUserRequest.getStoryId()));
            storyByUser.setUserId(narrationByUserRequest.getUserId());
            storyByUser.setCanBeSeen(narrationByUserRequest.isCanBeSeen());
            storyByUser.setFinished(narrationByUserRequest.isFinished());
            return storyByUserRepository.save(storyByUser);
        }else{
            throw new DoubleResourceNotAllowedException("Es gab schon ein StoryByUserObjekt für die UserId"+ narrationByUserRequest.getUserId()+ "und NarrationId"+narrationByUserRequest.getStoryId());
        }
    }



    public StoryByUser updateStoryByUser(StoryByUser storyByUserRequest, long id) {
        StoryByUser storyByUser = getStoryByUser(id);
        copyPropertiesOfEntity.copyNonNullProperties(storyByUserRequest, storyByUser);
        return storyByUserRepository.save(storyByUser);
    }

    public ResponseEntity<?> deleteStoryByUser(long id) {
        return storyByUserRepository.findById(id)
                .map(storyByUser -> {
                    storyByUserRepository.delete(storyByUser);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StoryByUser not found with id " + id));
    }

    public List<StoryByUser> getStoriesByUserAndNarrationId(Long userId,Long narrationId) {
      List<StoryByUser> StoriesOfUser = storyByUserRepository.getStoriesByUserByUserId(userId);
      List<StoryByUser> storiesOfUser = new ArrayList<>();
      for(StoryByUser storyByUser : StoriesOfUser ){
          if(storyByUser.getStory().getNarration().getId()==narrationId){
            storiesOfUser.add(storyByUser);
          }
      }
      return storiesOfUser;
    }

    public List<StoryByUser> getStoryOfUserWhichCanBeSeen(Long userId) {
       return storyByUserRepository.getStoriesOfUserWhichCanBeSeen(userId);
    }
}
