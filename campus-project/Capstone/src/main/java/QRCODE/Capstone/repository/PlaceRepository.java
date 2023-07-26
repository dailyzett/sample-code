package QRCODE.Capstone.repository;

import QRCODE.Capstone.domain.OldPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<OldPlace, Long> {
}
