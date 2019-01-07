/*
 * Copyright 2018 Pivotal Software, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.dubbo;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.commons.lang3.RandomStringUtils;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 *
 * @author xincao9@gmail.com
 */
public class ReactorTest {

    public static void main(String... args) throws Throwable {
        Flux.just(1, 2, 3, 4, 5, 6).subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3, 4, 5, 6}).subscribe(System.out::println);
        Flux.fromIterable(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6})).subscribe(System.out::println);
        Flux.fromStream(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6}).stream()).subscribe(System.out::println);
        Flux.just().subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        try {
            Flux.error(new Exception("flux error")).subscribe(System.out::println);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Mono.just(1).subscribe(System.out::println);
        Mono.empty().subscribe(System.out::println);
        Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
        try {
            Mono.error(new Exception("mono error")).subscribe(System.out::println);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Flux<Integer> flux = Flux.create((FluxSink<Integer> sink) -> {
            sink.next(1);
            sink.next(2);
            sink.next(3);
            sink.next(4);
            sink.next(5);
            sink.next(6);
            sink.complete();
        });
        flux.subscribe(System.out::println, System.err::println, () -> {
            System.out.println("完成");
        });
        Flux.range(1, 6).map(i -> Double.valueOf(Math.pow(i, 2)).intValue()).subscribe(System.out::println);
        String str = RandomStringUtils.randomNumeric(1024);
        Flux.just(str).flatMap((String s) -> {
            Flux<Character> f = Flux.create((FluxSink<Character> sink) -> {
                for (char c : s.toCharArray()) {
                    sink.next(c);
                }
                sink.complete();
            });
            return f;
        }).filter((Character t) -> t.compareTo('5') < 0).subscribe(System.out::println);
        Flux.range(1, 6).doOnRequest(System.out::println).subscribe(
                new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("response = " + value);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                request(1);
            }

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1);
            }
        }
        );
        Flux.range(1, 6)
                .map(i -> 10 / (3 - i))
                .retry(1)
                .subscribe(System.out::println, System.err::println);
        CountDownLatch cdl = new CountDownLatch(1);
        Mono.fromCallable(() -> {
            TimeUnit.SECONDS.sleep(1);
            return new Date().getTime();
        }).subscribeOn(Schedulers.elastic()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long t) {
                System.out.println(t);
                cdl.countDown();
            }
        });
        cdl.await();
    }
}
