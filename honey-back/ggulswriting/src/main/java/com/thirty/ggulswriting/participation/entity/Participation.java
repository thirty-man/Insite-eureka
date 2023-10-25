package com.thirty.ggulswriting.participation.entity;

import com.thirty.ggulswriting.global.entity.BaseEntity;
import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.room.entity.Room;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Participation")
public class Participation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int participationId;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Column(nullable = false)
	private Boolean isOut;

	public static Participation of(Member member, Room room, Boolean isOut) {
		return Participation.builder()
			.member(member)
			.room(room)
			.isOut(isOut)
			.build();
	}

	public void out(){
		this.isOut = true;
	}

	public void reParticipate(){ this.isOut = false; }
}
