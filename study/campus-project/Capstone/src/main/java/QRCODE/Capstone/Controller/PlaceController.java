package QRCODE.Capstone.Controller;

import QRCODE.Capstone.domain.OldPlace;
import QRCODE.Capstone.repository.MemberSearch;
import QRCODE.Capstone.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

//    @GetMapping("/place_history")
//    public String place(Model model){
//        List<OldPlace> place = placeService.findAll();
//        model.addAttribute("places", place);
//        return "place/placeList";
//    }

    @GetMapping("/place_history")
    public String placeList(@ModelAttribute("memberSearch") MemberSearch memberSearch, Model model){
        List<OldPlace> oldPlace = placeService.findOldPlace(memberSearch);
        model.addAttribute("places", oldPlace);

        return "place/placeList";
    }

}
