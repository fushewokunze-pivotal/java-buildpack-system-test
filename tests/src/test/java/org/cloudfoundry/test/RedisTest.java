/*
 * Copyright 2013-2016 the original author or authors.
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

package org.cloudfoundry.test;

import org.cloudfoundry.test.support.application.Application;
import org.cloudfoundry.test.support.service.RedisService;
import org.cloudfoundry.util.test.TestSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.core.tuple.Tuple;
import reactor.core.tuple.Tuple2;

public final class RedisTest extends AbstractTest<Tuple2<String, String>> {

    @Autowired
    private RedisService service;

    public RedisTest() {
        super("redis");
    }

    @Override
    protected void test(Application application, TestSubscriber<Tuple2<String, String>> testSubscriber) {
        Mono
            .when(application.request("/redis/check-access"), application.request("/redis/url"))
            .subscribe(testSubscriber
                .assertEquals(Tuple.of("ok", "redis://fake")));
    }

}