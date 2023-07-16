package com.example.jpapractice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTables({
        @SecondaryTable(
                name = "writer_info",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "writer_id", referencedColumnName = "id")),
        @SecondaryTable(
                name = "writer_address",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "writer_id", referencedColumnName = "id"))
})
public class Writer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Intro intro;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(table = "writer_address", name = "street")),
            @AttributeOverride(name = "city", column = @Column(table = "writer_address", name = "city")),
            @AttributeOverride(name = "state", column = @Column(table = "writer_address", name = "state")),
            @AttributeOverride(name = "zipCode", column = @Column(table = "writer_address", name = "zip_code"))
    })
    private Address address;
}
