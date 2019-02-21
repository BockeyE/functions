package protem;

import java.util.Date;

/**
 * @author bockey
 */
public class AssetValidity {
    int requirement;
    String assetId;
    int perfomFile;//0，无需；1，已上传；-1，未上传
    int assetFile;
    int invoiceCheck;
    int ZDWsearch;
    int ZDWregist;
    Date invoiceCheckDate;
    Date ZDWsearchDate;
    Date ZDWregistDate;

}
