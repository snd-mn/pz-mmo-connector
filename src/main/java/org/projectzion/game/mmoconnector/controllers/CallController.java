package org.projectzion.game.mmoconnector.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.*;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.projectzion.game.mmoconnector.scoped.request.RequestScoped;
import org.projectzion.game.mmoconnector.services.CallService;
import org.projectzion.game.mmoconnector.services.TargetSystemService;
import org.projectzion.game.mmoconnector.services.UserService;
import org.projectzion.game.mmoconnector.tos.itemtransfer.SendItemsToTargetSystemRequest;
import org.projectzion.game.mmoconnector.tos.pick.PickRequest;
import org.projectzion.game.mmoconnector.tos.pick.PickResponse;
import org.projectzion.game.mmoconnector.utils.calls.JsonUtils;
import org.projectzion.game.mmoconnector.utils.calls.pick.Pick;
import org.projectzion.game.mmoconnector.utils.calls.sendItems.SendItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CallController {

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

    @Autowired
    RequestScoped requestScoped;

    @PostMapping("SendItems")
    public ResponseEntity<String> sendItems (@RequestBody SendItemsToTargetSystemRequest request) throws IOException {
        ResponseEntity<String> ret;

        User user = requestScoped.currentUserPrincipal().getUser();
        Call call = callService.getCallByCallIdentifierAndTargetSystem(CallIdentifier.SEND_ITEMS, request.getTargetSystem());

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

    @SneakyThrows
    @PostMapping("pick")
    public ResponseEntity<PickResponse> pick (@RequestBody PickRequest request) throws IOException {
        PickResponse retBody = new PickResponse();

        User user = requestScoped.currentUserPrincipal().getUser();
        Call call = callService.getCallByCallIdentifierAndTargetSystem(CallIdentifier.PICK, request.getTargetSystem());

        UserCall userCall = new UserCall();
        userCall.setUser(user);
        userCall.setCall(call);
        userCall.setState(CallState.READY);

        Pick pick = (Pick) applicationContext.getBean(Class.forName(call.getBean()));
        pick.setNodeType(request.getNodeType());

        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        String json = objectMapper.writeValueAsString(pick);
        userCall.setSerializedBean(json);
        userCallRepository.save(userCall);

        retBody.setInfo("pick will be executed soon");
        return new ResponseEntity<PickResponse>(retBody, HttpStatus.OK);
    }

}
