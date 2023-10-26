package com.example.team1_be.domain.Schedule.Repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

class ScheduleRepositoryTest extends BaseTest {

	public ScheduleRepositoryTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository,
		WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository,
		SubstituteRepository substituteRepository, EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, scheduleRepository, substituteRepository, em);
	}

	@DisplayName("스케줄 조회")
	@Test
	void test1() {
		assertThat(scheduleRepository.findById(1L).orElse(null))
			.isNotEqualTo(null);
	}

	@DisplayName("최근 작성했던 스케줄 조회")
	@Test
	void test2() {
		Schedule schedule = scheduleRepository.findById(1L).orElse(null);
		Week lastestWeek = weekRepository.findLatestByScheduleAndStatus(schedule.getId(),
			WeekRecruitmentStatus.ENDED,
			PageRequest.of(0, 1)).getContent().get(0);
		assertThat(lastestWeek).isNotEqualTo(null);
		lastestWeek.getDay().get(0)
			.getWorktimes()
			.stream()
			.forEach(x -> System.out.println(x.getTitle()));

	}

	@DisplayName("주별 상태 조회")
	@Test
	void test3() {
		LocalDate startDate = LocalDate.parse("2023-10-09");
		Schedule schedule = scheduleRepository.findById(1L).orElse(null);
		Week week = weekRepository.findByScheduleIdAndStartDate(schedule.getId(), startDate).orElse(null);
		assertThat(week).isNotEqualTo(null);
		System.out.println(week.getStatus());
	}
}