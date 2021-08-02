package org.projectzion.game.mmoconnector.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.*;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.projectzion.game.mmoconnector.persistence.repositories.UserRepository;
import org.projectzion.game.mmoconnector.services.CallService;
import org.projectzion.game.mmoconnector.services.TargetSystemService;
import org.projectzion.game.mmoconnector.services.UserService;
import org.projectzion.game.mmoconnector.tos.itemtransfer.SendItemsToTargetSystemRequest;
import org.projectzion.game.mmoconnector.tos.itemtransfer.SendItemsToTargetSystemResponse;
import org.projectzion.game.mmoconnector.tos.pick.PickRequest;
import org.projectzion.game.mmoconnector.tos.pick.PickResponse;
import org.projectzion.game.mmoconnector.utils.calls.ICall;
import org.projectzion.game.mmoconnector.utils.calls.JsonUtils;
import org.projectzion.game.mmoconnector.utils.calls.pick.Pick;
import org.projectzion.game.mmoconnector.utils.calls.sendItems.SendItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TrinityCoreController {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @Autowired
    TargetSystemService targetSystemService;

    @Autowired
    CallService callService;

    @Autowired
    UserCallRepository userCallRepository;



    @PostMapping("SendItems")
    public ResponseEntity<String> sendItems (@RequestBody SendItemsToTargetSystemRequest request) throws IOException {
        ResponseEntity<String> ret;

        User user = userService.getUserById(request.getUserId());
        Call call = callService.getCallByCallIdentifierAndTargetSystem(CallIdentifier.SEND_ITEMS.value, request.getTargetSystem());

        SendItem sendItem = (SendItem) applicationContext.getBean(call.getBean());
        sendItem.setMailSubject(request.getMailSubject());
        sendItem.setMailText(request.getMailText());

        UserCall userCall = new UserCall();
        userCall.setUser(user);
        userCall.setState(CallState.READY);

        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        String json = objectMapper.writeValueAsString(sendItem);
        userCall.setSerializedBean(json);

        userCallRepository.save(userCall);

        ret = new ResponseEntity<String>("digga",HttpStatus.OK);

        return ret;
    }

    @PostMapping("pick")
    public ResponseEntity<PickResponse> pick (@RequestBody PickRequest request) throws IOException {
        PickResponse retBody = new PickResponse();

        User user = userService.getUserById(request.getUserId());
        Call call = callService.getCallByCallIdentifierAndTargetSystem(CallIdentifier.PICK.value, request.getTargetSystem());

        Pick pick = (Pick) applicationContext.getBean(call.getBean());
        pick.setNodeType(request.getNodeType());

        UserCall userCall = new UserCall();
        userCall.setUser(user);
        userCall.setState(CallState.READY);

        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        String json = objectMapper.writeValueAsString(pick);
        userCall.setSerializedBean(json);

        userCallRepository.save(userCall);

        return new ResponseEntity<PickResponse>(retBody, HttpStatus.OK);
    }

}
