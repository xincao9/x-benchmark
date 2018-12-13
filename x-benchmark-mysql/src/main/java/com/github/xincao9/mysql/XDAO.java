/*
 * Copyright 2018 Pivotal Software, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author xincao9@gmail.com
 */
public class XDAO {

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai", "root", "asdf");
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("create database if not exists db");
                statement.executeUpdate("use db");
                statement.executeUpdate("create table if not exists x (id bigint not null, value char(130), primary key (id)) engine=innodb default charset=utf8");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String get(Long id) throws Exception {
        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(String.format("select value from x where id=%d", id))) {
            while (resultSet.next()) {
                return resultSet.getString("value");
            }
        }
        return "";
    }

    public static Integer add(Long id, String value) throws Exception {
        try (Statement statement = conn.createStatement()) {
            return statement.executeUpdate(String.format("replace into x (id, value) values (%d, %s) ", id, value));
        }
    }

}
