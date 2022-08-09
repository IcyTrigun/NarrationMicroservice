package Masterarbeit.NarrationMicroservice;

import Masterarbeit.NarrationMicroservice.Enteties.Narration;
import Masterarbeit.NarrationMicroservice.Request.SetStoryByUserRequestRequest;
import Masterarbeit.NarrationMicroservice.Request.SetStoryRequest;
import Masterarbeit.NarrationMicroservice.Services.StoryByUserService;
import Masterarbeit.NarrationMicroservice.Services.NarrationService;
import Masterarbeit.NarrationMicroservice.Services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SaveDefaultNarrationData implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(SaveDefaultNarrationData.class);

    @Autowired
    NarrationService narrationService;

    @Autowired
    StoryService storyService;
    @Autowired
    StoryByUserService storyByUserService;

    @Override
    public void run(String... args) throws Exception {
        setNarrations();
        setStories();
        setStoryUsers();
    }

    public void setNarrations(){
        if(narrationService!=null){
            logger.info("saving Narrations");
            narrationService.setNarration(new Narration(1L,"die Drachenhöhle","das Drachenhöhlen Abenteuer","dummyimagePath"));
            narrationService.setNarration(new Narration(2L,"die Wikinger","Für Valhalla","dummyimagePath"));
        }else{
            logger.info("narrationService is null");
        }
    }

    public void setStories(){
        if(storyService!=null){
            logger.info("saving Stories");
            storyService.setStory(new SetStoryRequest(1L,"eingang in die Drachenhöhle","ihr geht in die höhle blaaaaa",1,false,"keksPath"));
            storyService.setStory(new SetStoryRequest(1L,"in der Höhle","dunkle höhle",1,false,"keksPath"));
            storyService.setStory(new SetStoryRequest(1L,"der drache kommt","Drachenatem",1,false,"keksPath"));

        }else{
            logger.info("StoryService is null");
        }
    }

    public void setStoryUsers(){
        if(storyByUserService !=null){
            logger.info("saving NarrationUsers");
            storyByUserService.setStoryByUser(new SetStoryByUserRequestRequest(1L,1L,true,false));
            storyByUserService.setStoryByUser(new SetStoryByUserRequestRequest(2L,1L,true,false));
            storyByUserService.setStoryByUser(new SetStoryByUserRequestRequest(3L,1L,true,false));

        }else{
            logger.info("narrationUserService; is null");
        }
    }
}
