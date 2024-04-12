package com.example.social_network.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInfoListPostReqDto;
import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
import com.example.social_network.service.UserService;
import com.example.social_network.utils.CommonConstants;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private UserInfoResponseUtils userInfoResponseUtils;

	@Autowired
	private ImageService imageService;

	@Autowired
	private FileService fileService;

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
			user.setDob(LocalDate.parse(dob));

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
	public Page<UserInforResDto> searchUserByName(UserInfoListPostReqDto reqDto) {
		Pageable paging = PageRequest.of(reqDto.getPageNo(), reqDto.getPageSize());
		Page<UserInfo> pagedResult = userInfoRepository.findAllByFirstNameContainsOrLastNameContains(reqDto.getName(),
				reqDto.getName(), paging);
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
		return userInfoRepository.findOneById(id);
	}

	@Override
	public UserInforResDto findDetailUser(Long id) {
		return userInfoResponseUtils.convert(userInfoRepository.findOneById(id));
	}

}
