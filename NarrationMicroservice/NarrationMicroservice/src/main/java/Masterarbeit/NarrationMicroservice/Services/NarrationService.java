package Masterarbeit.NarrationMicroservice.Services;

import Masterarbeit.NarrationMicroservice.Enteties.Narration;
import Masterarbeit.NarrationMicroservice.Exceptions.ResourceNotFoundException;
import Masterarbeit.NarrationMicroservice.HelperClasses.CopyPropertiesOfEntity;
import Masterarbeit.NarrationMicroservice.Repositories.NarrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NarrationService {

    @Autowired
    private NarrationRepository narrationRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    public Narration getNarration(Long id) {
        if (narrationRepository.findById(id).isPresent()) {
            return narrationRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no Narration found at id" + id);
        }
    }

    public List<Narration> getAllNarrations() {
        return narrationRepository.findAll();
    }

    public Narration setNarration(Narration narration) {

        return narrationRepository.save(narration);
    }

    public Narration updateNarration(Narration narrationRequest, long id) {
        Narration narration = getNarration(id);
        copyPropertiesOfEntity.copyNonNullProperties(narrationRequest, narration);
        return narrationRepository.save(narration);
    }



    public ResponseEntity<?> deleteNarration(long id) {
        return narrationRepository.findById(id)
                .map(narration -> {
                    narrationRepository.delete(narration);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Narration not found with id " + id));
    }

    public boolean checkIfNarrationNameAlreadyExists(String name) {
        return narrationRepository.getNarrationByName(name).isPresent();
    }

    public Narration getNarrationByName(String name) {
        if (narrationRepository.getNarrationByName(name).isPresent()) {
            return narrationRepository.getNarrationByName(name).get();
        } else {
            throw new ResourceNotFoundException("no Narration found at Name" + name);
        }
    }
}
