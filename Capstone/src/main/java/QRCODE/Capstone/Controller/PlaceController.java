package QRCODE.Capstone.Controller;

import QRCODE.Capstone.domain.OldPlace;
import QRCODE.Capstone.repository.MemberSearch;
import QRCODE.Capstone.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/place_history")
    public String place(Model model){
        List<OldPlace> place = placeService.findAll();
        model.addAttribute("places", place);
        return "place/placeList";
    }

}
