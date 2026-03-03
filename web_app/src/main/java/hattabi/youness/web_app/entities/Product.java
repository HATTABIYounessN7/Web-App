package main.java.hattabi.youness.web_app.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private string name;
    private string price;

    @Temporal(TemporalType.DATE)
    private Date createdAt;
}
