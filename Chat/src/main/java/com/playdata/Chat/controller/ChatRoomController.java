package com.playdata.Chat.controller;

import com.playdata.Chat.dto.ChatRoomResponse;
import com.playdata.Chat.dto.RoomCreateRequest;
import com.playdata.Chat.entity.ChatRoom;
import com.playdata.Chat.repository.ChatRoomRepository;
import com.playdata.Chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping
    public ChatRoom createRoom(@RequestBody RoomCreateRequest roomCreateRequest) {
        return chatRoomService.createRoom(roomCreateRequest.getRoomName(), roomCreateRequest.getMembers(),roomCreateRequest.getCreatorName());

    }
    @GetMapping("/member/{name}")
    public List<ChatRoom> getRoomsForMember(@PathVariable String name) {
        return chatRoomService.getRoomsForMember(name);
    }

    @PostMapping("/{roomId}/members")
    public ChatRoom addMemberToRoom(@PathVariable String roomId, @RequestParam String Id) {
        return chatRoomService.addMember(roomId, Id);
    }

    @DeleteMapping("/{roomId}/members/{name}")
    public ChatRoom removeMemberFromRoom(@PathVariable String roomId, @PathVariable String name) {
        return chatRoomService.removeMember(roomId,name);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoom> getRoomById(@PathVariable String roomId) {
        return chatRoomRepository.findById(roomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
