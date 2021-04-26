package com.justanalytics.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DataRepository {

    Logger logger = LoggerFactory.getLogger(DataRepository.class);

    @Autowired
    @Qualifier("synapseJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getData(String query) {
        return jdbcTemplate.queryForList(query);
    }

}
