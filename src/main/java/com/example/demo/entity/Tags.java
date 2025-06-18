// Tags.java
package com.example.demo.entity; // 실제 패키지명에 맞게 수정하세요.

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags") // 실제 테이블 이름이 'tags'인지 확인
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 만약 'tags' 테이블의 PK 컬럼 이름이 'id'라면,
    // 이 필드 위에는 @Column 어노테이션을 추가할 필요가 없습니다.
    private Integer id;

    private String name;

    // 생성자, Getter, Setter (이전과 동일)
    public Tags() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}