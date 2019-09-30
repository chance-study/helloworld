package org.chance.distributelock.mongo.model;

/**
 * LockDocument
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/27
 */
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Make sure you create a TTL index in your {@link org.chance.distributelock.api.annotation.Locked#storeId()} collection on {@code expireAt} field to enable lock expiration.
 * You might also want to index the {@code token} field for better search performance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockDocument {

    @Id
    private String id;
    private LocalDateTime expireAt;
    private String token;

}
