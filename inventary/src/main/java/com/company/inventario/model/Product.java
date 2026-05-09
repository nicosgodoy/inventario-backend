package com.company.inventario.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String name;
     private BigDecimal price;
     private int account;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "category_id")
     @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
     private Category category;

     @Lob
     @Basic(fetch = FetchType.LAZY)
     @Column(name = "picture", columnDefinition = "longblob")
     private byte[] picture;


}
