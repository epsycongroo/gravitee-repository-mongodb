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
package io.gravitee.repositories.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import io.gravitee.repositories.mongodb.mapper.GraviteeDozerMapper;
import io.gravitee.repositories.mongodb.mapper.GraviteeMapper;

@Configuration
@ComponentScan
@EnableMongoRepositories
public class RepositoryConfiguration extends AbstractMongoConfiguration {

	@Override
	protected String getMappingBasePackage() {
		return getClass().getPackage().getName();
	}

	@Override
	protected String getDatabaseName() {
		return "gravitee";
	}
	
	@Bean
	public GraviteeMapper graviteeMapper(){
		return new GraviteeDozerMapper();
	}

	@Override
	public Mongo mongo() throws Exception {

		Mongo mongo = new MongoClient();
		mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}
}