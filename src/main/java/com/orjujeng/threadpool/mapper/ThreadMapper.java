package com.orjujeng.threadpool.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ThreadMapper {
    @Insert("INSERT INTO threadlog(`function`,insert_date) VALUES(#{function}, CURRENT_TIMESTAMP(6))")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertThreadLog(String function);
}
