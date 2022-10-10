package Masterarbeit.NarrationMicroservice.Services;

import Masterarbeit.NarrationMicroservice.Enteties.Narration;
import Masterarbeit.NarrationMicroservice.Enteties.Story;
import Masterarbeit.NarrationMicroservice.Exceptions.ResourceNotFoundException;
import Masterarbeit.NarrationMicroservice.HelperClasses.CopyPropertiesOfEntity;
import Masterarbeit.NarrationMicroservice.Repositories.StoryRepository;
import Masterarbeit.NarrationMicroservice.Request.SetStoryByUserRequest;
import Masterarbeit.NarrationMicroservice.Request.SetStoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private NarrationService narrationService;


    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Story getStory(Long id) {
        if (storyRepository.findById(id).isPresent()) {
            return storyRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no Story found at id" + id);
        }
    }

    public List<Story> getStoriesByNarrationId(Long narrationId) {
       return storyRepository.getStoriesByNarrationId(narrationId);
    }
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    public Story setStory(SetStoryRequest setStoryRequest) {
        Story story = new Story();
        Narration narration = narrationService.getNarration(setStoryRequest.getNarrationId());
        story.setNarration(narration);
        story.setImagePath(setStoryRequest.getImagePath());
        story.setName(setStoryRequest.getName());
        story.setNr(setStoryRequest.getNr());
        story.setStory(setStoryRequest.getText());
        story.setLast(setStoryRequest.isLast());
        return storyRepository.save(story);
    }

    public Story updateStory(Story storyRequest, long id) {
        Story story = getStory(id);
        copyPropertiesOfEntity.copyNonNullProperties(storyRequest, story);
        return storyRepository.save(story);
    }



    public ResponseEntity<?> deleteStory(long id) {
        return storyRepository.findById(id)
                .map(story -> {
                    storyRepository.delete(story);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Story not found with id " + id));
    }


    public List<Story> getStoriesOfNarrationSorted(Long narrationId){
        return storyRepository.getStoriesOfNarrationSorted(narrationId);
    }

    public String getStoryHTMLCode(Long storyId,Long userId) {
        Story story = getStory(storyId);
        return createStoryHTMLCode(story,userId);
    }

    public String getStoryHTMLCode(Long narrationId,int Nr,Long userId) {
        Story story = getStoryByNarrationIdAndNr(narrationId,Nr);
        return createStoryHTMLCode(story,userId);
    }

    public String createStoryHTMLCode(Story story,Long userId){
        return " <div style=\"background-image:url('"+ story.getImagePath()+"');\" class=\"narrationElement\" id=\""+story.getId()+"\">\n" +
                "        <span onclick=\"storyFinishedLogic ('"+ story.getId()+ "'"+userId+")\" class=\"close\">&times;</span>\n" +
                "        <div class=\"narrationTitle\">" + story.getName() +"  </div>\n" +
                "        <div class=\"narrationNavText\">"+ story.getStory()+"</div>\n" +
                "        </div>";
    }

    public Story getStoryByNarrationIdAndNr(Long narrationId,int Nr){
        Optional<Story> storyOptional = storyRepository.getStoryByNarrationIdAndNr(narrationId,Nr);
        if (storyOptional.isPresent()) {
            return storyOptional.get();
        } else {
            throw new ResourceNotFoundException("no Story found at narrationId " + narrationId+ " and Nr "+Nr);
        }
    }
}
