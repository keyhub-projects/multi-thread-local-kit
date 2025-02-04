package keyhub.multithreadlocalkit.sandbox.component;

import keyhub.multithreadlocalkit.sandbox.dto.OrderIn;
import keyhub.multithreadlocalkit.sandbox.dto.OrderOut;
import keyhub.multithreadlocalkit.sandbox.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderConverter {
    OrderOut toDto(Order order);
    Order toEntity(OrderIn orderIn);
}
