package com.rustdv.socialmediaapp.facade;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdatePostDto;
import com.rustdv.socialmediaapp.dto.read.ReadPostDto;
import com.rustdv.socialmediaapp.mapper.CreateUpdatePostDtoMapper;
import com.rustdv.socialmediaapp.mapper.ReadPostDtoMapper;
import com.rustdv.socialmediaapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostServiceFacade {

    private final PostService postService;
    private final ReadPostDtoMapper readPostDtoMapper;
    private final CreateUpdatePostDtoMapper createUpdatePostDtoMapper;

    public ReadPostDto create(CreateUpdatePostDto createUpdatePostDto, Long userId) {

        return readPostDtoMapper.mapFrom(
                postService.create(
                        createUpdatePostDtoMapper.mapFrom(createUpdatePostDto), userId));
    }

    public List<ReadPostDto> findAll() {
        return postService.findAll().stream()
                .map(readPostDtoMapper::mapFrom)
                .toList();
    }

    public List<ReadPostDto> findAllByUserId(Long id) {
        return postService.findAllByUserId(id).stream()
                .map(readPostDtoMapper::mapFrom)
                .toList();
    }

    public Page<ReadPostDto> findAllByUserIdAndPageable(Long id, Pageable pageable) {
        return postService.findAllByUserIdAndPageable(id, pageable)
                .map(readPostDtoMapper::mapFrom);
    }

    public ReadPostDto findById(Long id) {
        return readPostDtoMapper.mapFrom(postService.findById(id));
    }

    public ReadPostDto update(Long postId, CreateUpdatePostDto createUpdatePostDto) {
        return readPostDtoMapper.mapFrom(
                postService.update(postId, createUpdatePostDtoMapper.mapFrom(createUpdatePostDto)));
    }

    public void delete(Long id) {
        postService.delete(id);
    }

    public List<ReadPostDto> displayActivityFeedByUserId(Long userId) {
        return postService.displayActivityFeedByUserId(userId)
                .stream()
                .map(readPostDtoMapper::mapFrom)
                .toList();
    }
}
