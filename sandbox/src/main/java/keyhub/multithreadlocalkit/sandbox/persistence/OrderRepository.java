package keyhub.multithreadlocalkit.sandbox.persistence;

import keyhub.multithreadlocalkit.sandbox.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
