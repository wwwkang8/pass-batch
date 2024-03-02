package com.marathon.passbatch.job.pass;

import java.time.LocalDateTime;
import java.util.List;

import com.marathon.passbatch.repository.pass.BulkPassEntity;
import com.marathon.passbatch.repository.pass.BulkPassRepository;
import com.marathon.passbatch.repository.pass.BulkPassStatus;
import com.marathon.passbatch.repository.pass.PassEntity;
import com.marathon.passbatch.repository.pass.PassRepository;
import com.marathon.passbatch.repository.user.UserGroupMappingEntity;
import com.marathon.passbatch.repository.user.UserGroupMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddPassesTasklet_T implements Tasklet {

    PassRepository passRepository;

    BulkPassRepository bulkPassRepository;

    UserGroupMappingRepository userGroupMappingRepository;

    public AddPassesTasklet_T(PassRepository passRepository, BulkPassRepository bulkPassRepository,
                              UserGroupMappingRepository userGroupMappingRepository) {
        this.passRepository = passRepository;
        this.bulkPassRepository = bulkPassRepository;
        this.userGroupMappingRepository = userGroupMappingRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {

        LocalDateTime startedAt = LocalDateTime.now().minusDays(1);
        List<BulkPassEntity> bulkPassEntityList
                = bulkPassRepository.findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startedAt);

        for(BulkPassEntity bulkPassEntity : bulkPassEntityList) {



        }


        return null;
    }

//    private int addPasses(BulkPassEntity bulkPassEntity, List<String> userIds) {
//
//        List<PassEntity>
//
//    }
}
