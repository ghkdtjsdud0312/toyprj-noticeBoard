package toyprj.toyprj_noticeBoard.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
// 시간 정보를 알기 위한 entity
public class BaseEntity{

    @CreationTimestamp // CreationTimestamp가 생성 되었을 때 시간을 만들어 주는 것
    @Column(updatable = false) // 수정 시에는 관여 안 하게 하기 위해서 사용
    private LocalDateTime createdTime;

    @UpdateTimestamp // 수정이 일어 났을 때
    @Column(insertable = false) // 입력 시 인서트를 할 때는 관여 안 하게 하기 위해 사용
    private LocalDateTime updatedTime;

}
