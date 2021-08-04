package org.projectzion.game.mmoconnector.tos.itemtransfer;

import lombok.Data;
import org.projectzion.game.mmoconnector.tos.BaseInTo;

import java.util.List;

@Data
public class SendItemsToTargetSystemRequest extends BaseInTo {
    String charName;
    String mailSubject;
    String mailText;
    List<ItemAmount> itemAmountList;
}
