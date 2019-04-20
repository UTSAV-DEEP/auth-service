package com.utsav.authservice.model.entities;

import com.utsav.authservice.common.Utils;
import com.utsav.authservice.model.dtos.AppDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "apps")
@Getter
@Setter
@ToString
public class App extends AbstractEntity {

    private String name;

    private String displayName;

    private String description;

    private String url;

    private String token;

    public static App from(AppDto appDto) {
        App appEntity = Utils.MODEL_MAPPER.map(appDto, App.class);
        appEntity.setToken(UUID.randomUUID().toString());
        return appEntity;
    }
}
