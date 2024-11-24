package org.zeropage.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zeropage.portal.domain.ForumFile;

@Repository
public interface ForumFileRepository extends JpaRepository<ForumFile, Long> {
}