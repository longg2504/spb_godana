package com.godana.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "reply_to_comment")
@Accessors(chain = true)

public class Reply extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "comment_id" , referencedColumnName = "id" , nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id" , nullable = false)
    private User user;


}