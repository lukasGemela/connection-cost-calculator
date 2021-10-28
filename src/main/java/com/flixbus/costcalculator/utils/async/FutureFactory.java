package com.flixbus.costcalculator.utils.async;


import com.flixbus.costcalculator.utils.logging.MdcContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class FutureFactory {

    public Future<Void> runAsyncWithMdc(Runnable runnable, MdcContext mdcContext) {
        return CompletableFuture.runAsync(withMdc(runnable, mdcContext));
    }

    private static Runnable withMdc(Runnable runnable, MdcContext context) {
        final var mdc = context.getContextMap();
        return () -> {
            context.setContextMap(mdc);
            runnable.run();
        };
    }
}
