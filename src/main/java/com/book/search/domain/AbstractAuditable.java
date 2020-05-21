package com.book.search.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class AbstractAuditable<ID extends Serializable> implements Serializable {

    @Setter
    @Getter
    @Column(updatable = false)
    @CreatedDate
    protected LocalDateTime createdDate;

    @JsonIgnore
    @Getter
    @Column
    @LastModifiedDate
    protected LocalDateTime updatedDate;

    @Getter
    @Version
    @JsonIgnore
    private Long version;

}

