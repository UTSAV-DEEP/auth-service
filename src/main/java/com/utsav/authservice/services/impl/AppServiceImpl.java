package com.utsav.authservice.services.impl;

import com.utsav.authservice.common.Utils;
import com.utsav.authservice.exceptions.HttpErrorException;
import com.utsav.authservice.model.dtos.AppDto;
import com.utsav.authservice.model.entities.App;
import com.utsav.authservice.repositories.AppRepository;
import com.utsav.authservice.services.AppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    public AppServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public AppDto registerApp(AppDto appDto) {
        App appEntity = App.from(appDto);
        appEntity = appRepository.save(appEntity);
        return AppDto.from(appEntity);
    }

    @Override
    public List<AppDto> getAllApps() {
        return appRepository.findAll().stream().map(AppDto::from).collect(Collectors.toList());
    }

    @Override
    public AppDto getAppDetails(long id) {
        App appEntity = appRepository.getOne(id);
        if (null == appEntity || appEntity.isDeleted()) {
            throw new HttpErrorException(String.format("App with id: '%d' not found", id), HttpStatus.NOT_FOUND);
        }
        return AppDto.from(appEntity);
    }

    @Override
    public AppDto updateApp(AppDto appDto) {
        App appEntity = appRepository.getOne(Utils.getOrDefault(appDto.getId(), -1L));
        if (null == appEntity || appEntity.isDeleted()) {
            throw new HttpErrorException(String.format("App with id: '%d' not found", appDto.getId()), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isNotBlank(appDto.getName())) {
            appEntity.setName(appDto.getName());
        }
        if (StringUtils.isNotBlank(appDto.getDescription())) {
            appEntity.setDescription(appDto.getDescription());
        }
        if (StringUtils.isNotBlank(appDto.getUrl())) {
            appEntity.setUrl(appDto.getUrl());
        }
        appEntity = appRepository.save(appEntity);
        return AppDto.from(appEntity);
    }

    @Override
    public void deleteApp(long id, boolean permanent) {
        App appEntity = appRepository.getOne(id);
        if (null == appEntity || appEntity.isDeleted()) {
            throw new HttpErrorException(String.format("App with id: '%d' not found", id), HttpStatus.NOT_FOUND);
        }
        if (permanent) {
            appRepository.delete(appEntity);
        } else {
            appEntity.setDeleted(true);
            appRepository.save(appEntity);
        }
    }
}
