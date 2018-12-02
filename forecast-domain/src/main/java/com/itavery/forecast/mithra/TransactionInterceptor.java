package com.itavery.forecast.mithra;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/5/18            
 |            
 *===========================================================================*/

import com.gs.fw.common.mithra.MithraManagerProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Primary
public class TransactionInterceptor {

    @Around("@annotation(com.itavery.forecast.mithra.annotation.Transactional)")
    public Object invoke(ProceedingJoinPoint methodInvocation){
        return getObject(methodInvocation);
    }

    private Object getObject(final ProceedingJoinPoint methodInvocation){
        return MithraManagerProvider.getMithraManager().executeTransactionalCommand(mithraTransaction -> methodInvocation.proceed());
    }
}
