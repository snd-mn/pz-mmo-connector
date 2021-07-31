package org.projectzion.game.mmoconnector.tos.itemtransfer;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemAmount implements Serializable {
    Integer item;
    Integer amount;
}
