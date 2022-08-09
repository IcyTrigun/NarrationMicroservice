package Masterarbeit.NarrationMicroservice.Controllers;


import Masterarbeit.NarrationMicroservice.Enteties.Story;
import Masterarbeit.NarrationMicroservice.Request.SetStoryRequest;
import Masterarbeit.NarrationMicroservice.Services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {

    @Autowired
    private StoryService storyService;


    @PostMapping("/story/set")
    public ResponseEntity<?> saveStory(@RequestBody SetStoryRequest setStoryRequest, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            storyService.setStory(setStoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Mission got saved successfully");
        }
    }

    @GetMapping("/story/get/{id}")
    public Story getStory(@PathVariable Long id) {
        return storyService.getStory(id);
    }

    @GetMapping("/story/getByNarrationId/{narrationId}")
    public List<Story> getStoryByNarrationId(@PathVariable Long narrationId) {
        return storyService.getStoriesByNarrationId(narrationId);
    }

    @GetMapping("/story/getAll")
    public List<Story> getAllStories() {
        return storyService.getAllStories();
    }



    @PutMapping("/story/update/{id}")
    public Story updateStory(@PathVariable Long id, @RequestBody Story storyRequest) {
        return storyService.updateStory(storyRequest, id);
    }

    @DeleteMapping("/story/delete/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable Long id) {
        return storyService.deleteStory(id);
    }
}
