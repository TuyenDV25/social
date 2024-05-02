package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Friend;
import com.example.social_network.entity.FriendRequest;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.mapper.user.FriendUserInfo;
import com.example.social_network.repository.FriendRepository;
import com.example.social_network.repository.FriendRequestRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.UserService;
import com.example.social_network.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class FriendServiceImplTest {

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private FriendRequestRepository friendRequestRepository;

	@Mock
	private FriendRepository friendRepository;

	@Mock
	private UserService userService;

	@Mock
	private UserInfoResponseUtils userInfoResponseUtils;

	private FriendServiceImpl friendServiceImpl;

	@BeforeEach
	public void setUp() {
		friendServiceImpl = new FriendServiceImpl(userInfoRepository, friendRequestRepository, friendRepository,
				userService, userInfoResponseUtils);
	}

	@Test
	void AddFriendRequest_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);
		userTest2.setFirstName("Nguyen");
		userTest2.setDob(Utils.convertStringToLocalDate("19901112"));
		userTest2.setIntroyourself("thich code");
		userTest2.setGender(true);

		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		Integer result = friendServiceImpl.addFriendRequest(2L);
		assertEquals(2, result);
	}

	@Test
	void AddFriendRequest_BeFriend_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);
		userTest2.setFirstName("Nguyen");
		userTest2.setDob(Utils.convertStringToLocalDate("19901112"));
		userTest2.setIntroyourself("thich code");
		userTest2.setGender(true);

		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		List<FriendRequest> listFriend = new ArrayList<>();
		FriendRequest fri = new FriendRequest();
		fri.setUserInfo(userTest2);
		listFriend.add(fri);
		userTest.setListFriendRequest(listFriend);

		Integer result = friendServiceImpl.addFriendRequest(2L);
		assertEquals(1, result);
	}

	@Test
	void addFriendRequest_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(2L)).thenReturn(null);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.addFriendRequest(2L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("User not existed", exception.getMessage());
	}

	@Test
	void addFriendRequest_validRequest_selfAdd_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(1L);
		when(userInfoRepository.findOneById(1L)).thenReturn(userTest2);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.addFriendRequest(1L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("You can not add friend youself", exception.getMessage());
	}

	@Test
	void AddFriendRequest_alreadyFriend_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		List<Friend> listFriend = new ArrayList<>();
		Friend fri = new Friend();
		fri.setUserInfo(userTest2);
		listFriend.add(fri);
		userTest.setListFriend(listFriend);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		List<Friend> listFriend2 = new ArrayList<>();
		Friend fri2 = new Friend();
		fri2.setUserInfo(userTest);
		listFriend2.add(fri2);
		userTest2.setListFriend(listFriend2);
		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.addFriendRequest(2L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("can not add friend with your friend", exception.getMessage());
	}

	@Test
	void AddFriendRequest_again_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		List<FriendRequest> listFriend = new ArrayList<>();
		FriendRequest fri = new FriendRequest();
		fri.setUserInfo(userTest);
		listFriend.add(fri);
		userTest2.setListFriendRequest(listFriend);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.addFriendRequest(2L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("you have send request to your friend", exception.getMessage());
	}

	@Test
	void RemoveRequest_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(3L);

		UserInfo userTest3 = new UserInfo();
		userTest3.setLastName("Dev");
		userTest3.setId(2L);

		List<FriendRequest> listFriend = new ArrayList<>();
		FriendRequest fri = new FriendRequest();
		FriendRequest fri2 = new FriendRequest();
		fri2.setUserInfo(userTest3);
		fri.setUserInfo(userTest2);
		listFriend.add(fri);
		listFriend.add(fri2);
		userTest.setListFriendRequest(listFriend);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(userInfoRepository.findOneById(3L)).thenReturn(userTest2);

		friendServiceImpl.removeFriendRequest(3L);
	}

	@Test
	void RemoveRequest_createRemove_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		UserInfo userTest3 = new UserInfo();
		userTest3.setLastName("Dev");
		userTest3.setId(3L);

		List<FriendRequest> listFriend = new ArrayList<>();
		FriendRequest fri = new FriendRequest();
		FriendRequest fri2 = new FriendRequest();
		fri2.setUserInfo(userTest3);
		fri.setUserInfo(userTest);
		listFriend.add(fri2);
		listFriend.add(fri);
		userTest2.setListFriendRequest(listFriend);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		friendServiceImpl.removeFriendRequest(2L);
	}

	@Test
	void RemoveRequest_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(2L)).thenReturn(null);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.removeFriendRequest(2L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("User not existed", exception.getMessage());
	}

	@Test
	void RemoveRequest_notExisRequest_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.removeFriendRequest(2L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("no request existed", exception.getMessage());
	}

	@Test
	void GetListRequest_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		List<FriendRequest> listUser = new ArrayList<>();

		Page<FriendRequest> userInfo = new PageImpl<>(listUser);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		var pageable = PageRequest.of(0, 10);

		when(friendRequestRepository.findByUserInfo(userTest.getId(), pageable)).thenReturn(userInfo);

		friendServiceImpl.getListRequest(pageable);
	}

	@Test
	void AcceptFriendRequest_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		UserInfo userTest3 = new UserInfo();
		userTest3.setLastName("Dev");
		userTest3.setId(3L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest2));

		when(userInfoRepository.findOneById(1L)).thenReturn(userTest);

		List<FriendRequest> listFriend = new ArrayList<>();
		FriendRequest fri = new FriendRequest();
		FriendRequest fri2 = new FriendRequest();
		fri2.setUserInfo(userTest3);
		fri.setUserInfo(userTest);
		listFriend.add(fri2);
		listFriend.add(fri);
		userTest2.setListFriendRequest(listFriend);

		friendServiceImpl.acceptFriendRequest(1L);
	}

	@Test
	void AcceptFriendRequest_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest2));
		when(userInfoRepository.findOneById(1L)).thenReturn(null);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.acceptFriendRequest(1L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("User not existed", exception.getMessage());
	}

	@Test
	void AcceptFriendRequest_yourself_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(1L)).thenReturn(userTest);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.acceptFriendRequest(1L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("you can not accept your request", exception.getMessage());
	}

	@Test
	void AcceptFriendRequest_NotExist_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.acceptFriendRequest(2L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("you have no request friend", exception.getMessage());
	}

	@Test
	void RemoveFriendRequest_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		UserInfo userTest3 = new UserInfo();
		userTest3.setLastName("Dev");
		userTest3.setId(3L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		List<Friend> listFriend = new ArrayList<>();
		Friend fri = new Friend();
		Friend fri2 = new Friend();
		Friend fri3 = new Friend();
		fri3.setUserInfo(userTest2);
		fri2.setUserInfo(userTest3);
		fri.setUserInfo(userTest);
		listFriend.add(fri2);
		listFriend.add(fri);
		userTest2.setListFriend(listFriend);
		List<Friend> listFriend2 = new ArrayList<>();
		listFriend2.add(fri3);
		userTest.setListFriend(listFriend2);

		friendServiceImpl.removeFriend(2L);
	}

	@Test
	void RemoveFriend_NotExistUser_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(2L)).thenReturn(null);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.removeFriend(2L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("User not existed", exception.getMessage());
	}

	@Test
	void RemoveFriend_yourself_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(1L)).thenReturn(userTest);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.removeFriend(1L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("can not remove friend yourself", exception.getMessage());
	}

	@Test
	void RemoveFriend_notFriend_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		UserInfo userTest2 = new UserInfo();
		userTest2.setLastName("Dev");
		userTest2.setId(2L);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(userInfoRepository.findOneById(2L)).thenReturn(userTest2);

		AppException exception = assertThrows(AppException.class, () -> friendServiceImpl.removeFriend(2L));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("not friend to remove", exception.getMessage());
	}

	@Test
	void GetListFriend_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		List<FriendUserInfo> listUser = new ArrayList<>();

		Page<FriendUserInfo> userInfo = new PageImpl<>(listUser);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		var pageable = PageRequest.of(0, 10);

		when(friendRepository.findByUserInfo(userTest.getId(), pageable)).thenReturn(userInfo);

		friendServiceImpl.getListFriend(pageable);
	}
}
