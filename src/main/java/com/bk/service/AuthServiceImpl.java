package com.bk.service;

import com.bk.dto.LoginDto;
import com.bk.dto.TookStats;
import com.bk.repo.AuthLogRepository;
import com.bk.repo.MetricRepository;
import com.bk.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${system.config.mail.admin}")
    private String mailAdmin;
    @Value("${system.config.mail.user.enable}")
    private String enableSendMailUser;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthLogRepository authLogRepository;
    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private ExternalApiService externalApiService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(LoginDto dto) {
        var userEntity = userRepository.findByUsername(dto.getUsername().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("username not found"));
        if (userEntity.getPassword().equals(dto.getPassword())) {
            return "succses";
        }
        return "fail";
    }

    @Override
    public Map<String, Object> report(String model, int type) {
        Map<String, Object> result = new HashMap<>();

        long correct = metricRepository.countCorrectPrediction(model, type);
        long wrong = metricRepository.countWrongPrediction(model, type);
        long fn = metricRepository.countFalseNegative(model, type);
        long fp = metricRepository.countFalsePositive(model, type);

        result.put("model", model);
        result.put("type", type);
        result.put("correct", correct);
        result.put("wrong", wrong);
        result.put("falseNegative", fn);
        result.put("falsePositive", fp);
        result.put("took_type_" + type, buildTookMap(metricRepository.getTookStatistics(model, type)));
        return result;
    }

    private Map<String, Object> buildTookMap(TookStats stats) {
        Map<String, Object> map = new HashMap<>();
        if (stats != null) {
            map.put("max", stats.getMaxTook());
            map.put("min", stats.getMinTook());
            map.put("avg", stats.getAvgTook());
        }
        return map;
    }
}
