package io.javabrains.reactiveworkshop;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.io.IOException;

public class Exercise5 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux() and ReactiveSources.userMono()

        // Subscribe to a flux using the error and completion hooks
        ReactiveSources.intNumbersFlux()
                .subscribe(item -> System.out.println(item),
                        error -> System.out.println(error.getMessage()),
                        () -> System.out.println("Complete"));

        // Subscribe to a flux using an implementation of BaseSubscriber
        ReactiveSources.intNumbersFlux()
                .subscribe(new MySubscriber<>());

        System.out.println("Press a key to end");
        System.in.read();
    }

}

class MySubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscription started.");
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value + " received");
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        System.out.println("Subscription completed");
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        System.out.println("Error occurred " + throwable.getMessage());
    }
}