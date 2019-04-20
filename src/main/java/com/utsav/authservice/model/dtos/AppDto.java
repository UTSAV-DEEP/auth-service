package com.utsav.authservice.model.dtos;


import com.utsav.authservice.common.Utils;
import com.utsav.authservice.model.entities.App;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppDto implements Serializable {

    private Long id;

    private String name;

    private String displayName;

    private String token;

    private String description;

    private String url;

    private long createdAt;

    private long updatedAt;

    public static AppDto from(App appEntity) {
        AppDto appDto = Utils.MODEL_MAPPER.map(appEntity, AppDto.class);
        appDto.setCreatedAt(appEntity.getCreatedAt().getTime());
        appDto.setUpdatedAt(appEntity.getUpdatedAt().getTime());
        return appDto;
    }

}
