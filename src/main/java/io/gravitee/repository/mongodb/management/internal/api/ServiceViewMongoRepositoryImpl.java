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
package io.gravitee.repository.mongodb.management.internal.api;

import io.gravitee.repository.mongodb.management.internal.model.ServiceViewMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jokki
 */
public class ServiceViewMongoRepositoryImpl implements ServiceViewMongoRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<ServiceViewMongo> findMaxOrderByPid(String s) {
        Query query = Query.query(Criteria.where("pid").is(s));
        query.with(Sort.by(Sort.Direction.DESC, "order"));
        return Optional.ofNullable(mongoTemplate.findOne(query, ServiceViewMongo.class));
    }

    @Override
    public List<String> deleteByPid(String s) {
        Query query = Query.query(Criteria.where("id").is(s).and("ancestors").is(s));
        List<String> deleteIds = mongoTemplate.find(query, ServiceViewMongo.class)
                .stream().map(ServiceViewMongo::getId).collect(Collectors.toList());
        mongoTemplate.remove(query, ServiceViewMongo.class);
        return deleteIds;
    }

    @Override
    public void updateOrderByPid(String pid, int post, int number) {
        Query query = Query.query(Criteria.where("pid").is(pid).and("order").gte(post));
        Update update = new Update().inc("order", number);
        mongoTemplate.updateMulti(query, update, ServiceViewMongo.class);
    }
}
