package com.manal.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String company;

    @NonNull
    private String startDate;

    @NonNull
    private String endDate;

    @NonNull
    private String type;

    @NonNull
    @Column(length = 2000)
    private String tasks;

    @NonNull
    @Column(length = 1000)
    private String technologies;

    @Column(columnDefinition = "TEXT")
    private String titleEn;

    @Column(columnDefinition = "TEXT")
    private String tasksEn;

    @Column(columnDefinition = "TEXT")
    private String typeEn;
}