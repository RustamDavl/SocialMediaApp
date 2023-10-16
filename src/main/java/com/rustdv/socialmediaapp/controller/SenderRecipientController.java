package com.rustdv.socialmediaapp.controller;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateSenderRecipientDto;
import com.rustdv.socialmediaapp.facade.SenderRecipientServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class SenderRecipientController {

    private final SenderRecipientServiceFacade facade;

    @PostMapping("/friendrequest")
    public String sendRequest(CreateUpdateSenderRecipientDto request) {

        var object = facade.sendRequest(request);

        return "redirect:/users?offset=0&userId=" + object.getSenderId();

    }

    @GetMapping("/users/{id}/subscriptions")
    public String findSubscriptions(Model model, @PathVariable Long id) {
        model.addAttribute("subscriptions", facade.findAllSubscriptions(id));
        model.addAttribute("userId", id);
        return "/subscriptions";
    }

    @GetMapping("/users/{id}/subscribers")
    public String findSubscribers(Model model, @PathVariable Long id) {
        model.addAttribute("subscribers", facade.findAllSubscribers(id));
        model.addAttribute("userId", id);
        return "/subscribers";
    }


    @PostMapping("/cancelsubscription")
    public String cancelSubscription(CreateUpdateSenderRecipientDto createUpdateSenderRecipientDto) {

        facade.cancelSubscription(createUpdateSenderRecipientDto);


        return "redirect:/users/" + createUpdateSenderRecipientDto.getCurrentUserId() + "/subscriptions";
    }

    @PostMapping("/addtofriends")
    public String addToFriends(CreateUpdateSenderRecipientDto createUpdateSenderRecipientDto) {
        facade.acceptRequest(createUpdateSenderRecipientDto.getSenderId(), createUpdateSenderRecipientDto.getRecipientId());
        return "redirect:/user/" + createUpdateSenderRecipientDto.getCurrentUserId() + "/subscribers";
    }

    @PostMapping("/deletefriend")
    public String deleteFriend(CreateUpdateSenderRecipientDto createUpdateSenderRecipientDto) {
        facade.deleteFriend(createUpdateSenderRecipientDto);
        return "redirect:/users/" + createUpdateSenderRecipientDto.getRecipientId() + "/friends";
    }


}
