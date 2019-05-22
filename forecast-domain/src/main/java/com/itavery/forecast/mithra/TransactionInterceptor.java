package com.itavery.forecast.mithra;

import com.gs.fw.common.mithra.MithraManagerProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-05
 * https://github.com/helloavery
 */

@Aspect
@Component
public class TransactionInterceptor {

    @Around("@annotation(com.itavery.forecast.mithra.annotation.Transactional)")
    public Object invoke(ProceedingJoinPoint methodInvocation){
        return getObject(methodInvocation);
    }

    private Object getObject(final ProceedingJoinPoint methodInvocation){
        return MithraManagerProvider.getMithraManager().executeTransactionalCommand(mithraTransaction -> methodInvocation.proceed());
    }
}
