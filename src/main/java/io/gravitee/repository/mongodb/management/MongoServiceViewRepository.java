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
package io.gravitee.repository.mongodb.management;

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ServiceViewRepository;
import io.gravitee.repository.management.model.ServiceView;
import io.gravitee.repository.mongodb.management.internal.api.ServiceViewMongoRepository;
import io.gravitee.repository.mongodb.management.internal.model.ServiceViewMongo;
import io.gravitee.repository.mongodb.management.mapper.GraviteeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jokki
 */
@Component
public class MongoServiceViewRepository implements ServiceViewRepository {
    private final Logger logger = LoggerFactory.getLogger(MongoServiceViewRepository.class);
    @Autowired
    private ServiceViewMongoRepository internalViewRepo;

    @Autowired
    private GraviteeMapper mapper;


    @Override
    public Set<ServiceView> findAll() throws TechnicalException {
        final List<ServiceViewMongo> views = internalViewRepo.findAll();
        return views.stream()
                .map(viewMongo -> mapper.map(viewMongo, ServiceView.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ServiceView> findMaxOrderByPid(String s) throws TechnicalException {
        logger.debug("Find ServiceView by PID [{}]", s);

        final ServiceViewMongo view = internalViewRepo.findMaxOrderByPid(s).orElse(null);

        logger.debug("Find ServiceView by PID [{}] - Done", s);
        return Optional.ofNullable(mapper.map(view, ServiceView.class));
    }

    @Override
    public List<String> deleteByPid(String s) throws TechnicalException {
        try {
            return internalViewRepo.deleteByPid(s);
        } catch (Exception e) {
            logger.error("An error occured when deleteByPid ServiceView [{}]", s, e);
            throw new TechnicalException("An error occured when deleteByPid ServiceView");
        }
    }

    @Override
    public void updateOrderByPid(String pid, int post, int number) throws TechnicalException {
        try {
            updateOrderByPid(pid, post, number);
        } catch (Exception e) {

            logger.error("An error occured when updateOrderByPid ServiceView", e);
            throw new TechnicalException("An error occured when updateOrderByPid ServiceView");
        }
    }

    @Override
    public Optional<ServiceView> findById(String viewId) throws TechnicalException {
        logger.debug("Find ServiceView by ID [{}]", viewId);

        final ServiceViewMongo view = internalViewRepo.findById(viewId).orElse(null);

        logger.debug("Find ServiceView by ID [{}] - Done", viewId);
        return Optional.ofNullable(mapper.map(view, ServiceView.class));
    }

    @Override
    public ServiceView create(ServiceView view) throws TechnicalException {
        logger.debug("Create ServiceView [{}]", view.getName());

        ServiceViewMongo viewMongo = mapper.map(view, ServiceViewMongo.class);
        ServiceViewMongo createdViewMongo = internalViewRepo.insert(viewMongo);

        ServiceView res = mapper.map(createdViewMongo, ServiceView.class);

        logger.debug("Create ServiceView [{}] - Done", view.getName());

        return res;
    }

    @Override
    public ServiceView update(ServiceView view) throws TechnicalException {
        if (view == null || view.getName() == null) {
            throw new IllegalStateException("ServiceView to update must have a name");
        }

        final ServiceViewMongo viewMongo = internalViewRepo.findById(view.getId()).orElse(null);

        if (viewMongo == null) {
            throw new IllegalStateException(String.format("No ServiceView found with name [%s]", view.getId()));
        }

        try {
            ServiceViewMongo viewMongoUpdated = internalViewRepo.save(mapper.map(view, ServiceViewMongo.class));
            return mapper.map(viewMongoUpdated, ServiceView.class);
        } catch (Exception e) {

            logger.error("An error occured when updating ServiceView", e);
            throw new TechnicalException("An error occured when updating ServiceView");
        }
    }

    @Override
    public void delete(String viewId) throws TechnicalException {
        try {
            internalViewRepo.deleteById(viewId);
        } catch (Exception e) {
            logger.error("An error occured when deleting ServiceView [{}]", viewId, e);
            throw new TechnicalException("An error occured when deleting ServiceView");
        }
    }
}
