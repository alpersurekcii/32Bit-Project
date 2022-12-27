package com.alpersurekci.project.ExternalService.DAO.Repository;
/**
 * Rol için oluşturulan repositorydir
 * Rol ile ilgili database işlemleri burada gerçekleşir.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
