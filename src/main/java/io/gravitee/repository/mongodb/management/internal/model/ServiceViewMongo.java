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
package io.gravitee.repository.mongodb.management.internal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

/**
 * @author Jokki
 */
@Document(collection = "serviceviews")
public class ServiceViewMongo {

    @Id
    private String id;
    private String pid;
    private String[] ancestors;
    private String name;
    private String description;
    private boolean hidden;
    private int order;
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String[] getAncestors() {
        return ancestors;
    }

    public void setAncestors(String[] ancestors) {
        this.ancestors = ancestors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceViewMongo)) return false;
        ServiceViewMongo view = (ServiceViewMongo) o;
        return Objects.equals(id, view.id) &&
                Objects.equals(name, view.name) &&
                Objects.equals(description, view.description) &&
                Objects.equals(pid, view.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, pid);
    }

    @Override
    public String toString() {
        return "ServiceViewMongo{" +
                "id='" + id + '\'' +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hidden='" + hidden + '\'' +
                '}';
    }
}
