package org.projectzion.game.mmoconnector.utils.calls.sendItems;

import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class SendItemTrinityCore extends SendItem implements Serializable {

    @Autowired
    CharacterService characterService;

    protected HttpHeaders getHttpHeaders(){
        HttpHeaders header = new HttpHeaders();
        Charset utf8 = Charset.forName("UTF-8");
        List<Charset> sets = new ArrayList<>();
        sets.add(utf8);
        header.setAcceptCharset(sets);
        header.setContentType(MediaType.APPLICATION_XML);
        return header;
    }

    protected RestTemplate getRestTemplate(TargetSystem targetSystem){
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication(targetSystem.getConnectionUser().getEmail(),targetSystem.getConnectionUser().getPassword()).build();
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

    private String getUrl(TargetSystem targetSystem) {
        return "http://" + targetSystem.getIp() + ":" + targetSystem.getPort();
    }

    @Bean(name = "SendItemsTrinityCore")
    public static SendItemTrinityCore getSendItemTrinityCore(){
        return new SendItemTrinityCore();
    }

    @Override
    public CallState executeCall(UserCall userCall) throws Exception{
        TargetSystem targetSystem = userCall.getCall().getTargetSystem();
        RestTemplate restTemplate = getRestTemplate(targetSystem);
        StringBuffer commandStr = new StringBuffer();
        commandStr.append(".send items ");
        commandStr.append(characterService.getCharacterForItemTransfer(userCall.getUser()));
        commandStr.append(getMailSubject());
        commandStr.append(getMailText());

        //TODO mapping table for pz-game-item -> targetsystem-item and amount multiplier
        itemAmountList.forEach((itemAmount) -> {
            commandStr.append(itemAmount.getItem());
            commandStr.append("[:");
            commandStr.append(itemAmount.getAmount());
            commandStr.append("] ");
        });

        String body = getCommandPreset(commandStr.toString());

        try{
            HttpEntity entity = new HttpEntity<>(body, getHttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(getUrl(targetSystem), HttpMethod.POST, entity, String.class);
            if(!response.getStatusCode().equals(HttpStatus.OK)){
                throw new Exception("failed: response http state: " + response.getStatusCode() + "\r\n answer: " + response.getBody());
            }
        }catch(Exception e){
            //TODO logging
            System.out.println(e.getMessage());
            return CallState.FAILED;
        }

        return CallState.DONE;
    }


}
