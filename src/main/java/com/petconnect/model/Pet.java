package com.petconnect.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;
    private String category;
    private int age;
    private String gender;
    private String description;
    private String imagePath;

    @ManyToOne(fetch = FetchType.EAGER)  // <-- make EAGER so owner is loaded
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"pets", "bookings"}) // avoid recursion
    private User owner;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
