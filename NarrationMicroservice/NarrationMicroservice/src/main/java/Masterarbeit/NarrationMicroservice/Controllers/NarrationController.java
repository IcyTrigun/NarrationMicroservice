package Masterarbeit.NarrationMicroservice.Controllers;

import Masterarbeit.NarrationMicroservice.Enteties.Narration;
import Masterarbeit.NarrationMicroservice.Exceptions.DoubleResourceNotAllowedException;
import Masterarbeit.NarrationMicroservice.Services.NarrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NarrationController {

    @Autowired
    private NarrationService narrationService;


    @PostMapping("/narration/set")
    public ResponseEntity<?> saveNarration(@RequestBody Narration narration, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            if(!narrationService.checkIfNarrationNameAlreadyExists(narration.getName())){
                narrationService.setNarration(narration);
                return ResponseEntity.status(HttpStatus.OK).body("Narration got saved successfully");
            }
           throw new DoubleResourceNotAllowedException("The NarrationName" + narration.getName()+ "already exists");
        }
    }

    @GetMapping("/narration/get/{id}")
    public Narration getNarration(@PathVariable Long id) {
        return narrationService.getNarration(id);
    }

    @GetMapping("/narration/getAll")
    public List<Narration> getAllNarrations() {
        return narrationService.getAllNarrations();
    }


    @GetMapping("/narration/getByNarrationName/{name}")
    public Narration getAllNarrationByName(@PathVariable String name) {
        return narrationService.getNarrationByName(name);
    }

    @PutMapping("/narration/update/{id}")
    public Narration updateNarration(@PathVariable Long id, @RequestBody Narration narration) {
        return narrationService.updateNarration(narration, id);
    }

    @DeleteMapping("/narration/delete/{id}")
    public ResponseEntity<?> deleteNarration(@PathVariable Long id) {
        return narrationService.deleteNarration(id);
    }
}
