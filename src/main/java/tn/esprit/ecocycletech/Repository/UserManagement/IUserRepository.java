package tn.esprit.ecocycletech.Repository.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
}
