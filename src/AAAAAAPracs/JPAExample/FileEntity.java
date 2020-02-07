//package AAAAAAPracs.JPAExample;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
///**
// * @author bockey
// */
//@Entity
//@Data
//@Table(indexes = {@Index(columnList = "fileHash")})
//public class FileEntity {
//
//    @Id
//    @GeneratedValue
//    Long id;
//    String fileHash;
//    String fileName;
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(columnDefinition = "mediumBLOB")
//    byte[] fileBytes;
//
//}
