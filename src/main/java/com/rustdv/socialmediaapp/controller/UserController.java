package com.rustdv.socialmediaapp.controller;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateUserDto;
import com.rustdv.socialmediaapp.dto.read.PageableResponse;
import com.rustdv.socialmediaapp.dto.read.ReadSenderRecipientDto;
import com.rustdv.socialmediaapp.dto.read.ReadUserDto;
import com.rustdv.socialmediaapp.facade.PostServiceFacade;
import com.rustdv.socialmediaapp.facade.SenderRecipientServiceFacade;
import com.rustdv.socialmediaapp.facade.UserServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceFacade userServiceFacade;

    private final SenderRecipientServiceFacade senderRecipientServiceFacade;

    private final PostServiceFacade postServiceFacade;


    @Value("${db.limit.users}")
    private final String limit;


    @GetMapping("/registration")
    public String registrationPage() {
        return "signUp";
    }

    @GetMapping("/login")
    public String authorization() {

        return "signIn";
    }

    @PostMapping("/signUp")
    public String signUp(Model model, CreateUpdateUserDto createUpdateUserDto) {

        model.addAttribute("readUserDto", userServiceFacade.save(createUpdateUserDto));

        return "redirect:/login";
    }

    @PostMapping("/signIn")
    public String signIn(Model model, CreateUpdateUserDto createUpdateUserDto) {

        var readUserDto = userServiceFacade.findByEmailAndPassword(createUpdateUserDto);

        return "redirect:/users/" + readUserDto.getId();


    }

    @GetMapping("/users/{id}")
    public String findById(Model model, @PathVariable("id") Long id) {

        model.addAttribute("userId", id);
        model.addAttribute("activityFeed", postServiceFacade.displayActivityFeedByUserId(id));

        return "main";

    }

    @GetMapping("/users")
    public String findAllExceptThat(Model model,
                                    @RequestParam("userId") Long userId,
                                    @RequestParam Integer offset) {

        var pageable = PageRequest.of(offset, Integer.parseInt(limit));
        var page = userServiceFacade.findAll(pageable);
        var response = PageableResponse.of(page);
        var list = senderRecipientServiceFacade.findAllBySenderId(userId);
        var list2 = senderRecipientServiceFacade.findAllByRecipientId(userId);
        var recipientIds = list
                .stream()
                .map(ReadSenderRecipientDto::getRecipientId)
                .toList();
        var senderIds = list2
                .stream()
                .map(ReadSenderRecipientDto::getSenderId)
                .toList();

        var friendList = senderRecipientServiceFacade.findAllBySenderIdOrRecipientIdAndRequestStatusAsFriends(userId);
        var friendIds = friendList.stream()
                .map(ReadUserDto::getId)
                .toList();


        model.addAttribute("users", response.getContent());
        model.addAttribute("pagination", response.getMetadata());
        model.addAttribute("userId", userId);
        model.addAttribute("recipientIds", recipientIds);
        model.addAttribute("senderIds", senderIds);
        model.addAttribute("friendIds", friendIds);

        return "allusers";
    }

    @GetMapping("users/{id}/friends")
    public String findMyFriends(Model model, @PathVariable Long id) {

        var friends = senderRecipientServiceFacade.findFriendsByUserId(id);

        model.addAttribute("friends", friends);
        model.addAttribute("userId", id);

        return "myfriends";
    }
}
