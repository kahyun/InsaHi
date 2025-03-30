package com.playdata.HumanResourceManagement.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id", unique = true)
    private Long authorityId;

    @Column(length = 50, nullable = false)
    private String authorityName;

    public Authority(String role) {
    }

    public Object getRole() {
        return this.authorityName;
    }
}
