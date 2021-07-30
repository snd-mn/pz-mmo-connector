package org.projectzion.game.mmoconnector.controllers;

import org.projectzion.game.mmoconnector.tos.SendItems2TrinityRequest;
import org.projectzion.game.mmoconnector.tos.SendItems2TrinityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TrinityCoreController {

    protected HttpHeaders getHttpHeaders(){
        HttpHeaders header = new HttpHeaders();
        Charset utf8 = Charset.forName("UTF-8");
        List<Charset> sets = new ArrayList<>();
        sets.add(utf8);
        header.setAcceptCharset(sets);
        header.setContentType(MediaType.APPLICATION_XML);
        return header;
    }

    protected RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("zipmo","zipmo").build();
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    protected String getCommandPreset(String command){
        String placeHolder = "%COMMAND%";
        String rausMitDerScheisse =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:TC\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        "    <SOAP-ENV:Body>\n" +
                        "        <ns1:executeCommand>\n" +
                        "            %COMMAND%\n" +
                        "        </ns1:executeCommand>\n" +
                        "    </SOAP-ENV:Body>\n" +
                        "</SOAP-ENV:Envelope>";
        return rausMitDerScheisse.replaceAll(placeHolder, command);
    }

    @PostMapping("SendItems2Trinity")
    public SendItems2TrinityResponse setItems (@RequestBody SendItems2TrinityRequest request){
        SendItems2TrinityResponse ret = new SendItems2TrinityResponse();

        StringBuffer commandStr = new StringBuffer();
        commandStr.append(".send items ");
        commandStr.append(request.getCharName());
        commandStr.append(request.getMailSubject());
        commandStr.append(request.getMailText());
        request.getItemAmountList().forEach((itemAmount) -> {
            commandStr.append(itemAmount.getItem());
            commandStr.append("[:");
            commandStr.append(itemAmount.getAmount());
            commandStr.append("] ");
        });

        String body = getCommandPreset(commandStr.toString());
        HttpEntity entity = new HttpEntity<>(body, getHttpHeaders());

        ResponseEntity<String> response = getRestTemplate().exchange("http://127.0.0.1:7878", HttpMethod.POST, entity, String.class);

        ret.setTcsResponse(response.getBody());

        return ret;
    }

}