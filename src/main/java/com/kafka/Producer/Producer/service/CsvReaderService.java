package com.kafka.Producer.Producer.service;

import com.kafka.Producer.Producer.entity.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CsvReaderService {
    public List<User> readUsersFromCsv(MultipartFile file) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            final List<User> userList = csvToBean.parse();
            sendMessageToTopic(userList);
            return userList;
        }
    }
    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendMessageToTopic( List<User> userList)
    {
        final CompletableFuture<SendResult<String, Object>> future = template.send("add_users", userList);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message "  +result.getRecordMetadata().topic()+ " to partition " + result.getRecordMetadata().partition() + " with offset " + result.getRecordMetadata().offset());
            } else {
                System.out.println("Unable to send message '" + userList + "': " + ex.getMessage());
            }
        });
    }
}
