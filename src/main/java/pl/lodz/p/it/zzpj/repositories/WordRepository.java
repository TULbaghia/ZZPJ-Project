package pl.lodz.p.it.zzpj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entities.model.Word;

@Repository
@Transactional(readOnly = true)
public interface WordRepository extends JpaRepository<Word, Long> {
}
