package com.book.search.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class History extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @NonNull
    @Column(nullable = false, updatable = false)
    private String keyword;

}
