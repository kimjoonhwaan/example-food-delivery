package fooddelivery;

import fooddelivery.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverdeliverystarted_주문상태변경(@Payload deliverystarted deliverystarted){

        if(deliverystarted.isMe() && deliverystarted.getOrderId()!=null){

//            try {
//                // 원래 데이터가 트랜잭션 커밋되기도 전에 이벤트가 너무 빨리 도달하는 경우를 막기 위함
//                Thread.currentThread().sleep(3000); //  no good. --> pay 가 TX 를 마친 후에만 실행되도록 수정함
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println("deliverystarted orderId = " + deliverystarted.getOrderId());

            // Correlation id 는 'orderId' 임
            주문Repository.findById(Long.valueOf(deliverystarted.getOrderId())).ifPresent((주문)->{
               주문.set상태("deliverystarted");
               주문Repository.save(주문);
            });

        }
    }

}
