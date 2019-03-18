/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.repository.mongodb.management.mapper;

import io.gravitee.repository.management.model.ServiceView;
import io.gravitee.repository.mongodb.management.internal.model.ServiceViewMongo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jokki
 */
public class GraviteeDozerMapperTest {

    @Test
    public void testMapper() throws Exception {
        GraviteeMapper mapper = new GraviteeDozerMapper();
        ServiceView serviceView = new ServiceView();
        serviceView.setName("name");
        serviceView.setId("id");
        List<ServiceView> views = new ArrayList<>();
        views.add(serviceView);
        ServiceViewMongo serviceViewMongo = mapper.map(serviceView, ServiceViewMongo.class);
        Assert.assertTrue(serviceViewMongo != null && "id".equals(serviceViewMongo.getId()));
    }
}