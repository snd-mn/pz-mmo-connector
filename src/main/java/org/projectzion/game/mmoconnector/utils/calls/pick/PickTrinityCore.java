package org.projectzion.game.mmoconnector.utils.calls.pick;

import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.ItemRepository;
import org.projectzion.game.mmoconnector.persistence.repositories.NodeTypeItemRepository;
import org.projectzion.game.mmoconnector.services.CharacterService;
import org.projectzion.game.mmoconnector.utils.calls.sendItems.SendItemTrinityCore;
import org.projectzion.game.mmoconnector.utils.setup.SetupDevelopmentEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PickTrinityCore extends Pick implements Serializable {
    Logger logger = LoggerFactory.getLogger(PickTrinityCore.class);

    @Autowired
    CharacterService characterService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    NodeTypeItemRepository nodeTypeItemRepository;

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

    @Override
    public CallState executeCall(UserCall userCall) throws Exception {
        TargetSystem targetSystem = userCall.getCall().getTargetSystem();
        StringBuffer commandStr = new StringBuffer();
        commandStr.append(".send items ");
        commandStr.append(characterService.getCharacterForItemTransfer(userCall.getUser()));
        commandStr.append("send mapped item amounts");
        commandStr.append("thank you!");

        List<NodeTypeItem> ntis = nodeTypeItemRepository.getNodeTypeItemsByTargetSystemAndNodeType(nodeType,userCall.getCall().getTargetSystem().getId());
        ntis.forEach(nti -> {
            commandStr.append(nti.getItem().getTargetSystemItemId());
            commandStr.append("[:");
            commandStr.append(nti.getAmount().intValue());
            commandStr.append("] ");
        });

        RestTemplate restTemplate = getRestTemplate(targetSystem);
        String body = getCommandPreset(commandStr.toString());

        try{
            HttpEntity entity = new HttpEntity<>(body, getHttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(getUrl(targetSystem), HttpMethod.POST, entity, String.class);
            if(!response.getStatusCode().equals(HttpStatus.OK)){
                throw new Exception("failed: response http state: " + response.getStatusCode() + "\r\n answer: " + response.getBody());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return CallState.FAILED;
        }

        return CallState.DONE;
    }
}
