package org.projectzion.game.mmoconnector.tos;

import lombok.Data;

import java.util.List;

@Data
public class SendItems2TrinityRequest extends BaseInTo{
    String charName;
    String mailSubject;
    String mailText;
    List<ItemAmount> itemAmountList;
}
