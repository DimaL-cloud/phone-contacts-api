package ua.dmytrolutsyuk.phonecontactsapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "confirmation_token")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", unique = false, nullable = false)
    private Date createdDate;

    @OneToOne
    @JoinColumn( name = "user_id", nullable = false)
    private User user;
}
