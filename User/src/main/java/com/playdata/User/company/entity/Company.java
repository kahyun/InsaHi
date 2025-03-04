package com.playdata.User.company.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @Column(unique = true) // companyCode가 고유값이므로 추가x
    private String companyCode; //1

    private String companyName; //2
//    private String companyImage;
    private String companyAddress; //3
    private String headCount; //4
    private Date createdAt; // 설립일 입력 받아서 저장
    private String businessNumber;
    private Timestamp startTime;

//    public Company(String companyName, String companyCode, String headCount, String businessNumber) {
//        this.companyName = companyName;
//        this.companyCode = companyCode;
//        this.headCount = headCount;
//        this.businessNumber = businessNumber;
//    }


}
