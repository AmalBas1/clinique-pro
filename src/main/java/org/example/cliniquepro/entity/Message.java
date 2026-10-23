package org.example.cliniquepro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String contenu;

    private LocalDateTime dateEnvoi;

    private String expediteurRole;

    @OneToOne
    @JoinColumn(name = "notification_id", unique = true)
    private Notification notification;
}
