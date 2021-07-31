package org.projectzion.game.mmoconnector.utils.calls.sendItems;

import lombok.Data;
import org.projectzion.game.mmoconnector.tos.itemtransfer.ItemAmount;
import org.projectzion.game.mmoconnector.utils.calls.ICall;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public abstract class SendItem implements ICall, Serializable {
    List<ItemAmount> itemAmountList;
    String mailSubject;
    String mailText;
}
