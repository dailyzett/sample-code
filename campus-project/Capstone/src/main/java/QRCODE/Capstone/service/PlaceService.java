package QRCODE.Capstone.service;

import QRCODE.Capstone.domain.OldPlace;
import QRCODE.Capstone.repository.MemberSearch;
import QRCODE.Capstone.repository.PlaceRepository;
import QRCODE.Capstone.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final SearchRepository searchRepository;

    public Long save(OldPlace oldPlace) {
        placeRepository.save(oldPlace);
        return oldPlace.getId();
    }

    public List<OldPlace> findAll(){
        return placeRepository.findAll();
    }

    public List<OldPlace> findPlace(MemberSearch memberSearch){
        return searchRepository.findAllByCriteria(memberSearch);
    }

    public List<OldPlace> findOldPlace(MemberSearch memberSearch) {
        return searchRepository.findAllByCriteria(memberSearch);
    }

}
