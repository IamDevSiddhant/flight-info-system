//package io.flightinfo.utils;
//
//
//import io.flightinfo.entity.UserDetailsModel;
//import lombok.NoArgsConstructor;
//import lombok.experimental.UtilityClass;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Component
//@Slf4j
//public class GetMemberDetailsUtils {
//
//
//    @Autowired
//    @Qualifier("restTemplate")
//    private RestTemplate restTemplate;
//
//    public GetMemberDetailsUtils(){}
//
//   public UserDetailsModel getUserDetailsByName(String userName){
//
//        String url="http://localhost:8081/api/v1/interactions/flightUsers/username/{userName}";
//        Map<String, String> uriVariables = new HashMap<>();
//        uriVariables.put("userName",userName);
//        HttpHeaders headers=new HttpHeaders();
//        headers.add("content", MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<UserDetailsModel> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, UserDetailsModel.class, uriVariables);
//
//        return exchange.getBody();
//
//    }
//
//}
