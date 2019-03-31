package com.utsav.authservice.model.entities;

import com.utsav.authservice.common.Utils;
import com.utsav.authservice.model.dtos.AppDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "apps")
@Getter
@Setter
@ToString
public class App implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private String url;

    private String token;

    private boolean deleted;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date deletedAt;

    public static App from(AppDto appDto) {
        App appEntity = Utils.MODEL_MAPPER.map(appDto, App.class);
        appEntity.setToken(UUID.randomUUID().toString());
        return appEntity;
    }
}
