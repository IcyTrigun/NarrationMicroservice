package Masterarbeit.NarrationMicroservice.Services;


import Masterarbeit.NarrationMicroservice.Enteties.Narration;
import Masterarbeit.NarrationMicroservice.Enteties.Story;
import Masterarbeit.NarrationMicroservice.Enteties.StoryByUser;
import Masterarbeit.NarrationMicroservice.Exceptions.DoubleResourceNotAllowedException;
import Masterarbeit.NarrationMicroservice.Exceptions.ResourceNotFoundException;
import Masterarbeit.NarrationMicroservice.HelperClasses.CopyPropertiesOfEntity;
import Masterarbeit.NarrationMicroservice.Repositories.StoryByUserRepository;
import Masterarbeit.NarrationMicroservice.Request.SetStoryByUserRequest;
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

    @Autowired
    private NarrationService narrationService;

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

    public StoryByUser setStoryByUser(SetStoryByUserRequest narrationByUserRequest) {

        if(!storyByUserRepository.getStoryByUserByUserIdAndStoryId(narrationByUserRequest.getUserId(), narrationByUserRequest.getStoryId()).isPresent()){
            Story story =    storyService.getStory(narrationByUserRequest.getStoryId());
            StoryByUser storyByUser = new StoryByUser();
            storyByUser.setStory(story);
            storyByUser.setNarrationId(story.getNarration().getId());
            storyByUser.setStoryNr(story.getNr());
            storyByUser.setUserId(narrationByUserRequest.getUserId());
            storyByUser.setCanBeSeen(narrationByUserRequest.isCanBeSeen());
            storyByUser.setFinished(narrationByUserRequest.isFinished());
            return storyByUserRepository.save(storyByUser);
        }else{
            throw new DoubleResourceNotAllowedException("Es gab schon ein StoryByUserObjekt für die UserId"+ narrationByUserRequest.getUserId()+ "und NarrationId"+narrationByUserRequest.getStoryId());
        }
    }



    public StoryByUser updateStoryByUser(SetStoryByUserRequest storyByUserRequest, long id) {
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

    public String getStoriesOfUserHTMLCode(Long userId) {

        List<Long> narrationIdsOfUser = getNarrationIdsOfUser(userId);
        String htmlString ="";

        for (Long narrationId: narrationIdsOfUser) {
            List<StoryByUser> storiesOfNarration = getStoriesOfNarrationOfUser(userId,narrationId);

            Narration narration = narrationService.getNarration(narrationId);
            htmlString+= " <div class=\"dropdown\">\n" +
                    "        <button class=\"dropbtn navTopButton\" onclick=\"showMissionElements('"+ narration.getName()+"')\">\n" +
                    "            <span class=\"navUnfoldIcon\" id=\"unfoldIcon"+narration.getName()+"\"> > </span>\n" +
                    "            <span>"+narration.getName()+"</span>\n" +
                    "        </button>\n" +
                    "        <div class=\"dropdown-content\" id=\""+narration.getName()+"\">\n";

            for (StoryByUser storyOfNarration:storiesOfNarration) {
                htmlString+= " <div class=\"navElement\">\n" +
                        "                <div class=\"narrationNavText\">\n" +
                        "                    <span class=\"narrationNr\">"+storyOfNarration.getStory().getNr()+"</span>\n" +
                        "                    <span class=\"narrationName\">"+storyOfNarration.getStory().getName()+"</span>\n" +
                        "                    <div style=\"background-image:"+storyOfNarration.getStory().getImagePath() +"\" class=\"narrationElement moreNarrationInformation navElementMoreInformation\" id=\""+storyOfNarration.getId()+"\">\n" +
                        "                        <div class=\"narrationTitle\">"+storyOfNarration.getStory().getName()+ " </div>\n" +
                        "                        <div class=\"narrationNavText\">"+storyOfNarration.getStory().getStory()+ "</div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n";
            }
            htmlString += "  </div> \n </div>";

        }

        return htmlString;
    }

    public List<StoryByUser> getStoriesOfNarrationOfUser(long userId,Long narrationId){
        return storyByUserRepository.getStoriesByUserAndNarrationId(userId,narrationId);
    }



    public List<Long> getNarrationIdsOfUser(Long userId) {
        return storyByUserRepository.getNarrationIdsOfUser(userId);
    }
}
