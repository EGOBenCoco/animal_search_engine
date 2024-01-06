package com.example.animal_search_engine.model;

import com.example.animal_search_engine.enums.EnumRole;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CollectionId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(indexes = {@Index(name = "idx_email",  columnList="email", unique = true)})
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank(message = "Firstname is required")
    @Column(name = "first_name")
    String firstName;

    @NotBlank(message = "Lastname is required")
    @Column(name = "last_name")
    String lastName;

    @NotNull(message = "Email is required")
    @NotEmpty
    @Email
    @Pattern(regexp=".+@.+\\..+")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String email;

    @NotNull(message = "Password is required")
    @NotEmpty
    @Size(min = 8, max = 64,message = "The size should vary from 8 to 64")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "consumer",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<ContactInfo> contactInfos;


    @JsonIgnore
    @OneToMany(mappedBy = "consumer",cascade = {CascadeType.REMOVE})
    List<Announcement> announcements;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    @JoinTable(
            name = "consumers_roles",
            joinColumns = @JoinColumn(name = "consumer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean enabled;
    public Consumer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
