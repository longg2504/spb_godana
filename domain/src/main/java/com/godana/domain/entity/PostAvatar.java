package com.godana.domain.entity;
//
//import com.godana.domain.dto.avatar.AvatarDTO;
//import com.godana.domain.dto.avatar.AvatarResDTO;
import com.godana.domain.dto.postAvatar.PostAvatarReqDTO;
import com.godana.domain.dto.postAvatar.PostAvatarResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="post_avatar")
@Accessors(chain = true)
public class PostAvatar extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "cloud_id")
    private String cloudId;

    private  Integer width;

    private Integer height;

    @ManyToOne
    @JoinColumn(name="post_id" , referencedColumnName = "id" )
    private Post post;


    public PostAvatarReqDTO toAvatarDTO() {
        return new PostAvatarReqDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCloudId(cloudId)
                .setWidth(width)
                .setHeight(height);
    }


    public PostAvatarResDTO toAvatarResDTO() {
        return new PostAvatarResDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCloudId(cloudId)
                .setWidth(width)
                .setHeight(height)
                ;
    }




}
