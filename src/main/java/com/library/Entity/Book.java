package com.library.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.library.Util.Availability;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 30 characters")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Size(min = 2, max = 30, message = "Author must be between 2 and 30 characters")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Loan> loans = new ArrayList<>();

}
