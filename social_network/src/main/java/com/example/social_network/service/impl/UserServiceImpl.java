package com.example.social_network.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
import com.example.social_network.service.UserService;
import com.example.social_network.utils.CommonConstants;
import com.example.social_network.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserInfoRepository userInfoRepository;

	private final UserInfoResponseUtils userInfoResponseUtils;

	private final ImageService imageService;

	private final FileService fileService;

	@Override
	public UserInforResDto updateInfo(UserInfoPutReqDto reqDto, MultipartFile[] multipartFile) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		String lastName = reqDto.getLastName();
		String firstName = reqDto.getFirstName();
		String dob = reqDto.getBirthDay();
		String bio = reqDto.getIntroyourself();
		String gender = reqDto.getGender();

		if (lastName != null)
			user.setLastName(lastName);

		if (firstName != null)
			user.setFirstName(firstName);

		if (dob != null)
			user.setDob(Utils.convertStringToLocalDate(dob));

		if (bio != null)
			user.setIntroyourself(bio);

		if (gender != null) {
			if (gender.equals("1"))
				user.setGender(true);
			else if (gender.equals("0"))
				user.setGender(false);
		}

		if (multipartFile != null) {
			ImageResDto imageResDto = fileService.uploadImage(multipartFile).get(0);
			Image image = imageService.findOneById(imageResDto.getId());
			image.setUserInfo(user);
			imageService.save(image);
			user.getAvatarImage().add(image);
		}

		userInfoRepository.save(user);
		return userInfoResponseUtils.convert(user);
	}

	@Override
	public UserInforResDto getUserInformation() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfoEntity = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		return userInfoResponseUtils.convert(userInfoEntity);
	}

	@Override
	public Page<UserInforResDto> searchUserByName(Integer pageNo, String content) {
		Pageable paging = PageRequest.of(pageNo, 10);
		Page<UserInfo> pagedResult;
		if (StringUtils.isBlank(content)) {
			pagedResult = userInfoRepository.findAll(paging);
		} else {
			pagedResult = userInfoRepository.findAllByFirstNameContainsOrLastNameContains(content, content, paging);
		}
		List<UserInforResDto> userInfoResponseList = pagedResult.stream().map(userInfoResponseUtils::convert)
				.collect(Collectors.toList());
		return new PageImpl<>(userInfoResponseList, paging, userInfoResponseList.size());
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoRepository.save(userInfo);
	}

	@Override
	public UserInfo findOneById(Long id) {
		UserInfo user = userInfoRepository.findOneById(id);
		if (user == null) {
			throw new  AppException(ErrorCode.USER_NOT_EXISTED);
		}
		return userInfoRepository.findOneById(id);
	}

	@Override
	public UserInforResDto findDetailUser(Long id) {
		return userInfoResponseUtils.convert(userInfoRepository.findOneById(id));
	}

}
