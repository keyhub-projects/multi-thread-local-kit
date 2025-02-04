package keyhub.multithreadlocalkit.sandbox.service;

import keyhub.multithreadlocalkit.sandbox.component.OrderConverter;
import keyhub.multithreadlocalkit.sandbox.dto.OrderIn;
import keyhub.multithreadlocalkit.sandbox.dto.OrderOut;
import keyhub.multithreadlocalkit.sandbox.entity.Order;
import keyhub.multithreadlocalkit.sandbox.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderConverter converter;

    @Transactional(readOnly = true)
    public List<OrderOut> list() {
        var result = repository.findAll();
        return result.stream().map(converter::toDto).toList();
    }

    @Transactional
    public OrderOut order(OrderIn inputDto) {
        Order order = converter.toEntity(inputDto);
        repository.save(order);
        return converter.toDto(order);
    }
}
