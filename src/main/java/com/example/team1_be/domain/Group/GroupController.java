package com.example.team1_be.domain.Group;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.GetInvitation;
import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.domain.Group.Invite.DTO.InvitationCheck;
import com.example.team1_be.domain.Group.Invite.InviteService;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

	private final GroupService groupService;
	private final InviteService inviteService;

	@PostMapping
	public ResponseEntity<?> create(@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody @Valid Create.Request request) {
		groupService.create(userDetails.getUser(), request);
		ApiUtils.ApiResult<String> response = ApiUtils.success(null);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/invitation/information/{invitationKey}")
	public ResponseEntity<?> invitationCheck(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable("invitationKey") String invitationKey) {
		InvitationCheck.Response responseDTO = inviteService.invitationCheck(invitationKey);
		ApiUtils.ApiResult<InvitationCheck.Response> response = ApiUtils.success(responseDTO);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/invitation")
	public ResponseEntity<?> invitationAccept(@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody @Valid InvitationAccept.Request request) {
		groupService.invitationAccept(userDetails.getUser(), request);
		ApiUtils.ApiResult<String> response = ApiUtils.success(null);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<?> getMembers(@AuthenticationPrincipal CustomUserDetails userDetails) {
		GetMembers.Response responseDTO = groupService.getMembers(userDetails.getUser());
		ApiUtils.ApiResult<GetMembers.Response> response = ApiUtils.success(responseDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/invitation")
	public ResponseEntity<?> getInvitation(@AuthenticationPrincipal CustomUserDetails userDetails) {
		GetInvitation.Response responseDTO = inviteService.getInvitation(userDetails.getUser());
		ApiUtils.ApiResult<GetInvitation.Response> response = ApiUtils.success(responseDTO);
		return ResponseEntity.ok(response);
	}
}
