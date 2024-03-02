package com.marathon.passbatch.job.pass;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.marathon.passbatch.repository.pass.BulkPassEntity;
import com.marathon.passbatch.repository.pass.BulkPassRepository;
import com.marathon.passbatch.repository.pass.BulkPassStatus;
import com.marathon.passbatch.repository.pass.PassEntity;
import com.marathon.passbatch.repository.pass.PassModelMapper;
import com.marathon.passbatch.repository.pass.PassRepository;
import com.marathon.passbatch.repository.user.UserGroupMappingEntity;
import com.marathon.passbatch.repository.user.UserGroupMappingRepository;
import com.marathon.passbatch.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddPassesTasklet implements Tasklet {

    private final PassRepository passRepository;

    private final BulkPassRepository bulkPassRepository;

    private final UserGroupMappingRepository userGroupMappingRepository;

    public AddPassesTasklet(PassRepository passRepository, BulkPassRepository bulkPassRepository,
                            UserGroupMappingRepository userGroupMappingRepository) {
        this.passRepository = passRepository;
        this.bulkPassRepository = bulkPassRepository;
        this.userGroupMappingRepository = userGroupMappingRepository;
    }


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {

        /**
         * 이용권 상태가 READY && 이용권 시작일 하루전 데이터들을 조회한다
         * 이유 : 정식 PassEntity로 등록하기 위한 작업.
         * */
        final LocalDateTime startedAt = LocalDateTime.now().minusDays(1);
        final List<BulkPassEntity> bulkPassEntityList
                = bulkPassRepository.findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startedAt);

        int count = 0;
        for(BulkPassEntity bulkPassEntity : bulkPassEntityList) {
            final List<String> userIds = userGroupMappingRepository.findByUserGroupId(bulkPassEntity.getUserGroupId())
                                            .stream().map(UserGroupMappingEntity::getUserId).toList();
            count += addPasses(bulkPassEntity, userIds);

            bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);
        }

        log.info("AddPassesTasklet - execute: 이용 {}건 추가완료, startedAt={}", count, startedAt);

        return RepeatStatus.FINISHED;
    }

    private int addPasses(BulkPassEntity bulkPassEntity, List<String> userIds) {

        List<PassEntity> passEntities = new ArrayList<>();

        for(String userId : userIds) {

            PassEntity passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userId);
            passEntities.add(passEntity);

        }

        return passRepository.saveAll(passEntities).size();
    }
}
