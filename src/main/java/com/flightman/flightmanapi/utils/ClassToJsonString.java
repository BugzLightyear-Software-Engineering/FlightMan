package com.flightman.flightmanapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClassToJsonString {
        Object obj;

        public ClassToJsonString(Object obj) {
                this.obj = obj;
        }

        public String getJsonString() throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                DefaultPrettyPrinter printer = new DefaultPrettyPrinter()
                                .withObjectIndenter(new DefaultIndenter("    ", "\n"));
                return mapper.writer(printer).writeValueAsString(this.obj);
        }
}
