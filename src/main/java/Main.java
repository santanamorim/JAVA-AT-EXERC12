import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

public class Main {
    public static void main(String[] args) {
        port(4567);

        get("/hello", (req, res) -> "Hello, Spark!");

        post("/data", (req, res) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            MyData data = objectMapper.readValue(req.body(), MyData.class);
            res.type("application/json");
            return objectMapper.writeValueAsString(data);
        });
    }

    @Data
    static class MyData {
        private String name;
        private int age;
    }
}