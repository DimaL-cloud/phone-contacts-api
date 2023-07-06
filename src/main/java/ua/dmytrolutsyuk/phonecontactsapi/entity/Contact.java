package ua.dmytrolutsyuk.phonecontactsapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contacts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "email", unique = false, nullable = false)
    private Set<String> emails;

    @ElementCollection
    @CollectionTable(name = "phone_numbers", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "phone_number", unique = false, nullable = false)
    private Set<String> phoneNumbers;

    @Column(name = "image_url", unique = true, nullable = false)
    private String imageUrl;

    @Column(name = "uuid", unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();
}
