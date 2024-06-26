package com.godana.service.user;


import com.godana.domain.dto.report.I6MonthAgoReportDTO;
import com.godana.domain.dto.report.IReportDTO;
import com.godana.domain.dto.report.IYearReportDTO;
import com.godana.domain.dto.user.UserCountDTO;
import com.godana.domain.dto.user.UserRegisterReqDTO;
import com.godana.domain.dto.user.UserReqUpDTO;
import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.entity.User;
import com.godana.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGeneralService<User, Long>, UserDetailsService {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    User getByUsername(String username);

    Optional<User> findByName(String userName);

    User create(UserRegisterReqDTO userRegisterReqDTO);

    User update(Long userId ,UserReqUpDTO userReqUpDTO);

    Page<UserResDTO> findUserBySearch(@Param("search") String search, Pageable pageable);

    Page<UserResDTO> findUserBanBySearch(@Param("search") String search, Pageable pageable);

    UserCountDTO countUser();

    void unban(User user);

    IReportDTO getUserReportOfCurrentDay();

    IReportDTO getUserReportOfCurrentMonth();

    List<IYearReportDTO> getUserReportByCurrentYear();

    List<I6MonthAgoReportDTO> getUserReport6Months();
}
