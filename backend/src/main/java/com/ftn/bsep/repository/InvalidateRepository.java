package com.ftn.bsep.repository;

import com.ftn.bsep.model.InvalidateAlias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidateRepository extends JpaRepository<InvalidateAlias, Long> {
    InvalidateRepository findByAlias(String alias);
}
