/*
 * Copyright (c) 2020, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

module io.ballerina.ballerina.openapi {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires handlebars;
    requires info.picocli;
    requires io.ballerina.lang;
    requires io.ballerina.parser;
    requires io.ballerina.stdlib.http;
    requires io.ballerina.cli;
    requires io.ballerina.tools.api;
    requires io.swagger.v3.core;
    requires io.swagger.v3.oas.models;
    requires java.ws.rs;
    requires jsr305;
    requires org.apache.commons.io;
    requires org.slf4j;
    requires swagger.core;
    requires swagger.models;
    requires swagger.parser;
    requires swagger.parser.v2.converter;
    requires swagger.parser.v3;
    exports org.ballerinalang.ballerina;
    exports org.ballerinalang.openapi;
    exports org.ballerinalang.openapi.cmd;
    exports org.ballerinalang.openapi.exception;
    exports org.ballerinalang.openapi.model;
    exports org.ballerinalang.openapi.typemodel;
    exports org.ballerinalang.openapi.utils;
}

