package com.utsav.authservice.services;

import com.utsav.authservice.model.dtos.AppDto;

import java.util.List;

public interface AppService {

    /**
     *
     * @param appDto
     * @return
     */
    AppDto registerApp(AppDto appDto);

    /**
     *
     * @return
     */
    List<AppDto> getAllApps();

    /**
     *
     * @param id
     * @return
     */
    AppDto getAppDetails(long id);

    /**
     *
     * @param appDto
     * @return
     */
    AppDto updateApp(AppDto appDto);

    /**
     *
     * @param id
     * @param permanent
     */
    void deleteApp(long id, boolean permanent);

}
