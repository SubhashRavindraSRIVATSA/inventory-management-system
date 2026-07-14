package com.subhash.ims.entity;

import com.subhash.ims.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RoleType name;

    @Column(length = 200)
    private String description;
}
