package com.rustdv.socialmediaapp.controller;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdatePostDto;
import com.rustdv.socialmediaapp.dto.read.PageableResponse;
import com.rustdv.socialmediaapp.facade.PostServiceFacade;
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
public class PostController {

    private final PostServiceFacade postServiceFacade;

    @Value("${db.limit.posts}")
    private final String limit;

    @PostMapping("/users/{id}/post")
    public String create(Model model, @PathVariable("id") Long id,
                         CreateUpdatePostDto createUpdatePostDto) {

        model.addAttribute("readPostDto", postServiceFacade.create(createUpdatePostDto, id));

        return "redirect:/posts?userId=" + id;
    }


    @GetMapping("/users/{id}/posts")
    public String findAllByPageable(Model model, @PathVariable Long id,
                                    @RequestParam Integer offset,
                                    @RequestParam(required = false) Long viewerId) {

        var pageable = PageRequest.of(offset, Integer.parseInt(limit));
        var page = postServiceFacade.findAllByUserIdAndPageable(id, pageable);
        var response = PageableResponse.of(page);
        model.addAttribute("myposts", response.getContent());
        model.addAttribute("pagination", response.getMetadata());
        model.addAttribute("userId", id);
        model.addAttribute("viewerId", viewerId);

        return "myposts";
    }

    @GetMapping("/posts")
    public String getPostPage(Model model, @RequestParam("userId") Long id) {

        model.addAttribute("userId", id);

        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String findById(Model model, @PathVariable Long id) {

        model.addAttribute("post", postServiceFacade.findById(id));

        return "createupdatepost";
    }

    @PostMapping("/posts/{id}/update")
    public String update(@PathVariable Long id, CreateUpdatePostDto createUpdatePostDto) {
        postServiceFacade.update(id, createUpdatePostDto);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/delete")
    public String delete(Long id) {
        var readPostDto = postServiceFacade.findById(id);
        postServiceFacade.delete(id);
        return "redirect:/users/" + readPostDto.getUserId() + "/posts";
    }
}
