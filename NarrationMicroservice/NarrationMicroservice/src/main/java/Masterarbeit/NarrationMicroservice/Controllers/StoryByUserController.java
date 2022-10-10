package Masterarbeit.NarrationMicroservice.Controllers;

import Masterarbeit.NarrationMicroservice.Enteties.StoryByUser;
import Masterarbeit.NarrationMicroservice.Request.SetStoryByUserRequest;
import Masterarbeit.NarrationMicroservice.Services.StoryByUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryByUserController {

    @Autowired
    private StoryByUserService storyByUserService;


    @PostMapping("/storyByUser/set")
    public ResponseEntity<?> saveStoriesByUser(@RequestBody SetStoryByUserRequest narrationByUserRequest, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            storyByUserService.setStoryByUser(narrationByUserRequest);
            return ResponseEntity.status(HttpStatus.OK).body("NarrationByUser got saved successfully");

        }
    }

    @PostMapping("/storyByUser/setStoryFinished/{storyId}/{userId}")
    public ResponseEntity<?> saveStoryByUserStoryFinished(@PathVariable Long storyId, @PathVariable Long userId) throws Exception {
            storyByUserService.setStoryByUser(new SetStoryByUserRequest(storyId,userId,true,true));
            return ResponseEntity.status(HttpStatus.OK).body("NarrationByUser got saved successfully");
    }

    @GetMapping("/storyByUser/get/{id}")
    public StoryByUser getStoriesByUser(@PathVariable Long id) {
        return storyByUserService.getStoryByUser(id);
    }

    @GetMapping("/storyByUser/getStoriesOfUserWhichCanBeSeen/{userId}")
    public List<StoryByUser> getStoriesOfUserWhichCanBeSeen(@PathVariable Long userId) {
        return storyByUserService.getStoryOfUserWhichCanBeSeen(userId);
    }

    @GetMapping("/storyByUser/getStoriesOfUserHTMLCode/{userId}")
    public String getStoriesOfUserHTMLCode(@PathVariable Long userId) {
        return storyByUserService.getStoriesOfUserHTMLCode(userId);
    }



    @GetMapping("/storyByUser/getStoriesByUserAndNarrationId/{userId}/{narrationId}")
    public List<StoryByUser> getStoriesOfUserByUserAndNarrationId(@PathVariable Long userId,@PathVariable Long narrationId) {
        return storyByUserService.getStoriesByUserAndNarrationId(userId,narrationId);
    }


    @GetMapping("/storyByUser/getAll")
    public List<StoryByUser> getAllStoriesByUsers() {
        return storyByUserService.getAllStoriesByUsers();
    }

    @GetMapping("/storyByUser/getAllNarrationIdsOfUser/{userId}")
    public List<Long> getAllStoriesOfUsersGroupedByNarrationIds(@PathVariable Long userId) {
        return storyByUserService.getNarrationIdsOfUser(userId);
    }

    @PutMapping("/storyByUser/update/{id}")
    public StoryByUser updateStoryByUser(@PathVariable Long id, @RequestBody SetStoryByUserRequest storyByUserRequest) {
        return storyByUserService.updateStoryByUser(storyByUserRequest, id);
    }

    @DeleteMapping("/storyByUser/delete/{id}")
    public ResponseEntity<?> deleteStoryByUser(@PathVariable Long id) {
        return storyByUserService.deleteStoryByUser(id);
    }
}
