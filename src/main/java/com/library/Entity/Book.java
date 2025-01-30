package com.library.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.library.Util.Availability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String title;


    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Loan> loans = new ArrayList<>();
}
