package com.boatfly.spring.transaction.transcationtemplate;

import com.boatfly.spring.transaction.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Controller
public class OrderService {
    @Autowired
    private TransactionTemplate transactiontemplate;

    public String sendOrderTemplate(Order order) {
        final Long orderId = order.getOrderId();
        Boolean lockStatus = transactiontemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //处理代码块事务
                Order order = new Order();
                order.setOrderId(orderId);
                order.setOrderStatus(4);//处理中
                order.setVersion(0);//锁标志
                //return 1 == orderDao.updateByVersion(order);
                return null;//用上一句
            }
        });
        //只有第一个进来的线程flag=true
        //基于状态机的乐观锁
        if(!lockStatus){ //只允许一个请求调用第三方接口，幂等性
            //调用第三方服务接口@TODO

            transactiontemplate.execute(new TransactionCallback() {

                @Override
                public Object doInTransaction(TransactionStatus transactionStatus) {
                    //处理代码块事务
                    return null;
                }
            });
        }else{
            // 锁失败 @TODO
        }
        return "";
    }
}
