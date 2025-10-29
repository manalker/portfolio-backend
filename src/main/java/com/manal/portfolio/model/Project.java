package com.manal.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {

    @Serial
    private static final long serialVersionUID = 3662619892003028675L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String title;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String description;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String technologies;

    @NonNull
    private String imageUrl;
}
