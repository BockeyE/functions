package protem;

/**
 * @author bockey
 */
public class AssetVO {
    String buyer;
    String seller;
    String amount;
    String billNumber;
    String billExpireDate;
    String invoiceNumber;
    String invoiceAmount;
    String contractNumber;
    String contractFileName;
    String assetFileName;
    int assetStatus;
    int attachStatus;
    int invoiceStatus;
    int validity;
    int ZDWSeachStatus;
    int ZDWRegistStatus;

    AssetAttach invoice;
    AssetAttach contract;
    AssetAttach perfomFile;
    AssetAttach assetFile;
}
