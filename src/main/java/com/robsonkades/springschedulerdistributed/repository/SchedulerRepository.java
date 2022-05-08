package com.robsonkades.springschedulerdistributed.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import java.util.Optional;

import org.hibernate.LockOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import com.robsonkades.springschedulerdistributed.entity.Scheduler;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

    @QueryHints({
            @QueryHint(
                    name = "javax.persistence.lock.timeout",
                    value = "-2"  //LockOptions.SKIP_LOCKED
            )
    })
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Scheduler> findFirst1ByNameOrderByPageDesc(String name);
}
