package com.bk.service;

import com.bk.dto.AuthLog;
import com.bk.entity.AuthLogEntity;
import com.bk.repo.AuthLogRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    @Autowired
    private AuthLogRepository authLogRepository;

    @Override
    public void uploadCsv(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);

            CsvToBean<AuthLog> csvToBean = new CsvToBeanBuilder<AuthLog>(reader)
                    .withType(AuthLog.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<AuthLog> logs = csvToBean.parse();
            ModelMapper mapper = new ModelMapper();
            List<AuthLogEntity> authLogEntities = new ArrayList<>();
            for (AuthLog authLog : logs) {
                AuthLogEntity entity = mapper.map(authLog, AuthLogEntity.class);
                entity.setId(null);
                authLogEntities.add(entity);
            }
            authLogRepository.saveAllAndFlush(authLogEntities);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
