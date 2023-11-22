package org.spring.dev.company.repository.department;

import org.spring.dev.company.entity.department.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
